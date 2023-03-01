package com.kanke.ml.elasticsearch

import com.kanke.ml.elasticsearch.`type`.ActionType
import com.kanke.ml.elasticsearch.query.{MatchAllQueryBuilder, Page, Query, QueryBuilder, Response, Scroll}


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
    this.doRequest(indexName, typeName, ActionType.SEARCH, null, json, cls)
  }

  def doSearchScroll[T](scrollId: String, scroll: Scroll, cls: Class[T]): Response[T] = {
    val response = restElasticsearchConfig.getHttpServer().requestScroll(scrollId, scroll)
    new Response(response, cls)
  }

  def doSearch[T](indexName: String, typeName: String, query: Query, cls: Class[T]): Response[T] = {
    this.doRequest(indexName, typeName, ActionType.SEARCH, query.getScroll(), query.toQueryString(), cls)
  }

  private def doRequest[T](indexName: String, typeName: String, action: ActionType.ActionType, scroll: Scroll, json: String, cls: Class[T]): Response[T] = {
    val response = restElasticsearchConfig.getHttpServer().request(indexName, typeName, action, scroll, json)
    new Response(response, cls)
  }

}
