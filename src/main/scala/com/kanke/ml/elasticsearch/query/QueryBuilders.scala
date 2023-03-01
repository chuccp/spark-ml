package com.kanke.ml.elasticsearch.query

class QueryBuilders {




}

object QueryBuilders {
  def matchAllQuery(): MatchAllQueryBuilder = {
    new MatchAllQueryBuilder()
  }

  def createQueryBuilders(): QueryBuilders = {
    new QueryBuilders
  }
  def termQuery(fieldName:String,value:String):TermQueryBuilder={
    new TermQueryBuilder(fieldName, value)
  }

  def rangeQueryBuilder(fieldName: String, from:Long,to:Long): RangeQueryBuilder = {
    new RangeQueryBuilder(fieldName, from,to)
  }

}
