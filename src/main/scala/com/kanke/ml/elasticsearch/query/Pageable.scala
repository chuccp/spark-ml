package com.kanke.ml.elasticsearch.query

class Pageable {
  var pageNo: Int = 1
  var pageSize: Int = 20
}

object Pageable {
  def defaultPageable(): Pageable = {
    new Pageable
  }

}
