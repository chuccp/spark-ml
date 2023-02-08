package com.kanke.ml.repository

import com.kanke.ml.elasticsearch.ElasticsearchRestTemplate
import com.kanke.ml.elasticsearch.query.Response
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class ElasticsearchRepository {

  val indexName: String = "hbiptv_userlog_index"

  val typeName: String = "hbiptv_userlog_type"

  @Autowired
  var elasticsearchRestTemplate: ElasticsearchRestTemplate = _

  def queryUserLog[T](pageSize: Int = 20, pageNo: Int = 1, cls: Class[T]): Response[T] = {
    var from = pageSize * (pageNo - 1)
    if (from < 0) {
      from = 0
    }
    val queryString = s"""{"query":{"match_all":{}},"from":"${from}","size":"${pageSize}"}""";
   elasticsearchRestTemplate.doSearch(indexName, typeName, queryString, cls: Class[T])
  }

  def queryOneUserLog(id: String): String = {
    ""
  }

}
