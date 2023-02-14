package com.kanke.ml

import org.apache.spark.ml.feature.{RegexTokenizer, Word2Vec}
import org.apache.spark.sql.SparkSession
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class Word2VecTests {

  @Test
  def ZTests(): Unit = {
    val spark = SparkSession.builder().master("local").appName("als").getOrCreate()
    val data = "1 2 6 0 2 3 1 1 0 0 3\n1 3 0 1 3 0 0 2 0 0 1\n1 4 1 0 0 4 9 0 1 2 0\n2 1 0 3 0 0 5 0 2 3 9\n3 1 1 9 3 0 2 0 0 1 3\n4 2 0 3 4 5 1 1 1 4 0\n2 1 0 3 0 0 5 0 2 2 9\n1 1 1 9 2 1 2 0 0 1 3\n4 4 0 3 4 2 1 3 0 0 0\n2 8 2 0 3 0 2 0 2 7 2\n1 1 1 9 0 2 2 0 0 3 3\n4 1 0 0 4 5 1 3 0 1 0";
    val data2 = Array(("1", "2", "3", "4", "5"), ("6", "7", "8", "9", "10"))
    val array = data.split("\n").map(Tuple1.apply)
    val dataReader = spark.createDataFrame(array).toDF("words")

    val regexTokenizer = new RegexTokenizer().setInputCol("words").setOutputCol("value").setPattern(" ");
    val regexTokenizerDf = regexTokenizer.transform(dataReader).drop("words")

    val word2vec = new Word2Vec().setVectorSize(20)


    val model = word2vec.setInputCol("value").fit(regexTokenizerDf)


    model.getVectors.show(false)

    //    val synonyms = model.findSynonyms("1", 5)
    //
    //    for (team <- synonyms) {
    //
    //      println(team)
    //    }
    //
    //    model.

    //    word2vec.

    //    model.save("C:\\Users\\cao\\Documents\\svn\\ooo.model")

  }
}
