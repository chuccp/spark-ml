package com.kanke.ml.util

import com.google.gson.JsonObject
import org.apache.commons.lang3.reflect.FieldUtils

import java.lang.reflect.Field

class BeanMetadata[T](cls: Class[T]) {


  var fieldMetadataAttributes: List[FieldMetadataAttribute] = List()

  def getValue(json: JsonObject): T = {
    val t = cls.newInstance()
    fieldMetadataAttributes.foreach((u) => {
      setValue(t, json, u)
    })
    t
  }

  private def setValue(t: T, json: JsonObject, fieldMetadataAttribute: FieldMetadataAttribute): Unit = {
    val name = fieldMetadataAttribute.fieldName()
    val jsonEle = json.get(name)
    if (!jsonEle.isJsonNull) {
      if (fieldMetadataAttribute.isString()) {
        FieldUtils.writeField(fieldMetadataAttribute.field, t, jsonEle.getAsString, true)
      } else if (fieldMetadataAttribute.isBoolean()) {
        FieldUtils.writeField(fieldMetadataAttribute.field, t, jsonEle.getAsBoolean, true)
      } else if (fieldMetadataAttribute.isInt()) {
        FieldUtils.writeField(fieldMetadataAttribute.field, t, jsonEle.getAsInt, true)
      } else if (fieldMetadataAttribute.isLong()) {
        FieldUtils.writeField(fieldMetadataAttribute.field, t, jsonEle.getAsLong, true)
      }
    }
  }

  def addField(field: Field): Unit = {
    val fieldMetadataAttribute = new FieldMetadataAttribute(field)
    this.fieldMetadataAttributes = this.fieldMetadataAttributes.+:(fieldMetadataAttribute)
  }

}

object BeanMetadata {

  def create[T](cls: Class[T]): BeanMetadata[T] = {
    val beanMetadata = new BeanMetadata[T](cls)
    val fields = FieldUtils.getAllFields(cls)
    fields.foreach(f => {
      beanMetadata.addField(f)
    })
    beanMetadata
  }

}