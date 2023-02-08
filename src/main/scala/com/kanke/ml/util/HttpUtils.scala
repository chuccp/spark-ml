package com.kanke.ml.util

import okhttp3.{MediaType, OkHttpClient, Request, RequestBody}
import org.slf4j.LoggerFactory

class HttpUtils

object HttpUtils {
  val log = LoggerFactory.getLogger(classOf[HttpUtils])

  def get(url: String): String = {
    val client = new OkHttpClient()
    val request = new Request.Builder().url(url).build()
    try {
      val response = client.newCall(request).execute()
      response.body().string()
    } catch {
      case ex: Exception =>
        ex.printStackTrace()
        ""
    }
  }


  def postJson(url: String, jsonBody: String): String = {
    val client = new OkHttpClient()
    val body = RequestBody.create(jsonBody, MediaType.get("application/json; charset=utf-8"))
    val request = new Request.Builder().url(url).post(body).build()
    try {
      val response = client.newCall(request).execute()
      response.body().string()
    } catch {
      case ex: Exception =>
        ex.printStackTrace()
        ""
    }
  }
}
