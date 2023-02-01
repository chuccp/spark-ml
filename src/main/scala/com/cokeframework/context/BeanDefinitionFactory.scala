package com.cokeframework.context

import org.springframework.core.`type`.classreading.MetadataReader
import org.springframework.util.ClassUtils

class BeanDefinitionFactory {
  private var beanDefinitionMap = Map[String, BeanDefinition]()

  private var metadataReaderMap = Map[String, MetadataReader]()

  def registerBean(cls: Class[_], any: Any): Unit = {
    val name = ClassUtils.getQualifiedName(cls)
    beanDefinitionMap += (name -> new BeanDefinition(cls, any))
  }


  def registerMetadata(metadataReader: MetadataReader): Unit = {
    metadataReaderMap.+=(metadataReader.getClassMetadata.getClassName -> metadataReader)
  }
}
