import org.scalatest.FunSuite

class Problem2Test extends FunSuite {

  val p2: Problem2 = new Problem2

  test("testToBinary") {
    assert(p2.toBinary(0, 1) === "0")
    assert(p2.toBinary(1, 1) === "1")
    assert(p2.toBinary(7, 3) === "111")
    assert(p2.toBinary(21, 5) === "10101")
    intercept[IllegalArgumentException] {
      p2.toBinary(8, 3)
    }
  }

  test("testWeight") {
    assert(p2.weight("0") === 0)
    assert(p2.weight("1") === 1)
    assert(p2.weight("1010010") === 3)
    assert(p2.weight("00011101") === 4)
    intercept[IllegalArgumentException] {
      p2.weight("abc")
    }
    intercept[IllegalArgumentException] {
      p2.weight("021")
    }
  }

}
