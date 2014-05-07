package com.suguruhamazaki

case class OoCoin(private var head: Boolean) {
  def flip() = { head = !head }
  def stay() = {} // do nothing
  def get = head
}

object OoCoin {
  def example() = {
    val c = OoCoin(true)
    c.flip()
    c.stay()
    c.flip()
    println("Showing a head? " + c.get)
  }
}
