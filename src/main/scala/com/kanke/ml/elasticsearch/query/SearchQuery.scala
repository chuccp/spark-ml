package com.kanke.ml.elasticsearch.query

class SearchQuery extends Query {

  var queryBuilder: QueryBuilder = _
  var pageable: Pageable = Pageable.defaultPageable()
  var scroll: Scroll = _

  override def toQueryString(): String = {
    var query = ""
    if (queryBuilder != null) {
      query = queryBuilder.toQuery()
    } else {
      query = """"{match_all":{}}"""
    }
    val from = pageable.pageNo * pageable.pageSize
    val pageSize = pageable.pageSize
    if(scroll==null){
      s"""{"query":${query},"from":"${from}","size":"${pageSize}"}"""
    }else{
      s"""{"query":${query},"size":"${pageSize}"}"""
    }

  }

  override def getScroll(): Scroll = {
    scroll
  }
}

object SearchQuery {


  def getSearchQuery(): SearchQuery = {
    new SearchQuery
  }

}
