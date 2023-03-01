package com.kanke.ml.repository

import com.kanke.ml.lucene.IndexFactory
import com.kanke.ml.model.UserLog
import org.apache.commons.io.FileUtils
import org.apache.lucene.document.{Document, Field, NumericDocValuesField, StringField}
import org.apache.lucene.index.Term
import org.springframework.stereotype.Component

import java.io.File

@Component
class StoreRepository {


  private def convert(userLog: UserLog): Document = {
    val document = new Document()
    val idStringField = new StringField("id", userLog.id, Field.Store.YES)
    document.add(idStringField)
    val videoTypeStringField = new StringField("videoType", userLog.videoType, Field.Store.YES)
    document.add(videoTypeStringField)
    val videoIdStringField = new StringField("videoId", userLog.videoId, Field.Store.YES)
    document.add(videoIdStringField)
    val playTimeStringField = new NumericDocValuesField("playTime", userLog.playTime)
    document.add(playTimeStringField)
    val userIdStringField = new StringField("userId", userLog.userId, Field.Store.YES)
    document.add(userIdStringField)
    document
  }

  def open(): Unit = {

  }

  def insert(userLog: UserLog): Unit = {
    val indexPath = new File(FileUtils.getTempDirectory, "index")
    val indexFactory = new IndexFactory(indexPath)
    val indexWriter = indexFactory.getIndexWriter("UserLog")
    val document = convert(userLog)
    indexWriter.updateDocument(new Term("id", userLog.id), document)
    indexWriter.flush
    indexWriter.commit
  }

  def inserts(userLogs: List[UserLog]): Unit = {
    val indexPath = new File(FileUtils.getTempDirectory, "index")
    val indexFactory = new IndexFactory(indexPath)
    val indexWriter = indexFactory.getIndexWriter("UserLog")
    for (u <- userLogs) {
      val document = convert(u)
      indexWriter.updateDocument(new Term("id", u.id), document)
    }
    indexWriter.flush
    indexWriter.commit
  }
}
