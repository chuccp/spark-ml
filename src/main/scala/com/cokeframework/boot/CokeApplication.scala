package com.cokeframework.boot

import com.cokeframework.context.ApplicationContext

class CokeApplication {
  var applicationContext: ApplicationContext = new ApplicationContext()

  def run(cls: Class[_], arrays: Array[String]): Unit = {
    this.run(cls, arrays, null)
  }

  def run(cls: Class[_], instance: Any): Unit = {
    this.run(cls, null, instance)
  }

  def run(cls: Class[_], arrays: Array[String], instance: Any): Unit = {
    applicationContext.initialize(cls, arrays, instance)
  }
}

object CokeApplication {
  def run(cls: Class[_], arrays: Array[String]): Unit = {
    run(cls, arrays, null)
  }

  def run(cls: Class[_], arrays: Array[String], instance: Any): Unit = {
    new CokeApplication().run(cls, arrays, instance)
  }

  def run(instance: Any): Unit = {
    run(instance.getClass, null, instance)
  }
}