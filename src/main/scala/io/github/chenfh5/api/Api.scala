package io.github.chenfh5.api

trait Api {
  def GetObject(key: String): Array[Byte]
  def PutObject(key: String, payload: Array[Byte])
  def DeleteObject(key: String)
  def ListObjects(prefix: String): Any
  def ExistObject(key: String): Boolean
}
