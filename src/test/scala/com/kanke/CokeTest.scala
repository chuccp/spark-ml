package com.kanke

import org.junit.Test
import org.springframework.core.`type`.classreading.CachingMetadataReaderFactory
import org.springframework.core.io.support.PathMatchingResourcePatternResolver

@Test
class CokeTest {

  @Test
  def fun(): Unit = {
    val pathMatchingResourcePatternResolver = new PathMatchingResourcePatternResolver();
    val resources = pathMatchingResourcePatternResolver.getResources("classpath*:com/kanke/*/**/*.class");
    val cachingMetadataReaderFactory = new CachingMetadataReaderFactory
    resources.foreach((v) => {
      val metadataReader = cachingMetadataReaderFactory.getMetadataReader(v)
      println(metadataReader.getClassMetadata.getClassName)
    })
  }

}
