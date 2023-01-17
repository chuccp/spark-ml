package com.cokeframework.core

import com.cokeframework.util.ClassUtils

import scala.collection.mutable.Set

class PackageScanner {

  val pathMatchingResourceResolver = new PathMatchingResourceResolver()

  def scanRootClass(cls: Class[_]) {

    val package_ = cls.getPackage
    var resources = ClassUtils.convertClassNameToResourcePath(package_.getName)
    val resourceSet = Set[Resource]()
    pathMatchingResourceResolver.doFindAllClassPathResources(resources).foreach(v => {
      if (v.isFile()) {
        pathMatchingResourceResolver.doFindPathMatchingFileResources(v).foreach(s => {
          resourceSet += s
        })
      }
    })
  }
}
