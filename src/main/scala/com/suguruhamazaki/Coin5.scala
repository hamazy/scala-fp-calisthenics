package com.suguruhamazaki

object Coin5 {
  import scalaz.State
  type CoinAction[A] = State[Coin, A]

  val flip: CoinAction[Boolean] = State { c =>
    val head = !c.head
    (Coin(head), head)
  }
  val stay: CoinAction[Boolean] = State(c => (c, c.head))

  def example() = {
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
