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
