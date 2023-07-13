package com.kanke.ml

import com.kanke.ml.annotation.RTask
import com.kanke.ml.task.{ElasticsearchToLocalLogTask, RecommendAlsTask}
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.web.context.WebApplicationContext

@SpringBootTest
class TaskTests {


  @Autowired
  var elasticsearchToLocalLogTask:ElasticsearchToLocalLogTask = _
  @Autowired
  var recommendAlsTask:RecommendAlsTask = _
  @Test
  def ZTests(): Unit = {
    elasticsearchToLocalLogTask.execute2("2019-02-15")
  }

  @Test
  def ZTests2(): Unit = {
    recommendAlsTask.run
  }
}
