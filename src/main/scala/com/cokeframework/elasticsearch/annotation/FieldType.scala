package com.cokeframework.elasticsearch.annotation

object FieldType extends Enumeration {
  type FieldType = Value
  val Text = Value("Text")
  val Integer = Value("Integer")
  val Long = Value("Long")
  val Double = Value("Double")
  val Date = Value("Date")
  val Boolean = Value("Boolean")
  val Keyword = Value("Keyword")
}
