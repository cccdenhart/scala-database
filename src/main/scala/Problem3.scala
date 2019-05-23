class Problem3 {

  // returns the fraction of records that would have to be re-assigned to a new node if records were re-partitioned from startN to endN nodes using the mod function
  def moved(records: Int, startN: Int, endN: Int): Double = {
    val before: Array[Int] = (1 to records).map(_ % startN).toArray
    val after: Array[Int] = (1 to records).map(_ % endN).toArray
    val numChanged: Double = (before zip after).map(t => if (t._1 != t._2) 1 else 0).sum
    numChanged / records.toDouble * 100
  }
}

object Problem3 {

  def main(args: Array[String]): Unit = {
    val p3: Problem3 = new Problem3
    println("Percentage of reassigned records for moved(1000000, 100, 107): " + p3.moved(1000000,
      100, 107))
  }
}
