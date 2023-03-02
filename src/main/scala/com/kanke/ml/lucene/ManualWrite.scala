package com.kanke.ml.lucene

import org.apache.lucene.index.{IndexWriter, IndexableField, Term}

import java.io.File

class ManualWrite {

  private var indexWriter: IndexWriter = _

  def this(index: String, indexFactory:IndexFactory) {
    this()
    indexWriter = indexFactory.getIndexWriter(index)
  }

  def writeOrUpdate(term: Term, doc: java.lang.Iterable[_ <: IndexableField]): Unit = {
    indexWriter.updateDocument(term, doc);
  }

  def flushAndCommit(): Unit = {
    indexWriter.flush
    indexWriter.commit
  }

}
