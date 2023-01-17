package com.cokeframework.core

import com.cokeframework.core.Resource.{URL_PROTOCOL_FILE, URL_PROTOCOL_JAR}

import java.net.URL

class Resource(var url: URL) {


  def isJar(): Boolean = {
    val protocol = url.getProtocol
    if (URL_PROTOCOL_JAR.equals(protocol)) {
      true
    } else {
      false
    }
  }

  def isFile(): Boolean = {
    val protocol = url.getProtocol
    if (URL_PROTOCOL_FILE.equals(protocol)) {
      true
    } else {
      false
    }
  }

}

object Resource {
  val URL_PROTOCOL_JAR = "jar"
  val URL_PROTOCOL_FILE = "file"
}
