package com.suguruhamazaki

import org.scalatest.{ FlatSpec, Matchers }
import Coin3._

class Coin3Spec extends FlatSpec with Matchers {

  "Flip" should "turn a head to a tail" in {
    flip(Coin(true))._1 should be(Coin(false))
  }

  it should "turn a tail to a head" in {
    flip(Coin(false))._1 should be(Coin(true))
  }

  "Stay" should "keep a head as it is" in {
    stay(Coin(true))._1 should be(Coin(true))
  }

  it should "keep a tail as it is" in {
    stay(Coin(false))._1 should be(Coin(false))
  }

  "Flip + flip" should "keep the status" in {
    (flip + flip)(Coin(true))._1 should be(Coin(true))
    (flip + flip)(Coin(false))._1 should be(Coin(false))
  }

  "Flip + flip" should "change the status" in {
    (flip + stay)(Coin(true))._1 should be(Coin(false))
    (flip + stay)(Coin(false))._1 should be(Coin(true))
  }

  "Stay + flip" should "change the status" in {
    (stay + flip)(Coin(true))._1 should be(Coin(false))
    (stay + flip)(Coin(false))._1 should be(Coin(true))
  }

  "Stay + stay" should "keep the status" in {
    (stay + stay)(Coin(true))._1 should be(Coin(true))
    (stay + stay)(Coin(false))._1 should be(Coin(false))
  }

}
