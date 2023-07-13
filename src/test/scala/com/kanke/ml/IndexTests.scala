package com.kanke.ml

import org.apache.spark.ml.feature.{NGram, Normalizer, StringIndexer, StringIndexerModel}
import org.apache.spark.ml.linalg.Vectors
import org.apache.spark.sql.SparkSession
import org.junit.Test

class IndexTests {

  @Test
  def writeTests(): Unit = {
    val spark = SparkSession.builder().master("local").appName("als").getOrCreate()
    //    import spark.implicits._
    val df = spark.createDataFrame(
      Seq((0, "a"), (1, "b"), (2, "c"), (3, "a"), (4, "a"), (5, "c"))
    ).toDF("id", "category")
    df.show(false)

    val indexer = new StringIndexer().setInputCol("category").setOutputCol("categoryIndex")



    val indexed = indexer.fit(df)
    indexed.transform(df)
    indexed.write.overwrite().save("C:\\Users\\cooge\\Documents\\SVN\\StringIndexer")
  }
  @Test
  def readTests(): Unit = {

    val spark = SparkSession.builder().master("local").appName("als").getOrCreate()
    val dataFrame = spark.createDataFrame(Seq(
      (0, Vectors.dense(1.0, 0.5, -1.0)),
      (1, Vectors.dense(2.0, 1.0, 1.0)),
      (2, Vectors.dense(4.0, 10.0, 2.0))
    )).toDF("id", "features")

    // Normalize each Vector using $L^1$ norm.
    val normalizer = new Normalizer()
      .setInputCol("features")
      .setOutputCol("normFeatures")
      .setP(1.0)

    val l1NormData = normalizer.transform(dataFrame)
    println("Normalized using L^1 norm")
    l1NormData.show()

    // Normalize each Vector using $L^\infty$ norm.
    val lInfNormData = normalizer.transform(dataFrame, normalizer.p -> Double.PositiveInfinity)
    println("Normalized using L^inf norm")
    lInfNormData.show()

  }

}
