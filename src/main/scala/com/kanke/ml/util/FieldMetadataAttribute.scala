package com.kanke.ml.util

import com.kanke.ml.annotation.FieldValue
import org.apache.commons.lang3.StringUtils

import java.lang.reflect.Field

class FieldMetadataAttribute {

  var fieldValue: FieldValue = _
  var field: Field = _

  def this(field: Field) {
    this()
    val cls = classOf[FieldValue]
    this.fieldValue = field.getAnnotation(cls)
    this.field = field
  }

  def fieldName(): String = {
    if (this.fieldValue != null) {
      val fieldName = this.fieldValue.fieldName()
      if (StringUtils.isNotBlank(fieldName)) {
        return fieldName
      }
    }
    field.getName
  }

  def isBoolean(): Boolean = {
    this.field.getGenericType == classOf[Boolean]
  }

  def isString(): Boolean = {
    this.field.getGenericType == classOf[String]
  }

  def isDouble(): Boolean = {
    this.field.getGenericType == classOf[Double]
  }

  def isLong(): Boolean = {
    this.field.getGenericType == classOf[Long]
  }

  def isInt(): Boolean = {
    this.field.getGenericType == classOf[Int]
  }

  def isList(): Boolean = {
    this.field.getGenericType == List.getClass
  }
}
