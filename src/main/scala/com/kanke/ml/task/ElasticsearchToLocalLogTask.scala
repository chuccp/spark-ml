package com.kanke.ml.task

import com.kanke.ml.lucene.StoreTemplate
import com.kanke.ml.model.{User, UserLog}
import com.kanke.ml.repository.{ElasticsearchRepository, UserStoreRepository}
import com.kanke.ml.util.DocumentUtil
import org.apache.lucene.index.Term
import org.apache.spark.internal.Logging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import java.util
import java.util.Date
import scala.collection.JavaConverters._
import scala.util.control.Breaks

@Component
class ElasticsearchToLocalLogTask extends Logging {

  @Autowired
  var elasticsearchRepository: ElasticsearchRepository = _
  @Autowired
  var userStoreRepository: UserStoreRepository = _

  def ok(unit: String): Unit = {

    println(unit)
  }

  @Autowired
  var storeTemplate: StoreTemplate = _

  def run: Unit = {
    val userMap = new util.HashMap[String, Long]()
    val userLogWrite = storeTemplate.openManualWrite("userLog")
    val pageSize: Int = 10000
    var response = elasticsearchRepository.queryUserLog(pageSize, "2019-02-15", classOf[UserLog])
    val hits = response.getHits()
    val hit = hits.hits()
    log.info("读取数据量：{}", hit.length)
    hit.foreach(v => {
      userLogWrite.writeOrUpdate(new Term("id", v.id), DocumentUtil.convert(v))
      userMap.put(v.userId, v.createTime)
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
            userLogWrite.writeOrUpdate(new Term("id", v.id), DocumentUtil.convert(v))
            userMap.put(v.userId, v.createTime)
          })
          if (hit.length == 0 || hit.length < pageSize) {
            loop.break
          }
        }
      }
    }
    userLogWrite.flushAndCommit()
    val userWrite = storeTemplate.openManualWrite("user")
    val userList = userMap.asScala.map({
      k => {
        val time = new Date(k._2)
        new User(k._1, k._1, time, time)
      }
    }).toList
    userStoreRepository.filterByAddTime("user", userList).foreach { v =>
      userWrite.writeOrUpdate(new Term("id", v.id), DocumentUtil.convert(v))
    }
    userWrite.flushAndCommit()
  }
}
