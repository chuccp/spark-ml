package com.kanke.ml.task

import com.kanke.ml.annotation.RTask
import com.kanke.ml.lucene.StoreTemplate
import com.kanke.ml.entity.{User, UserLog}
import com.kanke.ml.repository.{ElasticsearchRepository, UserStoreRepository}
import com.kanke.ml.service.RecommendLogService
import com.kanke.ml.util.DocumentUtil
import org.apache.commons.lang3.time.{DateFormatUtils, DateUtils}
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

  @Autowired
  var storeTemplate: StoreTemplate = _
  @Autowired
  var recommendLogService: RecommendLogService = _

  @RTask("ElasticsearchToLocalLogTask2")
  def execute(): Unit = {
    val dateString = DateFormatUtils.format(DateUtils.addDays(new Date(), -1), "yyyy-MM-dd")
    val typeKey = s"ElasticsearchToLocalLogTask_${dateString}"
    val recommendLog = recommendLogService.findRecommendLogS(typeKey)
    this.log.info(s"执行任务：${typeKey}")
    if (recommendLog != null) {
      this.log.info(s"任务：${typeKey} 已经执行，忽略执行")
    } else {
      if (this.execute3(dateString)) {
        recommendLogService.addRecommendLogS(typeKey)
        this.log.info(s"执行任务：${typeKey} 完成")
      } else {
        this.log.info(s"执行任务：${typeKey} 失败，无数据")
      }
    }
  }

  @RTask("ElasticsearchToLocalLogTask2")
  def execute2(date: String): Unit = {
    val typeKey = s"ElasticsearchToLocalLogTask_${date}"
    if (this.execute3(date)) {
      val recommendLog = recommendLogService.findRecommendLogS(typeKey)
      if (recommendLog == null) {
        recommendLogService.addRecommendLogS(typeKey)
      }
      this.log.info(s"执行任务：${typeKey} 完成")
    } else {
      this.log.info(s"执行任务：${typeKey} 失败，无数据")
    }
  }

  def execute3(date: String): Boolean = {
    var hasData = false
    val userMap = new util.HashMap[String, Long]()
    val userLogWrite = storeTemplate.openManualWrite("userLog")
    val pageSize: Int = 10000
    var response = elasticsearchRepository.queryUserLog(pageSize, date, classOf[UserLog])
    val hits = response.getHits()
    val hit = hits.hits()
    log.info("读取数据量：{}", hit.length)
    hit.foreach(v => {
      userLogWrite.writeOrUpdate(new Term("id", v.id), DocumentUtil.convert(v))
      userMap.put(v.userId, v.createTime)
    })
    if (hit.length == pageSize) {
      val loop = new Breaks
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

    this.log.info(s"用户信息保存总数量 ：${userMap.size()}")

    if (userMap.size() > 10000) {
      hasData = true
    }
    if (!userMap.isEmpty) {
      var userList = userMap.asScala.map({
        k => {
          val time = new Date(k._2)
          new User(k._1, k._1, time, time)
        }
      }).toList
      userList = userStoreRepository.filterByAddTime("user", userList)

      this.log.info(s"用户信息可保存数量 ：${userList.size}")

      if (!userList.isEmpty) {
        val userWrite = storeTemplate.openManualWrite("user")
        userList.foreach { v =>
          userWrite.writeOrUpdate(new Term("id", v.id), DocumentUtil.convert(v))
        }
        userWrite.flushAndCommit()
        this.log.info(s"用户信息保存数量完成 ：${userList.size}")
      }
    }
    hasData
  }
}
