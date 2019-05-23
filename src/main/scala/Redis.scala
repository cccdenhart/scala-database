import scala.collection.mutable

// a simple implementation of Redis using Scala
class Redis {

  /*
  * NOTE: if a key doesn't exist, NoSuchElementException is thrown
  */

  // the key-value store
  var db: mutable.Map[String, List[String]] = mutable.Map()

  // gets the string value paired with the given key
  def get(key: String): String = {
    this.db(key).head // simply store single values as a one item list
  }

  // pairs the given string value with the given key
  def set(key: String, value: String): Unit = {
    this.db(key) = List(value)
  }

  // prepends the value to the array at the given key
  def lpush(key: String, value: String): Unit = {
    if (this.db.contains(key)) {
      this.db(key) = value +: this.db(key)
    } else {
      this.db += (key -> List(value))
    }
  }

  // appends the value to the array at the given key
  def rpush(key: String, value: String): Unit = {
    if (this.db.contains(key)) {
      this.db(key) = this.db(key) :+ value
    } else {
      this.db += (key -> List(value))
    }
  }

  // removes and returns the first value of the array at the given key
  def lpop(key: String): String = {
      val value: String = this.db(key).head
      this.db += (key -> this.db(key).drop(1))
      value
  }

  // removes and returns the last value of the array at the given key
  def rpop(key: String): String = {
    val value: String = this.db(key).last
    this.db += (key -> this.db(key).dropRight(1))
    value
  }

  // returns all of the values of the array at the given key
  def lrange(key: String): List[String] = {
    this.db(key)
  }

  // returns the length of the array at the given key
  def llen(key: String): Int = {
    this.db(key).length
  }

  def flushall(): Unit = {
    this.db.clear()
  }

}
