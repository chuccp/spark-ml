package com.kanke.ml.elasticsearch


import org.apache.commons.lang3.{RandomUtils, StringUtils}

class RestElasticsearchConfig {
  var urls = List[String]()

  var index_ : String = _

  var type_ : String = _

  var version_ : String = _

  def addUrls(urls: String*): Unit = {
    urls.foreach(url => {
      if (StringUtils.endsWith(url, "/")) {
        this.urls = this.urls :+ url
      } else {
        this.urls = this.urls :+ (url + "/")
      }
    })
  }


  def getHttpServer(): HttpServer = {
    val index = RandomUtils.nextInt(0, urls.length)
    new HttpServer(urls.apply(index))
  }

}
