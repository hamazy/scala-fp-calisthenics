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
      s1 <- flip.map(b => "1st occurrence is a head? " + b)
      _ <- stay
      s3 <- flip.map(b => "3rd occurrence is a head? " + b)
    } yield (s1, s3)
    val (_, (s1, s3)) = action(Coin(true))
    println(s1)
    println(s3)
  }
}
