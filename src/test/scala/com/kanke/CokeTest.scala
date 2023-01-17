package com.kanke

import com.cokeframework.boot.CokeApplication
import com.cokeframework.core.PackageScanner
import org.junit.Test

import scala.io.Source

@Test
class CokeTest {

  @Test
  def fun(): Unit = {
    new PackageScanner().scanRootClass(getClass)
  }
}
