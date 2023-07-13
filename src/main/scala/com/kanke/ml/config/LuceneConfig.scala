package com.kanke.ml.config

import com.kanke.ml.lucene.StoreTemplate
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.{Bean, Configuration}

import java.io.File

@Configuration
class LuceneConfig {


  @Value("${user.log.path}")
  var indexPah: String = _

  @Bean
  def storeTemplate(): StoreTemplate = {
    val indexDir = new File(indexPah, "index");
    new StoreTemplate(indexDir);
  }
}
