package com.kanke

import com.cokeframework.boot.test.CokeJUnit4ClassRunner
import com.cokeframework.context.annotation.Autowired
import com.kanke.es.UserLogElasticsearchRepository
import org.junit.{Before, Test}


class ESTest {

  @Autowired
  var userLogElasticsearchRepository: UserLogElasticsearchRepository = _

  @Before
  def before(): Unit = {

    CokeJUnit4ClassRunner.run(this)

  }

  @Test
  def fun(): Unit = {

    println("---------------")

  }
}
