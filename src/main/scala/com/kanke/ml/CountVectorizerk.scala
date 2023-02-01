package com.kanke.ml

import org.apache.spark.ml.feature.StopWordsRemover
import org.apache.spark.sql.SparkSession

object CountVectorizerk {

  def main(array: Array[String]): Unit = {

    val sparkSession = SparkSession.builder().master("local").appName("als").getOrCreate()

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
