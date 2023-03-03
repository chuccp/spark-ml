package com.kanke.ml

import com.kanke.ml.annotation.RTask
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.web.context.WebApplicationContext

@SpringBootTest
class TaskTests {

  @Autowired
  var context: WebApplicationContext = _

  @Test
  def ZTests(): Unit = {
    val names = context.getBeanDefinitionNames
    names.foreach(v => {
      val obj = context.getBean(v)
      for (v <- obj.getClass.getMethods) {
        val rTask = v.getAnnotation(classOf[RTask])
        if (rTask != null) {
          println(rTask.value() + "  " + v.getParameterCount)
          if (v.getParameterCount == 0) {
            v.invoke(obj)
          }
        }
      }
    })
  }
}
