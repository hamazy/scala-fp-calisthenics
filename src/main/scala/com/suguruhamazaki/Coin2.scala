package com.suguruhamazaki

object Coin2 {
  case class CoinAction(action: Coin => Coin) extends (Coin => Coin) {
    def apply(c: Coin) = action(c)
    def +(next: CoinAction): CoinAction = CoinAction { c0 =>
      val c1 = action(c0)
      next(c1)
    }
  }

  val flip = CoinAction(c => Coin(!c.head))
  val stay = CoinAction(c => c)

  def example() = {
    val action = flip + stay + flip
    val c = action(Coin(true))
    println("Showing head: " + c.head)
  }
}
