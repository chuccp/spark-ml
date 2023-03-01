package com.kanke.ml

import org.apache.spark.mllib.random.RandomRDDs
import org.apache.spark.sql.SparkSession
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

import scala.Serializable

@SpringBootTest
class DataFrameTests {

  @Test
  def run: Unit = {
    val spark = SparkSession.builder().master("local").appName("als").getOrCreate()
    import spark.implicits._

   val df =  RandomRDDs.normalRDD(spark.sparkContext,10,10,10).toDF("name")

    df.show()
  }

}
