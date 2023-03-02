package com.kanke.ml.lucene

import org.apache.lucene.search.{IndexSearcher, ScoreDoc}

import java.io.File

class SearchQuery(indexDir: File) {
  private def getIndexSearcher(index: String): IndexSearcher = {
    new IndexSearcher(new StoreTemplate(indexDir).getIndexReader(index))
  }


  def search(index: String, num: Int, from: Int, to: Int): Array[ScoreDoc] = {


    null
  }


}

object SearchQuery
