package com.kanke.ml.model

import com.kanke.ml.lucene.StoreTemplate
import com.kanke.ml.rdd.LuceneRDDs
import org.apache.commons.lang3.time.DateFormatUtils
import org.apache.spark.ml.feature.{RegexTokenizer, StringIndexer}
import org.apache.spark.ml.recommendation.ALS
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types.IntegerType
import org.apache.spark.storage.StorageLevel
import org.springframework.beans.factory.annotation.{Autowired, Value}
import org.springframework.stereotype.Component

import java.io.File
import java.util.Date

@Component
class AlsModel {

  @Autowired
  var storeTemplate: StoreTemplate = _

  @Value("model.save.path")
  private var modelSavePath: String = _

  def run(date: Date): Unit = {
    val dateString = DateFormatUtils.format(date, "yyyyMMdd")
    val savePath = new File(new File(modelSavePath, "als"), dateString)
    if (!savePath.exists()) {
      savePath.mkdirs()
    }
    val spark = SparkSession.builder().master("local").appName("als").getOrCreate()
    import spark.implicits._
    val videoLogDf = LuceneRDDs.create(spark.sparkContext, 100, "userLog", storeTemplate.indexFile.getAbsolutePath).toDF()
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
    model.save(savePath.getAbsolutePath)
  }

}
