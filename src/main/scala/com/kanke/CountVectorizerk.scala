package com.kanke

import org.apache.spark.ml.feature.{CountVectorizer, CountVectorizerModel, StopWordsRemover}
import org.apache.spark.sql.SparkSession

object CountVectorizerk {

  def main(array: Array[String]): Unit ={

    val sparkSession = SparkSession.builder().master("local").appName("als").getOrCreate()
    import sparkSession.implicits._

    val remover = new StopWordsRemover()
      .setInputCol("raw")
      .setOutputCol("filtered")

    val dataSet = sparkSession.createDataFrame(Seq(
      (0, Seq("I", "saw", "the", "red", "balloon")),
      (1, Seq("Mary", "had", "a", "little", "lamb"))
    )).toDF("id", "raw")

    remover.transform(dataSet).show(false)



  }

}
