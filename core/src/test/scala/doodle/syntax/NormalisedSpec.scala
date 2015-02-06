package doodle.syntax

import utest._
import doodle.core.Normalised
import doodle.syntax.normalised._

object NormalisedSpec extends TestSuite {
  val tests = TestSuite {
    "syntax constructs expected normaliseds"-{
      assert(60.clip == Normalised.MaxValue)
      assert(1.clip == Normalised.MaxValue)
      assert(0.5.clip == Normalised.clip(0.5))
    }
  }
}
