package com.kanke.ml.elasticsearch.query

trait QueryBuilder {


  def toQuery(): String

}
