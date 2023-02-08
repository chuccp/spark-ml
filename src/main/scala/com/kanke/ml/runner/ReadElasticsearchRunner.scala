package com.kanke.ml.runner

import com.kanke.ml.repository.ElasticsearchRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.{ApplicationArguments, ApplicationRunner}
import org.springframework.stereotype.Component

@Component
class ReadElasticsearchRunner extends ApplicationRunner {

  @Autowired
  var elasticsearchRepository: ElasticsearchRepository = _

  override def run(args: ApplicationArguments): Unit = {

//    elasticsearchRepository.queryUserLog(100,2)

  }
}
