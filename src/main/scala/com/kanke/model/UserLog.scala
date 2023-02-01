package com.kanke.model

import com.cokeframework.elasticsearch.annotation.{Document, Field, FieldType, Id}

@Document(indexName = "hbiptv_userlog_index", typeName = "hbiptv_userlog_type")
class UserLog {
  @Id
  @Field(fieldType = FieldType.Text)
  var id: String = _
  @Field(fieldType = FieldType.Text)
  var userId: String = _
  @Field(fieldType = FieldType.Text)
  var videoId: String = _
  @Field(fieldType = FieldType.Long)
  var playTime: Long = _

}
