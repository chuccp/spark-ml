package com.cokeframework.context

import org.springframework.core.io.Resource
import org.springframework.core.io.support.{PathMatchingResourcePatternResolver, ResourcePatternResolver}
import org.springframework.util.ClassUtils

class PackageScanClassResolver {


  def scan(loader: ClassLoader, packageName: String): Array[Resource] = {
    val pathMatchingResourcePatternResolver = new PathMatchingResourcePatternResolver(loader);
    val pattern = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX + ClassUtils.convertClassNameToResourcePath(packageName) + "/**/*.class";
    pathMatchingResourcePatternResolver.getResources(pattern)
  }


}
