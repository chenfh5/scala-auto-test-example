package io.github.chenfh5.api

import java.nio.charset.StandardCharsets

import awscala.Region
import com.amazonaws.services.s3.model.{GetObjectRequest, PutObjectRequest}
import com.amazonaws.util.IOUtils
import jp.co.bizreach.s3scala.S3

class S3Client(conf: S3Config) extends Api {
  private val s3 = S3(accessKeyId = conf.accessKeyId, secretAccessKey = conf.secretAccessKey)(conf.region)

  override def GetObject(key: String): Array[Byte] = {
    val result = s3.getObject(new GetObjectRequest(conf.bucket, key))
    IOUtils.toByteArray(result.getObjectContent)
  }

  override def PutObject(key: String, payload: Array[Byte]): Unit = {
    s3.putObject(new PutObjectRequest(conf.bucket, key, new String(payload, StandardCharsets.UTF_8)))
  }

  override def DeleteObject(key: String): Unit = ???

  override def ListObjects(prefix: String): Any = ???

  override def ExistObject(key: String): Boolean = ???
}

object S3Client {
  def apply(conf: S3Config): S3Client = new S3Client(conf)
}

case class S3Config(accessKeyId: String, secretAccessKey: String, region: Region, bucket: String)
