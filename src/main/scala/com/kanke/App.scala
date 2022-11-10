package com.kanke

import org.apache.spark.SparkConf
import org.apache.spark.ml.feature.{HashingTF, IDF, Tokenizer}
import org.apache.spark.ml.linalg.{Matrix, Vector, Vectors}
import org.apache.spark.ml.stat.{ChiSquareTest, Correlation}

import scala.reflect.api.TypeCreator
import org.apache.spark.sql.{Row, SparkSession}

object App {

  def main(args: Array[String]): Unit = {
    val sparkSession = SparkSession.builder().master("local").appName("als").getOrCreate()
    import sparkSession.implicits._




//    val sentenceData = sparkSession.createDataFrame(Seq(
//      (0.0, "Hi I heard about Spark"),
//      (0.0, "I wish Java could use case classes"),
//      (1.0, "Logistic regression models are neat")
//    )).toDF("label", "sentence")
//
//   val tokenizer = new Tokenizer().setInputCol("sentence").setOutputCol("words")
//    val wordsData = tokenizer.transform(sentenceData)
//
//    wordsData.show()
//
//    val hashingTF = new HashingTF().setInputCol("words").setOutputCol("rawFeatures").setNumFeatures(200)
//
//    val featurizedData = hashingTF.transform(wordsData)
//
//    featurizedData.show()
//
//
//    val idf = new IDF().setInputCol("rawFeatures").setOutputCol("features")
//    val idfModel = idf.fit(featurizedData)
//
//    val rescaledData = idfModel.transform(featurizedData)
//    rescaledData.show(false)

  }
}
