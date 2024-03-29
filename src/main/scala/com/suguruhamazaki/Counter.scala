package com.suguruhamazaki

case class OoCounter(var get: Int) {
  def inc(): Int = {
    get = get + 1
    get
  }
}

object OoCounter {
  val c = OoCounter(0)
  def example() = {
    c.inc()
    c.inc()
    println(c.get)
  }
}

case class Counter(get: Int)

object FpCounter {

  type CounterState = State[Counter, Int]
  val inc: CounterState = State { c =>
    val v = c.get + 1
    (Counter(v), v)
  }

  def example() = {
    val c0 = Counter(0)
    val (c1, _) = inc.run(c0)
    val (_, v) = inc.run(c1)
    println(v)
  }

  def example2() = {
    val action = for {
      _ ← inc
      v ← inc
    } yield (v)
    val (_, v) = action.run(Counter(0))
    println(v)
  }

}
