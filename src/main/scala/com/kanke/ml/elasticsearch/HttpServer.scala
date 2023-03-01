package com.kanke.ml.elasticsearch

import com.kanke.ml.elasticsearch.`type`.ActionType
import com.kanke.ml.elasticsearch.query.Scroll
import com.kanke.ml.util.HttpUtils
import org.apache.commons.lang3.StringUtils
import org.slf4j.LoggerFactory

class HttpServer(url: String) {
  val log = LoggerFactory.getLogger(classOf[HttpServer])

  def get(indexName: String, typeName: String, query: String): String = {
    ""
  }

  def request(indexName: String, typeName: String, action: ActionType.ActionType, scroll: Scroll, jsonStr: String): String = {
    this.doRequest(indexName, typeName, action, scroll, jsonStr)
  }

  private def doRequest(indexName: String, typeName: String, action: ActionType.ActionType, scroll: Scroll, jsonStr: String): String = {
    var link = this.url;
    if (StringUtils.isNotEmpty(indexName)) {
      link = link + indexName + "/"
      if (StringUtils.isNotEmpty(typeName)) {
        link = link + typeName + "/"
      }
    }
    link = link + action.toString
    if (action == ActionType.SEARCH) {
      if (scroll != null) {
        link = link + s"?scroll=${scroll.minute}m"
      }
    }
    log.info(s"link:${link}      json:${jsonStr}")
    HttpUtils.postJson(link, jsonStr)
  }

   def requestScroll( scrollId: String, scroll: Scroll): String = {
    var link = this.url;
    link = link + ActionType.SEARCH.toString+"/scroll"
    val jsonStr = s"""{"scroll": "${scroll.minute}m","scroll_id":"${scrollId}"}"""
    log.info(s"link:${link}      json:${jsonStr}")
    HttpUtils.postJson(link, jsonStr)
  }
}
