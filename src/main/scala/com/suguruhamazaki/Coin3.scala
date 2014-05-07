package com.suguruhamazaki

object Coin3 {
  case class CoinAction[A](action: Coin => (Coin, A))
    extends (Coin => (Coin, A)) {
    def apply(c: Coin) = action(c)
    def +[B](next: CoinAction[B]): CoinAction[B] =
      flatMap(_ => next)
    def map[B](f: A => B): CoinAction[B] = CoinAction { c0 =>
      val (c1, a) = apply(c0)
      (c1, f(a))
    }
    def flatMap[B](f: A => CoinAction[B]): CoinAction[B] =
      CoinAction { c0 =>
        val (c1, a) = apply(c0)
        f(a)(c1)
      }
  }

  val flip = CoinAction { c =>
    val head = !c.head
    (Coin(head), head)
  }
  val stay = CoinAction(c => (c, c.head))

  def example() = {
    val (_, message) = (flip + stay + flip).map(head => "Showing a head? " + head)(Coin(true))
    println(message)
  }

  def example2() = {
    val action =
      flip.flatMap { _ =>
        stay.flatMap { _ =>
          flip
        }
      }
    val (c, _) = action(Coin(true))
    println("Showing a head? " + c.head)
  }

  def example3() = {
    val action =
      flip.flatMap { b1 =>
        stay.flatMap { _ =>
          flip.map { b3 =>
            (b1, b3)
          }
        }
      }
    val (_, (b1, b3)) = action(Coin(true))
    println("1st occurrence is a head? " + b1)
    println("3rd occurrence is a head? " + b3)
  }

  def example4() = {
    val action = for {
      b1 <- flip
      _ <- stay
      b3 <- flip
    } yield (b1, b3)
    val (_, (b1, b3)) = action(Coin(true))
    println("1st occurrence is a head? " + b1)
    println("3rd occurrence is a head? " + b3)
  }

  def example5() = {
    val action = for {
      s1 <- flip.map(b1 => "1st occurrence is a head? " + b1)
      _ <- stay
      s3 <- flip.map(b3 => "3rd occurrence is a head? " + b3)
    } yield (s1 + "\n" + s3)
    val (_, s) = action(Coin(true))
    println(s)
  }

}
