package com.kanke.ml.rdd

import com.kanke.ml.lucene.StoreTemplate
import com.kanke.ml.entity.UserLog
import org.apache.commons.io.FileUtils
import org.apache.lucene.search.{IndexSearcher, ScoreDoc}
import org.apache.spark.SparkContext

import java.io.File
import java.util

object LuceneRDDs {

  def create(sc: SparkContext,  numPartitions: Int,  logIndexSearcher: String,indexPath:String):LuceneRdd[String]={
     create(sc,-1,numPartitions,logIndexSearcher,indexPath)
  }


  def create(sc: SparkContext, number: Int,numPartitions: Int, logIndexSearcher: String,indexPath:String): LuceneRdd[String] = {
    new LuceneRdd(sc, number, numPartitions, logIndexSearcher,indexPath)
  }
}
