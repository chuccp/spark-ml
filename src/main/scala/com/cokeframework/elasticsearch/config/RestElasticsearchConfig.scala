package com.cokeframework.elasticsearch.config

import com.cokeframework.elasticsearch.Client

class RestElasticsearchConfig(restProperties: RestProperties) {


  def getClient(): Client = {
    val url: String = restProperties.uris.apply(0)
    new Client(url)
  }


}
