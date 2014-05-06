package com.suguruhamazaki

object Coin4 {
  type CoinAction[A] = State[Coin, A]
  val flip: CoinAction[Boolean] = State { c =>
    val head = !c.head
    (head, Coin(head))
  }
  val stay: CoinAction[Boolean] = State(c => (c.head, c))

  def example() = {
    val c0 = Coin(true)
    val (_, c1) = flip.run(c0)
    val (_, c2) = stay.run(c1)
    val (head, _) = flip.run(c2)
    println("Showing head: " + head)
  }

  def example2() {
    val action =
      flip.flatMap { _ =>
        stay.flatMap { _ =>
          flip.map { v =>
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
      _ ← modify { _: Coin => Coin(true) }
      c ← flip
    } yield (c)
    println(action.run(Coin(true))._1)
    println(action.run(Coin(false))._1)
  }
}
