package com.suguruhamazaki

case class Coin(head: Boolean)

object Coin1 {
  def flip(c: Coin) = Coin(!c.head)
  def stay(c: Coin) = c

  def example() = {
    val c0 = Coin(true)
    val c1 = flip(c0)
    val c2 = stay(c1)
    val c3 = flip(c2)
    println("Showing head: " + c3.head)
  }
}

object Coin2 {
  case class CoinAction(action: Coin ⇒ Coin) extends (Coin ⇒ Coin) {
    def apply(c: Coin) = action(c)
    def +(next: CoinAction): CoinAction = CoinAction { c0 ⇒
      val c1 = action(c0)
      next(c1)
    }
  }

  val flip = CoinAction(c ⇒ Coin(!c.head))
  val stay = CoinAction(c ⇒ c)

  def example() = {
    val c = (flip + stay + flip)(Coin(true))
    println("Showing head: " + c.head)
  }
}

object Coin3 {
  case class CoinAction[A](action: Coin ⇒ (A, Coin)) extends (Coin ⇒ (A, Coin)) {
    def apply(c: Coin) = action(c)
    def +(next: CoinAction[A]): CoinAction[A] = CoinAction { c0 ⇒
      val (_, c1) = apply(c0)
      next(c1)
    }
    def map[B](f: A ⇒ B): CoinAction[B] = CoinAction { c0 ⇒
      val (a, c1) = apply(c0)
      (f(a), c1)
    }
    def flatMap[B](f: A ⇒ CoinAction[B]): CoinAction[B] = CoinAction { c0 ⇒
      val (a, c1) = apply(c0)
      f(a)(c1)
    }
  }

  val flip = CoinAction { c ⇒
    val head = !c.head
    (head, Coin(head))
  }
  val stay = CoinAction(c ⇒ (c.head, c))

  def example() = {
    val (message, _) = (flip + stay + flip).map(head ⇒ "Showing head: " + head)(Coin(true))
    println(message)
  }

  def example2() = {
    val action = for {
      s1 ← flip.map(h ⇒ "first: " + h)
      i2 ← stay.map(h ⇒ if (h) 1 else 0)
      s3 ← flip.map(h ⇒ "third:" + h)
    } yield (s1, i2, s3)
    val ((s1, i2, s3), _) = action(Coin(true))
    println(s1)
    println(i2)
    println(s3)
  }
}

object Coin4 {
  type CoinAction[A] = State[Coin, A]
  val flip: CoinAction[Boolean] = State { c ⇒
    val head = !c.head
    (head, Coin(head))
  }
  val stay: CoinAction[Boolean] = State(c ⇒ (c.head, c))

  def example() = {
    val c0 = Coin(true)
    val (_, c1) = flip.run(c0)
    val (_, c2) = stay.run(c1)
    val (head, _) = flip.run(c2)
    println("Showing head: " + head)
  }

  def example2() {
    val action =
      flip.flatMap { _ ⇒
        stay.flatMap { _ ⇒
          flip.map { v ⇒
            v
          }
        }
      }
    println(action.run(Coin(true))._1)
  }

  def example3() = {
    val s = for {
      _ ← flip
      _ ← stay
      v ← flip
    } yield (v)
    println(s.run(Coin(true))._1)
  }

  def example4() = {
    import State._
    val action = for {
      _ ← modify { _: Coin ⇒ Coin(true) }
      c ← flip
    } yield (c)
    println(action.run(Coin(true))._1)
    println(action.run(Coin(false))._1)
  }
}

