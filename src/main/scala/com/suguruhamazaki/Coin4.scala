package com.suguruhamazaki

object Coin4 {
  type CoinAction[A] = State[Coin, A]
  val flip: CoinAction[Boolean] = State { c =>
    val head = !c.head
    (Coin(head), head)
  }
  val stay: CoinAction[Boolean] = State(c => (c, c.head))

  def example() = {
    val c0 = Coin(true)
    val (c1, _) = flip.run(c0)
    val (c2, _) = stay.run(c1)
    val (_, head) = flip.run(c2)
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
    println(action.run(Coin(true))._2)
  }

  def example3() = {
    val s = for {
      _ ← flip
      _ ← stay
      v ← flip
    } yield (v)
    println(s.run(Coin(true))._2)
  }

  def example4() = {
    import State._
    val action = for {
      _ ← modify { _: Coin => Coin(true) }
      c ← flip
    } yield (c)
    println(action.run(Coin(true))._2)
    println(action.run(Coin(false))._2)
  }
}
