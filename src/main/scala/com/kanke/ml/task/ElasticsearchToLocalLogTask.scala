package com.kanke.ml.task

import com.kanke.ml.model.UserLog
import com.kanke.ml.repository.ElasticsearchRepository
import org.apache.commons.io.FileUtils
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.{Autowired, Value}
import org.springframework.stereotype.Component

import java.io.File
import java.util.ArrayList
import scala.util.control.Breaks

@Component
class ElasticsearchToLocalLogTask {
  var log = LoggerFactory.getLogger(classOf[ElasticsearchToLocalLogTask])
  @Autowired
  var elasticsearchRepository: ElasticsearchRepository = _
  @Value("${log.path}")
  var logPath: String = _

  def ok(unit: String): Unit = {

    println(unit)
  }

  def run: Unit = {
    log.info(s"日志保存路径:${logPath}")
    var logFile = new File(logPath, "user.log")
    val loop = new Breaks;
    var pageNo: Int = 1
    loop.breakable {
      while (true) {
        val response = elasticsearchRepository.queryUserLog(100, pageNo, classOf[UserLog])
        pageNo += 1
        val hits = response.getHits()
        val list = new ArrayList[String]()
        val hit = hits.hits()

        println("hit.length:" + hit.length)


        hit.foreach((v) => {
          list.add(s"${v.userId};${v.videoId};${v.playTime}")
        })
        FileUtils.writeLines(logFile, list, true)
        if (hit.length == 0 || hit.length < 100) {
          loop.break
        }
      }
    }
  }


}
