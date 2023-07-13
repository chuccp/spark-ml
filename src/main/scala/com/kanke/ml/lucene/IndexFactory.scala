package com.kanke.ml.lucene

import org.apache.lucene.index.{DirectoryReader, IndexReader, IndexWriter, IndexWriterConfig}
import org.apache.lucene.store.FSDirectory

import java.io.File
import java.nio.file.Path
import scala.collection.mutable


class IndexFactory(var path: Path) {

  def this(path: String) {
    this(new File(path).toPath)
  }

  def this(pathFile: File) {
    this(pathFile.toPath)
  }

  def getIndexReader(index: String): IndexReader = {
    if (IndexFactory.indexReaderMap.contains(index)) {
      IndexFactory.indexReaderMap.get(index).get
    } else {
      val indexPath = new File(path.toFile, index)
      if (!indexPath.exists()) {
        indexPath.mkdirs()
      }
      val list = indexPath.list()
      if(list.isEmpty){
        return null
      }
      val fSDirectory = FSDirectory.open(indexPath.toPath)
      val reader = DirectoryReader.open(fSDirectory)
      IndexFactory.indexReaderMap.put(index, reader)
      reader
    }
  }

  def getIndexWriter(index: String): IndexWriter = {
    if (IndexFactory.indexWriterMap.contains(index)) IndexFactory.indexWriterMap.get(index).get
    else {
      val filePath = new File(path.toFile, index)
      if (!filePath.exists) filePath.mkdirs
      val fSDirectory = FSDirectory.open(filePath.toPath)
      val indexWriterConfig = new IndexWriterConfig
      val indexWriter = new IndexWriter(fSDirectory, indexWriterConfig)
      IndexFactory.indexWriterMap.put(index, indexWriter)
      indexWriter
    }
  }
}
object IndexFactory{
  private val indexWriterMap = new mutable.HashMap[String, IndexWriter]

  private val indexReaderMap = new mutable.HashMap[String, IndexReader]
}