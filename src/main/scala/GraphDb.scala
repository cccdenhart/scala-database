import scala.collection.mutable

// simple implementation of a graph database
class GraphDb {

  var redis: Redis = new Redis
  val INT_MAX: Int = 1000000
  var nodes: List[String] = List()

  // adds a node to this graph
  def addNode(v: String): Unit = {
    if (!this.nodes.contains()) this.nodes = v +: this.nodes
  }

  // add an edge to this graph
  def addEdge(u: String, v: String): Unit = {
    // only add an edge if both nodes have already been created
    if (this.nodes.contains(u) && this.nodes.contains(v)) {
      this.redis.lpush(u, v)
      this.redis.lpush(v, u)
    } else {
      throw new IllegalArgumentException("One of the nodes entered does not exist yet")
    }
  }

  // find all of the nodes connected to the given node
  def adjacent(v: String): List[String] = {
    // check if the given node has been added to this.nodes yet
    if (this.nodes.contains(v)) {
      try {
        this.redis.lrange(v)
      } catch {
        // if the node exists, but does not have any neighbors, return an empty list
        // else, pass on the exception
        case nee: NoSuchElementException => List()
        case e: Exception => throw new Exception(e.printStackTrace().toString)
      }
    } else {
      throw new IllegalArgumentException("The node entered does not exist yet")
    }
  }

  // find the shortest path from u to v using a standard bfs implementation
  def shortestPath(u: String, v: String): List[String] = {
    val queue: mutable.Queue[String] = mutable.Queue()
    var visited: Map[String, Boolean] = Map()
    var dist: Map[String, Int] = Map()
    var pred: Map[String, String] = Map()
    for (node <- this.nodes) {
      visited += (node -> false)
      dist += (node -> INT_MAX)
      pred += (node -> null)
    }
    visited += (u -> true)
    dist += (u -> 0)
    queue.enqueue(u)
    var last: String = u

    while (queue.nonEmpty) {
      val cur: String = queue.dequeue()
      for (adj <- this.adjacent(cur)) {
        if (!visited(adj)) {
          visited += (adj -> true)
          dist += (cur -> (dist(cur) + 1))
          pred += (adj -> cur)
          queue.enqueue(adj)
          if (adj == v) {
            last = adj
          }
        }
      }
    }
    this.tracePath(pred, last, u)
  }

  // trace back the path from the last to the first node
  def tracePath(pred: Map[String, String], last: String, first: String): List[String] = {
    var path: List[String] = List()
    var cur: String = last
    while (cur != first) {
      path = cur +: path
      cur = pred(cur)
    }
    path = cur +: path
    path
  }
}

object GraphDb {

  def main(args: Array[String]): Unit = {
    val graph: GraphDb = new GraphDb

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

    println("Shortest Path from 'x' to 'y': ")
    for (i <- graph.shortestPath("x", "y")) println(i)
  }
}