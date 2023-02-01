package com.cokeframework.context

import org.springframework.core.`type`.classreading.{CachingMetadataReaderFactory, MetadataReader}
import org.springframework.core.io.support.{PathMatchingResourcePatternResolver, ResourcePatternResolver}
import org.springframework.util.ClassUtils

import java.util.Properties

class ApplicationContext {

  private val propertySourcesConfigure = new PropertySourcesConfigure()
  private val beanDefinitionFactory = new BeanDefinitionFactory()
  private val cachingMetadataReaderFactory = new CachingMetadataReaderFactory


  def initialize(cls: Class[_], arrays: Array[String], instance: Any): Unit = {
    this.scanPackage(cls)
    if (instance != null && !instance.getClass.equals(cls)) {
      this.scanPackage(instance.getClass)
    }
    if (instance != null) {
      this.registerBean(instance.getClass, instance)
    }
    this.initializeProperty()
  }

  def registerBean(cls: Class[_], any: Any): Unit = {
    beanDefinitionFactory.registerBean(cls, any)
  }

  def initializeProperty(): Unit = {
    val result = new Properties()
    propertySourcesConfigure.readProperties(result)
  }

  def scanPackage(cls: Class[_]): Unit = {
    val resourcePath = ClassUtils.convertClassNameToResourcePath(ClassUtils.getPackageName(cls))
    val pathMatchingResourcePatternResolver = new PathMatchingResourcePatternResolver();
    val patternPath = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX + resourcePath + "/*/**/*" + ClassUtils.CLASS_FILE_SUFFIX
    val resources = pathMatchingResourcePatternResolver.getResources(patternPath);
    resources.foreach((v) => {
      val metadataReader = cachingMetadataReaderFactory.getMetadataReader(v)
      if (metadataReader.getAnnotationMetadata.hasAnnotation("scala.reflect.ScalaSignature")) {
        if (metadataReader.getAnnotationMetadata.getAnnotationTypes.size() > 1) {
          this.registerMetadata(metadataReader)
        }
      } else {
        if (metadataReader.getAnnotationMetadata.getAnnotationTypes.size() > 0) {
          this.registerMetadata(metadataReader)
        }
      }
    })
  }

  def registerMetadata(metadataReader: MetadataReader): Unit = {
    beanDefinitionFactory.registerMetadata(metadataReader)
  }
}
