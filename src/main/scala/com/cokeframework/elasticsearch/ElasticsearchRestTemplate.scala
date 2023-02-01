package com.cokeframework.elasticsearch

import com.cokeframework.elasticsearch.config.RestElasticsearchConfig
import com.cokeframework.elasticsearch.query.{GetQuery, Page, SearchQuery, SearchResponse}

class ElasticsearchRestTemplate(restElasticsearchConfig: RestElasticsearchConfig) {


  def queryForObject[T](query: GetQuery, clazz: Class[T]): T = {

    clazz.newInstance()

  }

  def queryForPage[T](query: SearchQuery, clazz: Class[T]): Page[T] = {
    new Page[T]
  }

  private def doSearch(query: String): SearchResponse = {
    restElasticsearchConfig.getClient().doSearch(query)
  }
}
