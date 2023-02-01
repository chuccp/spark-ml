package com.cokeframework.elasticsearch

import com.cokeframework.elasticsearch.query.SearchResponse
import com.cokeframework.util.HttpUtils

class Client(url: String) {


  def doSearch(query: String): SearchResponse = {

    HttpUtils.postJson(url,query)
    new SearchResponse
  }

}
