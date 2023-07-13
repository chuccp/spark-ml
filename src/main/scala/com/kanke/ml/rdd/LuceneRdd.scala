package com.kanke.ml.rdd

import com.kanke.ml.lucene.StoreTemplate
import com.kanke.ml.util.DocumentUtil
import org.apache.lucene.search.{IndexSearcher, MatchAllDocsQuery, ScoreDoc}
import org.apache.spark.internal.Logging
import org.apache.spark.rdd.RDD
import org.apache.spark.{Partition, SparkContext, TaskContext}

import java.io.File
import java.util
import scala.reflect.ClassTag

class LuceneRdd[T: ClassTag](sc: SparkContext, number: Int, numPartitions: Int, index: String, indexPath: String) extends RDD[T](sc, Nil) with Logging {


  private def getIndexSearcher(): IndexSearcher = {
    new IndexSearcher(new StoreTemplate(new File(indexPath)).getIndexReader(index))
  }


  override def compute(split: Partition, context: TaskContext): Iterator[T] = {
    val lucenePartition = split.asInstanceOf[LucenePartition]
    val IndexSearcher = getIndexSearcher()
    val hitScores = new util.ArrayList[ScoreDoc]()
    var indexNum = 0
    IndexSearcher.search(new MatchAllDocsQuery(), lucenePartition.to).scoreDocs.foreach({ v => {
      if (indexNum >= lucenePartition.from) {
        hitScores.add(v)
      } else {
        indexNum = indexNum + 1
      }
    }
    })
    this.logInfo(s"读取数量: ${hitScores.size()} index:${lucenePartition.index} from:${lucenePartition.from} to:${lucenePartition.to}")
    var list = List[String]()
    hitScores.forEach { v => {
      val vv = DocumentUtil.convertToString(IndexSearcher.doc(v.doc))
      list = list :+ vv
    }
    }
    list.toIterator.asInstanceOf[Iterator[T]]
  }

  override protected def getPartitions: Array[Partition] = {

    val indexSearcher = this.getIndexSearcher()
    var readNum = number
    if (number <= 0) {
      readNum = indexSearcher.getIndexReader.numDocs()
    }
    val blockSize = math.ceil(readNum.toDouble / numPartitions).toInt
    val values = (0 until numPartitions).map { i =>
      new LucenePartition(i, i * blockSize, (i + 1) * blockSize)
    }.toArray
    values.asInstanceOf[Array[Partition]]
  }

  private class LucenePartition(indexNum: Int, var from: Int, var to: Int) extends Partition {
    override def index: Int = {
      this.indexNum
    }
  }
}


