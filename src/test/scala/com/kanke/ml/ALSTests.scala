package com.kanke.ml

import org.apache.spark.ml.feature.{IndexToString, RegexTokenizer, StringIndexer}
import org.apache.spark.ml.recommendation.ALS
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.{col, explode}
import org.apache.spark.sql.types.IntegerType
import org.apache.spark.storage.StorageLevel
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class ALSTests {

  @Test
  def yyyyyyyy(): Unit = {

    val spark = SparkSession.builder().master("local").appName("als").getOrCreate()
    import spark.implicits._
    var logdf = spark.read.textFile("user.log").toDF("value")
    val regexTokenizer = new RegexTokenizer().setInputCol("value").setOutputCol("words").setPattern(";");
    var regexTokenizerDf = regexTokenizer.transform(logdf);
    val userVideoDf = regexTokenizerDf.withColumn("userId", $"words".getItem(0)).withColumn("videoId", $"words".getItem(1)).withColumn("score", $"words".getItem(2).cast(IntegerType)).select("userId", "videoId", "score")


    var userIndexer = new StringIndexer().setInputCol("userId").setOutputCol("userIdIndex").fit(userVideoDf)

    var userIndexerdf = userIndexer.transform(userVideoDf).persist(StorageLevel.DISK_ONLY)

    var videoIndexer = new StringIndexer().setInputCol("videoId").setOutputCol("videoIdIndex").fit(userIndexerdf)

    var videoIndexerdf = videoIndexer.transform(userIndexerdf).persist(StorageLevel.DISK_ONLY)


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
    val userRecs = model.recommendForAllUsers(130).persist(StorageLevel.DISK_ONLY)


    var rest = userRecs.withColumn("videoidRating", explode($"recommendations")).drop($"recommendations").persist(StorageLevel.DISK_ONLY)

    rest = rest.withColumn("videoIdIndex", col(("videoidRating"))("videoIdIndex")).drop($"videoidRating").persist(StorageLevel.DISK_ONLY)

    val indexToString = new IndexToString().setInputCol("userIdIndex").setOutputCol("useridString").setLabels(userIndexer.labels)
    val kankeIDToString = new IndexToString().setInputCol("videoIdIndex").setOutputCol("videoidString").setLabels(videoIndexer.labels)
    val recRecult = indexToString.transform(rest).persist(StorageLevel.DISK_ONLY)
    val recRecult2 = kankeIDToString.transform(recRecult).persist(StorageLevel.DISK_ONLY)
    recRecult2.show(false)
  }

}
