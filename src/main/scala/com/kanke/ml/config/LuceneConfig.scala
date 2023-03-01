package com.kanke.ml.config

import com.kanke.ml.lucene.StoreTemplate
import org.apache.commons.io.FileUtils
import org.springframework.context.annotation.{Bean, Configuration}

import java.io.File

@Configuration
class LuceneConfig {

  @Bean
  def storeTemplate(): StoreTemplate = {
    val indexDir = new File(FileUtils.getTempDirectory, "index");
    println(indexDir.getAbsoluteFile)
    new StoreTemplate(indexDir);
  }
}
