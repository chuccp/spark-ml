package com.cokeframework.util

object ClassUtils {

  val PACKAGE_SEPARATOR: Char = '.'
  val PATH_SEPARATOR: Char = '/'

  def convertClassNameToResourcePath(className:String): String = {
    className.replace(PACKAGE_SEPARATOR,PATH_SEPARATOR)
  }

}
