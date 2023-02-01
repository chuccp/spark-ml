package com.kanke.config

import com.cokeframework.context.annotation.Configuration
import com.cokeframework.elasticsearch.annotation.Bean
import com.cokeframework.elasticsearch.config.{RestElasticsearchConfig, RestProperties}

@Configuration
class ElasticsearchConfig {
  
  @Bean
  def restElasticsearchConfig(): RestElasticsearchConfig = {
    val restProperties: RestProperties = new RestProperties()
    new RestElasticsearchConfig(restProperties)
  }

}
