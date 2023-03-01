package com.kanke.ml.elasticsearch.query

class TermQueryBuilder(fieldName: String, value: Any) extends QueryBuilder {



  override def toQuery(): String = {

    if (value.getClass == classOf[String]) {
      s"""{"term":{"${fieldName}":"${value}"}}"""
    } else {
      s"""{"term":{"${fieldName}":"${value}"}}"""
    }
  }
}
