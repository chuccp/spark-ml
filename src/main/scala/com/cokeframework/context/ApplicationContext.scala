package com.cokeframework.context

import java.util.Properties
import scala.io.Source

class ApplicationContext {

  var propertySourcesConfigure = new PropertySourcesConfigure()

  def initialize(): Unit = {
    this.initializeProperty()
  }

  def initializeProperty(): Unit = {
    var result = new Properties()
    propertySourcesConfigure.readProperties(result)
  }

}
