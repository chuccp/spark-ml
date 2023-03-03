package com.kanke.ml.util

import com.kanke.ml.annotation.RTask
import org.springframework.web.context.WebApplicationContext

import java.util.concurrent.Executors

object MethodUtil {

  val pool = Executors.newFixedThreadPool(5)

  def execute(context: WebApplicationContext, name: String, args: Array[String]): Unit = {
    val names = context.getBeanDefinitionNames
    names.foreach(v => {
      val obj = context.getBean(v)
      for (v <- obj.getClass.getMethods) {
        val rTask = v.getAnnotation(classOf[RTask])
        if (rTask != null) {
          if (args == null || args.isEmpty) {
            if (v.getParameterCount == 0) {
              pool.execute(()=>{
                v.invoke(obj)
              })
            }
          } else {
            if (v.getParameterCount == args.length) {
              pool.execute(() => {
                v.invoke(obj, args: _*)
              })
            }
          }
        }
      }
    })
  }
}
