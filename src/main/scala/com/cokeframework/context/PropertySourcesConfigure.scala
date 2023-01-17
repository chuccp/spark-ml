package com.cokeframework.context

import java.util.Properties

class PropertySourcesConfigure {

  def readProperties(prop: Properties): Unit = {
    var result = new Properties()
    val url = getClass.getResource("/application.properties");
    result.load(url.openStream())
    prop.putAll(result)
  }

}
