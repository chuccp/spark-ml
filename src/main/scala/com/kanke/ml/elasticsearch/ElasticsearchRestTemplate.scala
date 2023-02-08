package com.kanke.ml.elasticsearch

import com.kanke.ml.elasticsearch.`type`.ActionType
import com.kanke.ml.elasticsearch.query.{Page, Response}


class ElasticsearchRestTemplate(restElasticsearchConfig: RestElasticsearchConfig) {


  def doGet(indexName: String, typeName: String, query: String): String = {

    restElasticsearchConfig.getHttpServer().get(indexName, typeName, query)
  }

  def doRequest(indexName: String, json: String): String = {

    ""
  }

  def queryForPage[T](indexName: String, typeName: String, json: String, cls: Class[T]): Page[T] = {
    val response = this.doSearch(indexName, typeName, json, cls)
    val hits = response.getHits()
    new Page[T](hits.total(), hits.hits())
  }

  def doSearch[T](indexName: String, typeName: String, json: String, cls: Class[T]): Response[T] = {
    this.doRequest(indexName, typeName, ActionType.SEARCH, json, cls)
  }

  private def doRequest[T](indexName: String, typeName: String, action: ActionType.ActionType, json: String, cls: Class[T]): Response[T] = {
    val response = restElasticsearchConfig.getHttpServer().request(indexName, typeName, action, json)
    new Response(response, cls)
  }

}
