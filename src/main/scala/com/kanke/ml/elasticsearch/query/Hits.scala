package com.kanke.ml.elasticsearch.query

import com.google.gson.JsonObject
import com.kanke.ml.util.BeanMetadata

class Hits[T](cls: Class[T], hitsJson: JsonObject) {

  def total(): Long = {
    val total = hitsJson.get("total")
    total.getAsLong
  }

  def hits(): List[T] = {
    val beanMetadata = BeanMetadata.create(cls)
    var list = List[T]()
    val hits = hitsJson.getAsJsonArray("hits")
    val iterator = hits.iterator()
    while (iterator.hasNext) {
      val jsonElement = iterator.next()
      val _source = jsonElement.getAsJsonObject.getAsJsonObject("_source")
      val _id = jsonElement.getAsJsonObject.get("_id").getAsString
      val t = beanMetadata.getValue(_source,_id)
      list = list.+:(t)
    }
    list
  }
}
