package com.kanke.ml

import org.apache.commons.lang3.reflect.{MethodUtils, TypeUtils}
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

    names.foreach(v=>{

    val obj =   context.getBean(v)

      println("===============================")
      println(obj)
      for(v <- obj.getClass.getMethods ){

        println(v.getName)


      }

    })


  }
}
