package com.kanke.ml.util

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule

object JsonUtil {

  def ObjectToString(any: Object): String = {
    val mapper = new ObjectMapper
    mapper.registerModule(DefaultScalaModule)
    mapper.writeValueAsString(any)
  }

}
