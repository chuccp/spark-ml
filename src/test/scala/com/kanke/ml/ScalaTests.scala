package com.kanke.ml

import org.apache.spark.sql.Row
import org.junit.Test

class ScalaTests {



  @Test
  def run(): Unit = {

    val a = List(AAA("1"), AAA("2"), AAA("3"), AAA("4"))

    a.foreach {
      case AAA(i:String) => println(i)
    }
  }




  case class AAA(var str: String)
}
