package io.github.chenfh5

import java.util.concurrent.ConcurrentHashMap

import io.github.chenfh5.api.Api
import io.github.chenfh5.prd.Prd
import org.scalamock.scalatest.MockFactory
import org.scalatest.{BeforeAndAfter, FunSuite}

class PrdTest extends FunSuite with MockFactory with BeforeAndAfter {
  val initMap = new ConcurrentHashMap[String, String]()

  before {
    initMap.put("k1", "v1")
    initMap.put("k2", "v2")
    initMap.put("your_key", "your_value")
  }

  after {
    initMap.clear()
    initMap == null // help gc
  }

  test("prd get success") {
    val storage = new Prd(null, initMap)
    assert(storage.get("k1") == "v1")
    assert(storage.get("k2") == "v2")
    assert(storage.get("your_key") == "your_value")
  }

  test("prd set success") {
    // mock here
    val client = stub[Api]
    (client.GetObject _).when(*).returns(Array(97, 98, 99, 100, 101).map(_.toByte)).once() // abcde

    val storage = new Prd(client, initMap)
    storage.set("mock_your_key_already")
    assert(storage.get(new String(Array(97, 98).map(_.toByte))) == new String(Array(99, 100, 101).map(_.toByte)))
    assert(initMap.size() == 4) // every test is independence
  }

  test("prd set duplicated success") {
    // mock here
    val client = stub[Api]
    (client.GetObject _).when(*).returns(Array(97, 98, 99, 100, 101).map(_.toByte)).once()
    (client.GetObject _).when(*).returns(Array(97, 98, 99, 100, 101).map(_.toByte)).once()

    val storage = new Prd(client, initMap)
    storage.set("mock_your_key_already")
    storage.set("mock_your_key_already")
    assert(storage.get(new String(Array(97, 98).map(_.toByte))) == new String(Array(99, 100, 101).map(_.toByte)))
    assert(initMap.size() == 4)
  }

  test("prd set success2") {
    // mock here
    val client = stub[Api]
    (client.GetObject _).when(*).returns(Array(97, 98, 99, 100, 101).map(_.toByte)).once()
    (client.GetObject _).when(*).returns(Array(65, 66, 67, 68, 69).map(_.toByte)).once() // ABCDE
    (client.GetObject _).when(*).returns(Array(65, 66, 67, 68, 69, 70).map(_.toByte)).once() // ABCDEF

    val storage = new Prd(client, initMap)
    storage.set("mock_your_key_already")
    storage.set("mock_your_key_already")
    storage.set("mock_your_key_already")
    assert(storage.get(new String(Array(97, 98).map(_.toByte))) == new String(Array(99, 100, 101).map(_.toByte)))
    assert(storage.get(new String(Array(65, 66).map(_.toByte))) == new String(Array(67, 68, 69).map(_.toByte)))
    assert(storage.get(new String(Array(65, 66, 67).map(_.toByte))) == new String(Array(68, 69, 70).map(_.toByte)))
    assert(initMap.size() == 6)
  }

  test("prd set failure") {
    assertThrows[NoSuchElementException] {
      // mock here
      val client = stub[Api]
      (client.GetObject _).when(*).throws(new NoSuchElementException("mock exception")).once()

      val storage = new Prd(client, initMap)
      storage.set("mock_your_key_already")
    }
  }

}
