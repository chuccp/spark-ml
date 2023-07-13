package com.kanke.ml.lucene

import org.apache.lucene.search.{IndexSearcher, Query, ScoreDoc}

class SearchQuery(indexSearcher: IndexSearcher) {


  def searchByPage(key: String, query: Query, from: Int, to: Int): Array[ScoreDoc] = {
    val scorePage = SearchQuery.get(key)
    if (scorePage.lastScoreDoc == null) {
      val scoreTopDocs = indexSearcher.search(query, to)
      scoreTopDocs.scoreDocs.foreach(scorePage.addScoreDoc)
      scorePage.lastIndex = to
    } else {
      if (scorePage.lastIndex >= to) {

      } else {
        val scoreDoc = scorePage.getLastScoreDocs()
        val scoreTopDocs = indexSearcher.searchAfter(scoreDoc, query, to - scorePage.lastIndex)
        scoreTopDocs.scoreDocs.foreach(scorePage.addScoreDoc)
        scorePage.lastIndex = to
      }
    }

    null
  }


}

object SearchQuery {

  def get(key: String): ScorePage = {
    if (pageMap.containsKey(key)) {
      pageMap.get(key)
    } else {
      val sp = new ScorePage(key)
      pageMap.put(key, sp)
      sp
    }
  }

  private val pageMap = new java.util.HashMap[String, ScorePage]()
}

