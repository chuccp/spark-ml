package com.kanke.ml.config

import com.kanke.ml.elasticsearch.{ElasticsearchRestTemplate, RestElasticsearchConfig}
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.{Bean, Configuration}

@Configuration
class ElasticsearchConfigure {

  @Value("${elasticsearch.host}")
  private var host_ : String = _

  @Value("${elasticsearch.version:default}")
  private var version_ : String = _

  @Bean
  def elasticsearchRestTemplate(): ElasticsearchRestTemplate = {
    val restElasticsearchConfig = new RestElasticsearchConfig()
    restElasticsearchConfig.addUrls(host_)
    restElasticsearchConfig.version_ = version_
    new ElasticsearchRestTemplate(restElasticsearchConfig);
  }

}
