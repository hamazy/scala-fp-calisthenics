package com.suguruhamazaki

import org.scalatest.{ FlatSpec, Matchers }
import Coin2._

class Coin2Spec extends FlatSpec with Matchers {

  "Flip" should "turn a head to a tail" in {
    flip(Coin(true)) should be(Coin(false))
  }

  it should "turn a tail to a head" in {
    flip(Coin(false)) should be(Coin(true))
  }

  "Stay" should "keep a head as it is" in {
    stay(Coin(true)) should be(Coin(true))
  }

  it should "keep a tail as it is" in {
    stay(Coin(false)) should be(Coin(false))
  }

  "Flip + flip" should "keep the status" in {
    (flip + flip)(Coin(true)) should be(Coin(true))
    (flip + flip)(Coin(false)) should be(Coin(false))
  }

  "Flip + flip" should "change the status" in {
    (flip + stay)(Coin(true)) should be(Coin(false))
    (flip + stay)(Coin(false)) should be(Coin(true))
  }

  "Stay + flip" should "change the status" in {
    (stay + flip)(Coin(true)) should be(Coin(false))
    (stay + flip)(Coin(false)) should be(Coin(true))
  }

  "Stay + stay" should "keep the status" in {
    (stay + stay)(Coin(true)) should be(Coin(true))
    (stay + stay)(Coin(false)) should be(Coin(false))
  }

}
