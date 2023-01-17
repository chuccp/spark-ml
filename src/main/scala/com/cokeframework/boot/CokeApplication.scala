package com.cokeframework.boot

import com.cokeframework.context.ApplicationContext

class CokeApplication {
  var applicationContext: ApplicationContext = new ApplicationContext()

  def run(cls: Class[_], arrays: Array[String]): Unit = {
    applicationContext.initialize()
  }
}

object CokeApplication {
  def run(cls: Class[_], arrays: Array[String]): Unit = {
    new CokeApplication().run(cls, arrays)
  }
}