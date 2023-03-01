package com.kanke.ml.elasticsearch.query

class MatchAllQueryBuilder extends QueryBuilder {

  val NAME = "match_all"

  override def toQuery(): String = {
     """{"match_all":{}}"""
  }
}
