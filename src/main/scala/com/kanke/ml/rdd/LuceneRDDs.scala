package com.kanke.ml.rdd

import com.kanke.ml.lucene.StoreTemplate
import com.kanke.ml.model.UserLog
import org.apache.commons.io.FileUtils
import org.apache.lucene.search.{IndexSearcher, ScoreDoc}
import org.apache.spark.SparkContext

import java.io.File
import java.util

object LuceneRDDs {

  def create(sc: SparkContext,  numPartitions: Int,  logIndexSearcher: String):LuceneRdd[String]={
     create(sc,-1,numPartitions,logIndexSearcher)
  }


  def create(sc: SparkContext, number: Int,numPartitions: Int, logIndexSearcher: String): LuceneRdd[String] = {
    new LuceneRdd(sc, number, numPartitions, logIndexSearcher)
  }
}
