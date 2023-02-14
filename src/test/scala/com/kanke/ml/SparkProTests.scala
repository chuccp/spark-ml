package com.kanke.ml

import org.apache.spark.ml.classification.LogisticRegression
import org.apache.spark.ml.feature.{Binarizer, FeatureHasher, NGram, PolynomialExpansion, StopWordsRemover}
import org.apache.spark.ml.linalg.Vectors
import org.apache.spark.ml.linalg.Vector
import org.apache.spark.ml.param.ParamMap
import org.apache.spark.sql.{Row, SparkSession}
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class SparkProTests {


  @Test
  def ZTests(): Unit = {
    val spark = SparkSession.builder().master("local").appName("als").getOrCreate()
    val data = Array(
      Vectors.dense(2.0, 1.0),
      Vectors.dense(0.0, 0.0),
      Vectors.dense(3.0, -1.0)
    )
    val df = spark.createDataFrame(data.map(Tuple1.apply)).toDF("features")

    val polyExpansion = new PolynomialExpansion()
      .setInputCol("features")
      .setOutputCol("polyFeatures")
      .setDegree(3)

    val polyDF = polyExpansion.transform(df)
    polyDF.show(false)

  }
}
