package com.kanke.ml

import com.kanke.ml.lucene.{SearchQuery, StoreTemplate}
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class SearchTests {

  @Autowired
  var storeTemplate:StoreTemplate = _

  @Test
  def run(): Unit = {

    val searchQuery = new SearchQuery(storeTemplate.getIndexSearcher("userLog"))

//    searchQuery.searchByPage("ooooooo",0,10000)

  }

}
