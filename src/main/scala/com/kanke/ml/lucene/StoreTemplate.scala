package com.kanke.ml.lucene

import java.io.File


class StoreTemplate(var indexFile: File) {


  def openManualWrite(index: String): ManualWrite = {
    new ManualWrite(index, indexFile)
  }

}
