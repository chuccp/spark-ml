package com.kanke.ml

import org.apache.spark.ml.feature.Word2Vec
import org.apache.spark.sql.SparkSession

object Word2Vec {

  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder().master("local").appName("als").getOrCreate()

    //    spark.createDataFrame(Seq( "Hi I heard about Spark".split(" ")).map(Tuple1.apply)).toDF("text");


    //    println(Seq( "Hi I heard about Spark".split(" ")).map(Tuple1.apply))

    val seq = Seq(
      "Hi I heard about Spark".split(" "),
      "I wish Java could use case classes".split(" "),
      "Logistic regression models are neat".split(" ")
    ).map(Tuple1.apply);

    val documentDF = spark.createDataFrame(seq).toDF("text")

    documentDF.show(false)


    val word2Vec = new Word2Vec()
      .setInputCol("text")
      .setOutputCol("result")
      .setVectorSize(50)
      .setMinCount(0)
    val model = word2Vec.fit(documentDF)

    val result = model.transform(documentDF)


    result.show(false)


  }

}
