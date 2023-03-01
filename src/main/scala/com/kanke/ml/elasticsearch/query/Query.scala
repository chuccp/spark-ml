package com.kanke.ml.elasticsearch.query

trait Query {
  def toQueryString(): String

  def getScroll(): Scroll


}
