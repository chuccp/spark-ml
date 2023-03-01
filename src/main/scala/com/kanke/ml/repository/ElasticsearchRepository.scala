package com.kanke.ml.repository

import com.kanke.ml.elasticsearch.ElasticsearchRestTemplate
import com.kanke.ml.elasticsearch.query.{QueryBuilders, Response, Scroll, SearchQuery}
import org.apache.commons.lang3.time.DateUtils
import org.springframework.beans.factory.annotation.{Autowired, Value}
import org.springframework.stereotype.Component

@Component
class ElasticsearchRepository {

  @Value("${elasticsearch.behavior.index}")
  var indexName: String = _
  @Value("${elasticsearch.behavior.type}")
  var typeName: String = _

  @Autowired
  var elasticsearchRestTemplate: ElasticsearchRestTemplate = _



  def queryUserLog[T](pageSize: Int = 20,date:String ,cls: Class[T]): Response[T] = {

   val queryTime =  DateUtils.parseDate(date,"yyyy-MM-dd","yyyyMMdd")

    val searchQuery = SearchQuery.getSearchQuery()
    searchQuery.pageable.pageSize = pageSize
    searchQuery.queryBuilder = QueryBuilders.rangeQueryBuilder("addTime",queryTime.getTime,DateUtils.addDays(queryTime,1).getTime)
    searchQuery.scroll = new Scroll(10)
    elasticsearchRestTemplate.doSearch(indexName, typeName, searchQuery, cls: Class[T])
  }

  def queryUserLog[T](scrollId:String, cls: Class[T]): Response[T] = {
    elasticsearchRestTemplate.doSearchScroll(scrollId,new Scroll(10),cls)
  }

  def queryOneUserLog(id: String): String = {
    ""
  }

}
