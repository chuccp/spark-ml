package com.kanke.ml.task

import com.kanke.ml.lucene.StoreTemplate
import com.kanke.ml.model.UserLog
import com.kanke.ml.repository.{ElasticsearchRepository, StoreRepository}
import org.apache.lucene.document.{Document, Field, NumericDocValuesField, StringField}
import org.apache.lucene.index.Term
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.{Autowired, Value}
import org.springframework.stereotype.Component

import java.util
import java.util.ArrayList
import scala.util.control.Breaks

@Component
class ElasticsearchToLocalLogTask {
  var log = LoggerFactory.getLogger(classOf[ElasticsearchToLocalLogTask])
  @Autowired
  var elasticsearchRepository: ElasticsearchRepository = _

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

  def ok(unit: String): Unit = {

    println(unit)
  }

  @Autowired
  var storeTemplate: StoreTemplate = _

  def run: Unit = {

    val userLogWrite = storeTemplate.openManualWrite("userLog")

    val pageSize: Int = 10000
    var response = elasticsearchRepository.queryUserLog(pageSize, "2019-02-15", classOf[UserLog])
    val hits = response.getHits()
    val hit = hits.hits()
    log.info("读取数据量：{}", hit.length)
    hit.foreach(v => {
      userLogWrite.writeOrUpdate(new Term("id", v.id),this.convert(v))
    })
    if (hit.length == pageSize) {
      val loop = new Breaks;
      loop.breakable {
        while (true) {
          response = elasticsearchRepository.queryUserLog(response.getScrollId(), classOf[UserLog])
          val hits = response.getHits()
          val hit = hits.hits()
          log.info("读取数据量：{}", hit.length)
          hit.foreach(v => {
            userLogWrite.writeOrUpdate(new Term("id", v.id),this.convert(v))
          })
          if (hit.length == 0 || hit.length < pageSize) {
            loop.break
          }
        }
      }
    }

    userLogWrite.flushAndCommit()

  }
}
