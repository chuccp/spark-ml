package com.kanke.ml.task

import com.kanke.ml.annotation.RTask
import com.kanke.ml.lucene.StoreTemplate
import com.kanke.ml.rdd.LuceneRDDs
import com.kanke.ml.repository.RedisRespository
import com.kanke.ml.service.RecommendLogService
import org.apache.commons.lang3.time.{DateFormatUtils, DateUtils}
import org.apache.spark.internal.Logging
import org.apache.spark.ml.feature.{IndexToString, RegexTokenizer, StringIndexer}
import org.apache.spark.ml.recommendation.ALS
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.{col, collect_set, concat_ws, explode}
import org.apache.spark.sql.types.IntegerType
import org.apache.spark.storage.StorageLevel
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import java.util.Date

@Component
class RecommendAlsTask extends Logging {

  @Autowired
  var storeTemplate: StoreTemplate = _
  @Autowired
  var redisRespository: RedisRespository = _

  @Autowired
  var recommendLogService: RecommendLogService = _

  var redisKey = "itu_als"

  @RTask("RecommendAlsTask")
  def execute(): Unit = {
    val dateString = DateFormatUtils.format(DateUtils.addDays(new Date(), -1), "yyyy-MM-dd")
    val typeKey = s"ElasticsearchToLocalLogTask_${dateString}"

    val recommendLog = recommendLogService.findRecommendLogS(typeKey)
    this.log.info(s"执行任务：${typeKey}")
    if (recommendLog != null) {
      this.log.info(s"任务：${typeKey} 已经执行，忽略执行")
    } else {
      this.run
      recommendLogService.addRecommendLogS(typeKey)
    }

  }

  def run: Unit = {
    val spark = SparkSession.builder().master("local").appName("als").getOrCreate()
    import spark.implicits._
    val videoLogDf = LuceneRDDs.create(spark.sparkContext,100, 100, "userLog", storeTemplate.indexFile.getAbsolutePath).toDF()
    videoLogDf.show(false)
    val regexTokenizer = new RegexTokenizer().setInputCol("value").setOutputCol("words").setPattern(";");
    val regexTokenizerDf = regexTokenizer.transform(videoLogDf).drop("value")
    val userVideoDf = regexTokenizerDf.withColumn("userId", $"words".getItem(0))
      .withColumn("videoId", $"words".getItem(1))
      .withColumn("score", $"words".getItem(2).cast(IntegerType)).drop("words")
    val userIndexer = new StringIndexer().setInputCol("userId").setOutputCol("userIdIndex").fit(userVideoDf)
    val userIndexerdf = userIndexer.transform(userVideoDf).persist(StorageLevel.MEMORY_ONLY)
    val videoIndexer = new StringIndexer().setInputCol("videoId").setOutputCol("videoIdIndex").fit(userIndexerdf)
    val videoIndexerdf = videoIndexer.transform(userIndexerdf).persist(StorageLevel.MEMORY_ONLY)

    videoIndexerdf.show(false)

    val als = new ALS()
      .setMaxIter(10)
      .setRegParam(0.05)
      .setRank(25)
      .setUserCol("userIdIndex")
      .setItemCol("videoIdIndex")
      .setRatingCol("score")
      .setNonnegative(true)
      .setColdStartStrategy("drop")
      .setSeed(1l);
    val model = als.fit(videoIndexerdf)
    model.save("")



    model.userFactors.show(false)
    model.itemFactors.show(false)

//    model.save("")



//
    val userRecs = model.recommendForAllUsers(25).persist(StorageLevel.MEMORY_ONLY)
    var rest = userRecs.withColumn("videoidRating", explode($"recommendations")).drop($"recommendations").persist(StorageLevel.MEMORY_ONLY)
    rest = rest.withColumn("videoIdIndex", col("videoidRating")("videoIdIndex")).drop($"videoidRating").persist(StorageLevel.MEMORY_ONLY)
    val indexToString = new IndexToString().setInputCol("userIdIndex").setOutputCol("useridString").setLabels(userIndexer.labelsArray.head)
    val kankeIDToString = new IndexToString().setInputCol("videoIdIndex").setOutputCol("videoidString").setLabels(videoIndexer.labelsArray.head)
    val recRecult = indexToString.transform(rest).persist(StorageLevel.MEMORY_ONLY)
    val recRecult2 = kankeIDToString.transform(recRecult).persist(StorageLevel.MEMORY_ONLY)
    val recRecult3 = recRecult2.groupBy("useridString").agg(collect_set("videoidString").as("vvv")).withColumn("videoids", concat_ws(";", $"vvv")).drop("vvv")
    recRecult3.show(false)
    val recMap = new java.util.HashMap[String, String]()
    val iterator = recRecult3.toLocalIterator()
    while (iterator.hasNext) {
      val row = iterator.next()
      recMap.put(row.get(0).toString, row.get(1).toString)
      if (recMap.size >= 1000) {
        redisRespository.setHM(redisKey, recMap)
        recMap.clear()
      }
    }
    if (!recMap.isEmpty) {
      redisRespository.setHM(redisKey, recMap)
    }

  }

}
