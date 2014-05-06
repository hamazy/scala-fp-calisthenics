package com.suguruhamazaki

object Coin3 {
  case class CoinAction[A](action: Coin => (A, Coin))
    extends (Coin => (A, Coin)) {
    def apply(c: Coin) = action(c)
    def +(next: CoinAction[A]): CoinAction[A] = CoinAction { c0 =>
      val (_, c1) = apply(c0)
      next(c1)
    }
    def map[B](f: A => B): CoinAction[B] = CoinAction { c0 =>
      val (a, c1) = apply(c0)
      (f(a), c1)
    }
    def flatMap[B](f: A => CoinAction[B]): CoinAction[B] =
      CoinAction { c0 =>
        val (a, c1) = apply(c0)
        f(a)(c1)
      }
  }

  val flip = CoinAction { c =>
    val head = !c.head
    (head, Coin(head))
  }
  val stay = CoinAction(c => (c.head, c))

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
    val ((s1, b3), _) = action(Coin(true))
    println(s1)
    println(b3)
  }

  def example3() = {
    val action = for {
      s1 <- flip.map(h => "first: " + h)
      _ <- stay
      b3 <- flip
    } yield (s1, b3)
    val ((s1, b3), _) = action(Coin(true))
    println(s1)
    println(b3)
  }
}
