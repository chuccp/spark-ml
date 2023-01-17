package com.cokeframework.core

import scala.collection.mutable.Set

class PathMatchingResourceResolver {

  def doFindAllClassPathResources(path: String): Set[Resource] = {
    var classPath = path;
    if (!classPath.endsWith("/")) {
      classPath = classPath + "/"
    }
    val resourceSet = Set[Resource]()
    val resources = Thread.currentThread().getContextClassLoader().getResources(classPath)
    while (resources.hasMoreElements()) {
      val url = resources.nextElement()
      resourceSet += (new Resource(url))
    }
    resourceSet
  }

  def doFindPathMatchingFileResources(rootDirResource: Resource): Set[Resource] = {
    val resourceSet = Set[Resource]()
    return resourceSet
  }


}
