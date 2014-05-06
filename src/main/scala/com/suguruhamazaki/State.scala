package com.suguruhamazaki

case class State[S, +A](run: S => (A, S)) {
  def map[B](f: A => B): State[S, B] =
    State { s =>
      val (a, s2) = run(s)
      (f(a), s2)
    }
  def flatMap[B](f: A => State[S, B]): State[S, B] =
    State { s =>
      val (a, s2) = run(s)
      f(a).run(s2)
    }
}

object State {
  def unit[S, A](a: A): State[S, A] = State { (a, _) }
  def get[S]: State[S, S] = State(s => (s, s))
  def set[S](s: S): State[S, Unit] =
    State { _ =>
      ((), s)
    }
  def modify[S](f: S => S): State[S, Unit] =
    for {
      s ← get
      _ ← set(f(s))
    } yield ()
}
