package com.kanke.ml.lucene

import org.apache.lucene.search.ScoreDoc

import java.util

class ScorePage(var key: String) {

  private val scoreDocs = new util.ArrayList[ScoreDoc]()

  var lastIndex: Int = 0
  var lastScoreDoc: ScoreDoc = null


  def addScoreDoc(scoreDoc: ScoreDoc): Unit = {
    scoreDocs.add(scoreDoc)
  }

  def getLastScoreDocs(): ScoreDoc = {

      scoreDocs.get(scoreDocs.size() - 1)

  }
}
