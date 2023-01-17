package com.kanke.util

import okhttp3.{MediaType, OkHttpClient, Request, RequestBody}

import scala.util.parsing.json.JSONArray


object HttpUtils {

  def get(url:String):String={
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


  def postJson(url:String,jsonBody:String):String={
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