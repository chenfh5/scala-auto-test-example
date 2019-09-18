package io.github.chenfh5.api

class EsClient extends Api {
  override def GetObject(key: String): Array[Byte] = {
    println(s"EsClient GetObject key=%s", key)
    Array(128, 128, 128).map(_.toByte)
  }

  override def PutObject(key: String, payload: Array[Byte]): Unit = ???

  override def DeleteObject(key: String): Unit = ???

  override def ListObjects(prefix: String): Any = ???

  override def ExistObject(key: String): Boolean = ???
}
