package com.cokeframework.core

import java.io.File
import java.net.URL
import scala.collection.mutable

class PathMatchingResourceResolver {

  def doFindAllClassPathResources(path: String): mutable.Set[Resource] = {
    var classPath = path;
    if (!classPath.endsWith("/")) {
      classPath = classPath + "/"
    }
    val resourceSet = mutable.Set[Resource]()
    val resources = Thread.currentThread().getContextClassLoader().getResources(classPath)
    while (resources.hasMoreElements()) {
      val url = resources.nextElement()
      resourceSet += (new Resource(url))
    }
    resourceSet
  }

  def doFindPathMatchingFileResources(rootDirResource: Resource): mutable.Set[Resource] = {
    val resourceSet = mutable.Set[Resource]()
    val file = rootDirResource.getFile.getAbsoluteFile
    doRetrieveMatchingFiles(file, resourceSet)
    resourceSet
  }

  def doRetrieveMatchingFiles(dir: File, set: mutable.Set[Resource]): Unit = {

    val files = dir.listFiles()
    if (files != null && files.length > 0) {
      files.foreach(v => {

        if (v.isDirectory) {
          set += new Resource(v.toURI.toURL)
          doRetrieveMatchingFiles(v, set)
        }
      })

    }

  }


}
