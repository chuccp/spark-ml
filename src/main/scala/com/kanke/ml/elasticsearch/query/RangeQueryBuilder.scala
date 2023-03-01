package com.kanke.ml.elasticsearch.query

class RangeQueryBuilder(fieldName: String, from: Any, to: Any) extends QueryBuilder {




  override def toQuery(): String = {
    if (from.getClass == classOf[Int] || from.getClass == classOf[Long]) {
      s"""{"range":{ "${fieldName}" : {"gte" : ${from},"lte" : ${to}}}}"""
    } else {
      s"""{"range":{ "${fieldName}" : {"gte" : ${from},"lte" : ${to}}}}"""
    }
  }
}
