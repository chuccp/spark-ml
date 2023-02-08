package com.kanke.ml.util

import com.google.gson.JsonObject

object JsonBeanUtils {

  def JsonToObject[T](jsonObject: JsonObject, cls: Class[T]): T = {
    var beanMetadata = BeanMetadata.create(cls)
    beanMetadata.getValue(jsonObject)
  }


}
