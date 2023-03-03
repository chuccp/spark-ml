package com.kanke.ml.repository

import com.kanke.ml.lucene.StoreTemplate
import com.kanke.ml.model.User
import com.kanke.ml.util.DocumentUtil
import org.apache.lucene.search.TermInSetQuery
import org.apache.lucene.util.BytesRef
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import scala.collection.JavaConverters._

@Component
class UserStoreRepository {

  @Autowired
  var storeTemplate: StoreTemplate = _

  private def queryByIds(index: String, ids: Array[String]): Map[String, User] = {
    val vv = ids.map { v =>
      new BytesRef(v)
    }.toList.asJava
    val termInSetQuery = new TermInSetQuery("id", vv)
    val indexSearcher = storeTemplate.getIndexSearcher(index);
    val topDocs = indexSearcher.search(termInSetQuery, vv.size())
    topDocs.scoreDocs.map {
      v => DocumentUtil.convertToUser(indexSearcher.doc(v.doc))
    }.map { v => (v.id, v) }.toMap
  }

  def filterByAddTime(index: String, userList: List[User]): List[User] = {
    val ids = userList.map({
      v => v.userId
    }).toArray
    val userMap = this.queryByIds(index, ids)
    userList.filter {
      v => {
        val oo = userMap.get(v.id)
        if (oo.isEmpty) {
          true
        } else {
          v.lastTime.after(oo.get.lastTime)
        }
      }
    }
  }
}
