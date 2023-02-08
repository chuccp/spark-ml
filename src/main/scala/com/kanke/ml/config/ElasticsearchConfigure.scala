package com.kanke.ml.config

import com.kanke.ml.elasticsearch.{ElasticsearchRestTemplate, RestElasticsearchConfig}
import org.springframework.context.annotation.{Bean, Configuration}

@Configuration
class ElasticsearchConfigure {

  @Bean
  def elasticsearchRestTemplate(): ElasticsearchRestTemplate = {
    val restElasticsearchConfig = new RestElasticsearchConfig()
    restElasticsearchConfig.addUrls("http://192.168.1.131:9200")
    new ElasticsearchRestTemplate(restElasticsearchConfig);
  }

}
