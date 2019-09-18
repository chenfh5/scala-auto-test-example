package io.github.chenfh5.prd

import java.util.concurrent.ConcurrentHashMap

import io.github.chenfh5.api.Api

class Prd(client: Api, data: ConcurrentHashMap[String, String]) { // Storage

  def get(key: String): String = {
    data.get(key)
  }

  def set(obj: String): Unit = {
    val b = client.GetObject(obj)
    val n = b.length
    val key = new String(b.slice(0, n / 2))
    val value = new String(b.slice(n / 2, n))
    data.put(key, value)
  }

}
