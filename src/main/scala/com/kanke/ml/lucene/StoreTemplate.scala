package com.kanke.ml.lucene

import org.apache.lucene.index.IndexReader
import org.apache.lucene.search.IndexSearcher

import java.io.File


class StoreTemplate {

  var indexFile: File = _
  var indexFactory: IndexFactory = _

  def this(indexFile: File) {
    this()
    this.indexFactory = new IndexFactory(indexFile)
    this.indexFile = indexFile
  }

  def openManualWrite(index: String): ManualWrite = {
    new ManualWrite(index, indexFactory)
  }

  def getIndexReader(index: String): IndexReader = {
    indexFactory.getIndexReader(index)
  }

  def getIndexSearcher(index: String): IndexSearcher = {
    new IndexSearcher(indexFactory.getIndexReader(index))
  }

}
