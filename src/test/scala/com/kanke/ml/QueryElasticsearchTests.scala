package com.kanke.ml

import com.kanke.ml.elasticsearch.query.Query
import com.kanke.ml.model.UserLog
import com.kanke.ml.repository.ElasticsearchRepository
import com.kanke.ml.util.JsonUtil
import org.apache.commons.io.FileUtils
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

import java.io.File
import java.util.ArrayList
import scala.util.control.Breaks

@SpringBootTest
class QueryElasticsearchTests {

  @Autowired
  var elasticsearchRepository: ElasticsearchRepository = null

  @Test
  def yyyyyyyy(): Unit = {
    var logFile = new File("C:\\Users\\cao\\Documents\\svn", "user.log")
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

  @Test
  def AAAAAAA(): Unit = {

    val query = new Query
    query.fileName = 1


    println(JsonUtil.ObjectToString(query))


  }

}
