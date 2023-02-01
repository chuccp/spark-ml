package com.kanke.ml

import org.apache.spark.sql.SparkSession

object App {

  def main(args: Array[String]): Unit = {
    val sparkSession = SparkSession.builder().master("local").appName("als").getOrCreate()




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
