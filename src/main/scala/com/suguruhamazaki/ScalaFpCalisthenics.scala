package com.suguruhamazaki

object ScalaFpCalisthenics {
  def main(args: Array[String]): Unit = {
    println("Hello, World")
  }

}

object NaiveRecursion {
  def isOdd(n: Long): Boolean =
    if (n == 0) false
    else isEven(n - 1)

  def isEven(n: Long): Boolean =
    if (n == 0) true
    else isOdd(n - 1)
}

object MutualRecursion {
  import scala.util.control.TailCalls.{ TailRec, done, tailcall }

  def isOdd(n: Long): TailRec[Boolean] =
    if (n == 0) done(false)
    else tailcall(isEven(n - 1))

  def isEven(n: Long): TailRec[Boolean] =
    if (n == 0) done(true)
    else tailcall(isOdd(n - 1))
}

object SubstitutionModel {
  def sumOfSquares(i1: Int, i2: Int) = square(i1) + square(i2)
  def square(i: Int) = i * i
}
