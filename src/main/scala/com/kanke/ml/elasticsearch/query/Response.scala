package com.kanke.ml.elasticsearch.query

import com.google.gson.{JsonObject, JsonParser}

class Response[T](cls: Class[T]) {

  private var rootJson: JsonObject = _


  def getScrollId(): String = {
    val _scroll_id = this.rootJson.getAsJsonPrimitive("_scroll_id")
    if (_scroll_id != null && !_scroll_id.isJsonNull) {
      return _scroll_id.getAsString
    }
    null
  }

  def this(body: String, cls: Class[T]) {
    this(cls)
    val rootElement = JsonParser.parseString(body);
    if (rootElement.isJsonObject) {
      this.rootJson = rootElement.getAsJsonObject
      val status = this.rootJson.get("status")
      if (status != null && !status.isJsonNull) {
        if (status.getAsInt != 200) {
          throw new IllegalStateException(this.rootJson.toString)
        }
      }
    }
  }


  def getHits(): Hits[T] = {
    val hitsJsonObject = this.rootJson.getAsJsonObject("hits")
    new Hits[T](this.cls, hitsJsonObject)
  }

}
