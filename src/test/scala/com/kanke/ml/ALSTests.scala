package com.kanke.ml

import org.apache.spark.ml.feature.{IndexToString, RegexTokenizer, StringIndexer}
import org.apache.spark.ml.recommendation.ALS
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.{col, collect_set, concat_ws, explode}
import org.apache.spark.sql.types.IntegerType
import org.apache.spark.storage.StorageLevel
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest

import java.io.File

@SpringBootTest
class ALSTests {

  @Value("${log.path}")
  var logPath: String = _

  @Test
  def ZTests(): Unit = {
    val spark = SparkSession.builder().master("local").appName("als").getOrCreate()
    import spark.implicits._
    val logFile = new File(logPath, "user.log")

    println(logFile.getAbsolutePath)

    val logDf = spark.read.textFile(logFile.getAbsolutePath).toDF("value")
    val regexTokenizer = new RegexTokenizer().setInputCol("value").setOutputCol("words").setPattern(";");
    val regexTokenizerDf = regexTokenizer.transform(logDf).drop("value")

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

    val userRecs = model.recommendForAllUsers(3).persist(StorageLevel.MEMORY_ONLY)

    //    userRecs.show(false)


    var rest = userRecs.withColumn("videoidRating", explode($"recommendations")).drop($"recommendations").persist(StorageLevel.MEMORY_ONLY)
    rest = rest.withColumn("videoIdIndex", col("videoidRating")("videoIdIndex")).drop($"videoidRating").persist(StorageLevel.MEMORY_ONLY)
    val indexToString = new IndexToString().setInputCol("userIdIndex").setOutputCol("useridString").setLabels(userIndexer.labelsArray.head)
    val kankeIDToString = new IndexToString().setInputCol("videoIdIndex").setOutputCol("videoidString").setLabels(videoIndexer.labelsArray.head)
    val recRecult = indexToString.transform(rest).persist(StorageLevel.MEMORY_ONLY)
    val recRecult2 = kankeIDToString.transform(recRecult).persist(StorageLevel.MEMORY_ONLY)


    val recRecult3 = recRecult2.groupBy("useridString").agg(collect_set("videoidString").as("vvv")).withColumn("videoids", concat_ws(";", $"vvv")).drop("vvv")

    recRecult3.show(false)


    recRecult3.foreach((v) => {
      println(v.get(0) + "=====" + v.get(1))
    })


  }
}
