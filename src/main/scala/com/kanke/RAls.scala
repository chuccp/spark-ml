package com.kanke

import org.apache.spark.ml.feature.{IndexToString, RegexTokenizer, StringIndexer}
import org.apache.spark.ml.recommendation.ALS
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.{col, explode, split}
import org.apache.spark.sql.types.{DataType, IntegerType}
import org.apache.spark.storage.StorageLevel

object RAls {

  def main(array: Array[String]): Unit = {

    val spark = SparkSession.builder().master("local").appName("als").getOrCreate()
    import spark.implicits._
    var logdf = spark.read.textFile("user.log").toDF("value")
    val regexTokenizer = new RegexTokenizer().setInputCol("value").setOutputCol("words").setPattern(";");
    var regexTokenizerDf = regexTokenizer.transform(logdf);
    val userVideoDf = regexTokenizerDf.withColumn("userId", $"words".getItem(0)).withColumn("videoId", $"words".getItem(1)).withColumn("score", $"words".getItem(2).cast(IntegerType)).select("userId", "videoId", "score")


    var userIndexer = new StringIndexer().setInputCol("userId").setOutputCol("userIdIndex").fit(userVideoDf)

    var userIndexerdf = userIndexer.transform(userVideoDf)

    var videoIndexer = new StringIndexer().setInputCol("videoId").setOutputCol("videoIdIndex").fit(userIndexerdf)

    var videoIndexerdf = videoIndexer.transform(userIndexerdf)


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
    val userRecs = model.recommendForAllUsers(130)


    var rest = userRecs.withColumn("videoidRating",explode($"recommendations")).drop($"recommendations")

    rest =  rest.withColumn("videoIdIndex",col(("videoidRating"))("videoIdIndex")).drop($"videoidRating")

    val indexToString = new IndexToString().setInputCol("userIdIndex").setOutputCol("useridString").setLabels(userIndexer.labels)
    val kankeIDToString = new IndexToString().setInputCol("videoIdIndex").setOutputCol("videoidString").setLabels(videoIndexer.labels)
    val recRecult = indexToString.transform(rest)
    val recRecult2 = kankeIDToString.transform(recRecult)
    recRecult2.show(false)



  }

}
