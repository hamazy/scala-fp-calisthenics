package com.suguruhamazaki

object Coin3 {
  case class CoinAction[A](action: Coin => (Coin, A))
    extends (Coin => (Coin, A)) {
    def apply(c: Coin) = action(c)
    def +(next: CoinAction[A]): CoinAction[A] = CoinAction { c0 =>
      val (c1, _) = apply(c0)
      next(c1)
    }
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
    val (message, _) = (flip + stay + flip).map(head => "Showing head: " + head)(Coin(true))
    println(message)
  }

  def example2() = {
    val action =
      flip.map(h => "first: " + h).flatMap { s1 =>
        stay.flatMap { _ =>
          flip.map { b3 =>
            (s1, b3)
          }
        }
      }
    val (_, (s1, b3)) = action(Coin(true))
    println(s1)
    println(b3)
  }

  def example3() = {
    val action = for {
      s1 <- flip.map(h => "first: " + h)
      _ <- stay
      b3 <- flip
    } yield (s1, b3)
    val (_, (s1, b3)) = action(Coin(true))
    println(s1)
    println(b3)
  }
}
