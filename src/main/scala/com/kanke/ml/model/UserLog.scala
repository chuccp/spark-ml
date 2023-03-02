package com.kanke.ml.model

import com.kanke.ml.annotation.{FieldValue, Id}

class UserLog {

  def this(id: String, userId: String, videoId: String, videoType: String, playTime: Long) {
    this()
  }


  @Id
  var id: String = _

  @FieldValue(fieldName = "userId")
  var userId: String = _

  @FieldValue(fieldName = "kankeId")
  var videoId: String = _

  @FieldValue(fieldName = "videoType")
  var videoType: String = _

  @FieldValue(fieldName = "playTime")
  var playTime: Long = _


  @FieldValue(fieldName = "addTime")
  var createTime: Long = _
}
