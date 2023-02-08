package com.kanke.ml.elasticsearch.query

import com.google.gson.{JsonObject, JsonParser}

class Response[T](cls: Class[T]) {

  private var rootJson: JsonObject = _

  def this(body: String, cls: Class[T]) {
    this(cls)
    val rootElement = JsonParser.parseString(body);
    if (rootElement.isJsonObject) {
      this.rootJson = rootElement.getAsJsonObject
    }
  }


  def getHits(): Hits[T] = {
    val hitsJsonObject = this.rootJson.getAsJsonObject("hits")
    new Hits[T](this.cls, hitsJsonObject)
  }

}
