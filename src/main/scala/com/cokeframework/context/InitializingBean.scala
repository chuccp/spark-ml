package com.cokeframework.context

trait InitializingBean {

  def afterPropertiesSet(): Unit
}
