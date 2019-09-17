# Overview
try some scala proto buffer auto generate using sbt `compile`, so as to test the PB features,
1. field number/index is the most important
2. if field number not change, but field name change, then output would using the new field name

# Versions
- scalapb, 0.9.0
- sbt-protoc, 0.99.23

# Quickstart
- modify `person.proto`
- run `compile` in sbt shell

# Reference
- [ScalaPB](https://github.com/scalapb/ScalaPB)
- [google protobuf简介及使用](https://blog.gwq5210.cn/2016/07/30/google%20protobuf%E7%AE%80%E4%BB%8B/)
