package com.kanke.ml.util

import com.kanke.ml.entity.{User, UserLog}
import org.apache.lucene.document.DateTools.Resolution
import org.apache.lucene.document._

object DocumentUtil {

  def convert(userLog: UserLog): Document = {
    val document = new Document()

//    val store = Field.Store.YES

    val idStringField = new StringField("id", userLog.id, store)
    document.add(idStringField)
    val videoTypeStringField = new StringField("videoType", userLog.videoType, store)
    document.add(videoTypeStringField)
    val videoIdStringField = new StringField("videoId", userLog.videoId, store)
    document.add(videoIdStringField)
    val playTimeNumberField = new StoredField("playTime", userLog.playTime)
    document.add(playTimeNumberField)
    val userIdStringField = new StringField("userId", userLog.userId, store)
    document.add(userIdStringField)
    val createTimeNumberField = new StoredField("createTime", userLog.createTime)
    document.add(createTimeNumberField)
    document
  }

  def convert(user: User): Document = {
    val document = new Document()
    val store = Field.Store.YES
    val idStringField = new StringField("id", user.id, store)
    document.add(idStringField)
    val userIdStringField = new StringField("userId", user.userId, store)
    document.add(userIdStringField)
    val lastDateStringField = new StringField("lastDate", DateTools.dateToString(user.lastDate, Resolution.DAY), store)
    document.add(lastDateStringField)
    val lastTimeStringField = new StringField("lastTime", DateTools.dateToString(user.lastTime, Resolution.SECOND), store)
    document.add(lastTimeStringField)
    document
  }

  def convert(document: Document): UserLog = {
    val userLog = new UserLog()
    userLog.id = document.get("id")
    userLog.userId = document.get("userId")
    userLog.videoId = document.get("videoId")
    userLog.playTime = document.getField("playTime").numericValue().longValue()
    userLog.videoType = document.get("videoType")
    userLog
  }

  def convertToUser(document: Document): User = {
    val lastDate = document.get("lastDate")
    val lastTime = document.get("lastTime")
    val user = new User(document.get("id"), document.get("userId"), DateTools.stringToDate(lastTime), DateTools.stringToDate(lastDate))
    user
  }

  def convertToString(document: Document): String = {
    s"${document.get("userId")};${document.get("videoType")}_${document.get("videoId")};${document.get("playTime")}"
  }
}
