package com.kanke.ml.model

import com.kanke.ml.annotation.{FieldType, FieldValue, Id}
import jdk.nashorn.internal.objects.annotations.Setter

import scala.annotation.meta.getter

class UserLog {


  @Id
  @FieldValue(fieldType = FieldType.Auto)
  @getter @Setter
  var id: String = _

  @getter @Setter
  var userId: String = _
  @getter @Setter
  var videoId: String = _
  @getter @Setter
  var playTime: Long = _
}
