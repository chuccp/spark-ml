package com.kanke.ml.elasticsearch


import org.apache.commons.lang3.StringUtils

class RestElasticsearchConfig {
  private var urls = List[String]()

  def addUrls(urls: String*): Unit = {
    urls.foreach(url => {
      if (StringUtils.endsWith(url, "/")) {
        this.urls = this.urls :+ (url)
      } else {
        this.urls = this.urls :+ (url + "/")
      }
    })
  }


  def getHttpServer(): HttpServer = {
    new HttpServer(urls.apply(0))
  }

}
