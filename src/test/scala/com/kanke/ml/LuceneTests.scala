package com.kanke.ml

import com.kanke.ml.lucene.{IndexFactory, StoreTemplate}
import org.apache.commons.io.FileUtils
import org.apache.lucene.document.{DateTools, Document, Field, StringField}
import org.apache.lucene.index.Term
import org.apache.lucene.search.{IndexSearcher, MatchAllDocsQuery}
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

import java.io.File

@SpringBootTest
class LuceneTests {

  @Autowired
  var storeTemplate: StoreTemplate = _

  @Test
  def writeTests(): Unit = {


    val manualWrite = storeTemplate.openManualWrite("videoData")

    val document = new Document()

    val stringField = new StringField("name", "66666", Field.Store.YES)
    val idStringField = new StringField("id", "1212", Field.Store.YES)
    document.add(stringField)
    document.add(idStringField)


    manualWrite.writeOrUpdate(new Term("id", "1212"), document)


    val document2 = new Document()
    val stringField2 = new StringField("name", "66666", Field.Store.YES)
    val idStringField2 = new StringField("id", "1213", Field.Store.YES)
    document2.add(stringField2)
    document2.add(idStringField2)

    manualWrite.writeOrUpdate(new Term("id", "1213"), document2)

    manualWrite.flushAndCommit()
  }

  @Test
  def readTests(): Unit = {
    val indexPath = new File(FileUtils.getTempDirectory, "index")
    val indexFactory = new IndexFactory(indexPath)
    val indexReader = indexFactory.getIndexReader("videoData")
    val indexSearcher = new IndexSearcher(indexReader)
    val query = new MatchAllDocsQuery()
    val topdocs = indexSearcher.search(query, 100)
    val docs = topdocs.scoreDocs



    for (doc <- docs) {

      val docment = indexSearcher.doc(doc.doc)
      println(docment.get("name"))

    }


  }
}
