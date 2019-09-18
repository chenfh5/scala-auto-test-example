name := "scala-auto-test-example"

version := "0.1"

scalaVersion := "2.13.0"

libraryDependencies ++= compileSeq
libraryDependencies ++= testSeq

val compileSeq = Seq("jp.co.bizreach" % "aws-s3-scala_2.12" % "0.0.15")

val testSeq = Seq(
  "org.scalatest"     %% "scalatest"  % "3.0.8" % Test,
  "org.scalamock"     %% "scalamock"  % "4.4.0" % Test,
  "com.storm-enroute" %% "scalameter" % "0.19"  % Test)
