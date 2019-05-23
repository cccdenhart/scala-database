import org.scalatest.FunSuite

class Problem3Test extends FunSuite {

  val p3 = new Problem3
  test("testMoved") {
    //assert(p3.moved(0, 2, 3) === 0)
    assert(p3.moved(10, 2, 3) === (7.0 / 10.0 * 100))
    assert(p3.moved(10, 2, 4) === (5.0 / 10.0 * 100))
    assert(p3.moved(10, 3, 4) === (8.0 / 10.0 * 100))
  }

}
