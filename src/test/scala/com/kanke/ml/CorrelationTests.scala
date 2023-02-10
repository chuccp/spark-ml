package com.kanke.ml

import org.apache.spark.ml.linalg.{Matrix, Vectors}
import org.apache.spark.ml.stat.Correlation
import org.apache.spark.sql.{Row, SparkSession}
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

class CorrelationTests {

  @Test
  def run: Unit = {
    val spark = SparkSession.builder().master("local").appName("als").getOrCreate()
    import spark.implicits._



  }

}
