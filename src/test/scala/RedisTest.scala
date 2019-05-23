import org.scalatest.FunSuite

import scala.collection.mutable

class RedisTest extends FunSuite {

  val redis: Redis = new Redis

  test("testLpush") {
    redis.flushall()
    redis.lpush("key1", "hello")
    assert(redis.lpop("key1") === "hello")
    redis.lpush("key1", "hello")
    redis.lpush("key1", "world")
    assert(redis.rpop("key1") === "hello")
    assert(redis.lpop("key1") === "world")
  }

  test("testSet") {
    redis.flushall()
    redis.set("key2", "hello")
    assert(redis.get("key2") === "hello")
  }

  test("testRpop") {
    redis.flushall()
    redis.rpush("key1", "hello")
    redis.rpush("key1", "world")
    assert(redis.rpop("key1") === "world")
    assert(redis.rpop("key1") === "hello")
  }

  test("testLrange") {
    redis.flushall()
    intercept[NoSuchElementException] {
      redis.lrange("key1")
    }
    redis.lpush("key1", "hello")
    redis.lpush("key1", "goodbye")
    assert(redis.lrange("key1") === List("goodbye", "hello"))
  }

  test("testGet") {
    redis.set("key1", "5")
    assert(redis.get("key1") === "5")
    intercept[NoSuchElementException] {
      redis.get("key2")
    }
  }

  test("testRpush") {
    redis.flushall()
    redis.rpush("key1", "hello")
    assert(redis.lpop("key1") === "hello")
    redis.rpush("key1", "hello")
    redis.rpush("key1", "world")
    assert(redis.rpop("key1") === "world")
    assert(redis.rpop("key1") === "hello")
  }

  test("testLpop") {
    redis.db = mutable.Map("key1" -> List("hello", "world"))
    assert(redis.lpop("key1") === "hello")
    assert(redis.lpop("key1") === "world")
  }

  test("testFlushall") {
    redis.set("key1", "10")
    redis.lpush("key2", "hello")
    redis.lpush("key2", "world")
    redis.flushall()
    intercept[NoSuchElementException] {
      redis.get("key1")
    }
    intercept[NoSuchElementException] {
      redis.lpop("key2")
    }
  }

  test("testLlen") {
    redis.flushall()
    intercept[NoSuchElementException] {
      redis.llen("key1")
    }
    redis.lpush("key1", "hello")
    assert(redis.llen("key1") === 1)
  }
}
