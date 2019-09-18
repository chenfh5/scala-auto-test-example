package io.github.chenfh5

import java.util.concurrent.ConcurrentHashMap

import io.github.chenfh5.api.Api
import io.github.chenfh5.prd.Prd
import org.scalameter.api._
import org.scalamock.scalatest.MockFactory

import scala.util.Random

object RangeBenchmark extends Bench.LocalTime with MockFactory {
  // init
  val initMap = new ConcurrentHashMap[String, String]()
  initMap.put("k1", "v1")
  initMap.put("k2", "v2")
  initMap.put("your_key", "your_value")

  // mock here
  val client = stub[Api]
  (client.GetObject _).when(*).returns(Array(getRand, getRand, getRand).map(_.toByte)).once()
  (client.GetObject _).when(*).returns(Array(getRand, getRand, getRand).map(_.toByte)).once()
  (client.GetObject _).when(*).returns(Array(getRand, getRand, getRand).map(_.toByte)).once()
  (client.GetObject _).when(*).returns(Array(getRand, getRand, getRand).map(_.toByte)).once()
  (client.GetObject _).when(*).returns(Array(getRand, getRand, getRand).map(_.toByte)).atLeastOnce()

  private def getRand: Int = {
    new Random().between(65, 91)
  }

  val storage = new Prd(client, initMap)

  // bm-init
  val sizes = Gen.range("size")(300000, 1500000, 300000)
  val arrays = for (sz <- sizes) yield (0 until sz).toArray

  // bm-exec
  performance.of("Array") in {
    measure.method("get") in {
      using(arrays) in { _ =>
        storage.get("mock_your_key_already")
      }
    }

    measure.method("set") in {
      using(arrays) in { _ =>
        storage.set("mock_your_key_already")
      }
    }
  }

}
