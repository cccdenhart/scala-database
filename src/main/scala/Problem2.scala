import java.io.{BufferedWriter, FileWriter}
import scala.collection.JavaConverters._
import au.com.bytecode.opencsv.CSVWriter

class Problem2 {

  // convert the given unsigned integer to a bit string of the given length
  def toBinary(x: Int, bits: Int): String = {
    if (math.pow(2, bits) <= x) throw new IllegalArgumentException("not enough bits to convert this integer")
    if (x == 0) return "0" * bits
    toBinary(math.floor(x / 2).toInt, bits - 1) + (x % 2).toString
  }

  // count the number of ones in the given bit string
  def weight(b: String): Int = {
    if (!b.matches("[01]+")) throw new IllegalArgumentException("cannot enter a non-binary number")
    b.count(_ == '1')
  }
}

object Problem2 {
  def main(args: Array[String]): Unit = {
    val p2 = new Problem2
    val MAX_NUM = 1024
    val MAX_BIN = math.ceil(math.log(MAX_NUM) / math.log(2)).toInt + 1
    println("Converting 1,234,567,890 to binary:")
    println(p2.toBinary(1234567890, 32))
    println()
    println("Exporting a file of weights of all binary numbers from 0 to 1024...")
    val nums= 0 to MAX_NUM
    val weights: java.util.List[Array[String]] = nums.map(x => Array(p2.weight
    (p2.toBinary(x, MAX_BIN)).toString)).toList.asJava
    val outputFile = new BufferedWriter(new FileWriter("./data/weights.csv"))
    val csvWriter = new CSVWriter(outputFile)
    csvWriter.writeAll(weights)
    outputFile.close()
    println("Done")
  }
}
