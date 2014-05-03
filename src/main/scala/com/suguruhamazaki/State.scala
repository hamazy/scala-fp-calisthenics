package com.suguruhamazaki

case class State[S, +A](run: S ⇒ (A, S)) {
  def unit: State[S, A] = this
  def map[B](f: A ⇒ B): State[S, B] =
    State { s ⇒
      val (a, s2) = run(s)
      (f(a), s2)
    }
  def map2[B, C](sb: State[S, B])(f: (A, B) ⇒ C): State[S, C] =
    flatMap { a ⇒
      sb.map { b ⇒
        f(a, b)
      }
    }
  def flatMap[B](f: A ⇒ State[S, B]): State[S, B] =
    State { s ⇒
      val (a, s2) = run(s)
      f(a).run(s2)
    }
  def set(s: S): State[S, Unit] =
    State { _ ⇒
      ((), s)
    }
}

object State {
  def get[S]: State[S, S] = State(s ⇒ (s, s))
  def set[S](s: S): State[S, Unit] =
    State { _ ⇒
      ((), s)
    }
  def modify[S](f: S ⇒ S): State[S, Unit] =
    for {
      s ← get
      _ ← set(f(s))
    } yield ()
}
