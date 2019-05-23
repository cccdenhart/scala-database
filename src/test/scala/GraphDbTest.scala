import org.scalatest.FunSuite

class GraphDbTest extends FunSuite {

  test("testAddNode") {
    val graph: GraphDb = new GraphDb
    val beforeNodes: Int = graph.nodes.size
    graph.addNode("a")
    assert(graph.nodes.size === beforeNodes + 1)
  }

  test("testAddEdges") {
    val graph: GraphDb = new GraphDb
    graph.addNode("a")
    graph.addNode("b")
    graph.addEdge("a", "b")
    assert(graph.redis.lrange("a") === List("b"))
    intercept[IllegalArgumentException] {
      graph.addEdge("a", "c")
    }
  }

  test("testAdjacent") {
    val graph: GraphDb = new GraphDb
    intercept[IllegalArgumentException] {
      graph.adjacent("a")
    }
    graph.addNode("a")
    graph.addNode("b")
    graph.addNode("c")
    graph.addNode("d")
    graph.addEdge("a", "b")
    graph.addEdge("a", "c")
    assert(graph.adjacent("a") === List("c", "b"))
    assert(graph.adjacent("b") === List("a"))
    assert(graph.adjacent("c") === List("a"))
    assert(graph.adjacent("d") === List())
  }

  test("testShortestPath") {
    val graph: GraphDb = new GraphDb
    intercept[IllegalArgumentException] {
      graph.shortestPath("a", "b")
    }
    // add graph nodes
    graph.addNode("x")
    graph.addNode("j")
    graph.addNode("b")
    graph.addNode("c")
    graph.addNode("r")
    graph.addNode("f")
    graph.addNode("e")
    graph.addNode("y")

    // add graph edges
    graph.addEdge("x", "j")
    graph.addEdge("j", "f")
    graph.addEdge("j", "b")
    graph.addEdge("j", "r")
    graph.addEdge("b", "f")
    graph.addEdge("b", "r")
    graph.addEdge("b", "c")
    graph.addEdge("f", "e")
    graph.addEdge("r", "c")
    graph.addEdge("r", "e")
    graph.addEdge("r", "y")
    graph.addEdge("e", "y")

    // check shortest paths
    assert(graph.shortestPath("x", "j") === List("x", "j"))
    assert(graph.shortestPath("x", "y") === List("x", "j", "r", "y"))
  }
}
