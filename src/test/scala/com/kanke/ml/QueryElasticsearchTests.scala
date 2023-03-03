package com.kanke.ml

import com.kanke.ml.model.UserLog
import com.kanke.ml.repository.ElasticsearchRepository
import com.kanke.ml.task.ElasticsearchToLocalLogTask
import com.kanke.ml.util.JsonUtil
import org.apache.commons.io.FileUtils
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

import java.io.File
import java.util.ArrayList
import scala.util.control.Breaks

@SpringBootTest
class QueryElasticsearchTests {


  @Autowired
  var elasticsearchToLocalLogTask: ElasticsearchToLocalLogTask = null

  @Test
  def yyyyyyyy(): Unit = {
    elasticsearchToLocalLogTask.execute()
  }

  @Test
  def AAAAAAA(): Unit = {


  }

}
