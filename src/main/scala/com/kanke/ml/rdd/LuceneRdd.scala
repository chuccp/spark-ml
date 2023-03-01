package com.kanke.ml.rdd

import org.apache.spark.{Partition, SparkContext, TaskContext}
import org.apache.spark.internal.Logging
import org.apache.spark.rdd.RDD

import scala.reflect.ClassTag
class LuceneRdd[T: ClassTag](sc: SparkContext)  extends RDD[T](sc, Nil) with Logging{
  override def compute(split: Partition, context: TaskContext): Iterator[T] = {

    null
  }

  override protected def getPartitions: Array[Partition] = {
    null
  }
}
