package com.kanke.ml.util

import com.google.gson.JsonObject
import com.kanke.ml.annotation.Id
import org.apache.commons.lang3.reflect.FieldUtils

import java.lang.reflect.Field

class BeanMetadata[T](cls: Class[T]) {


  var fieldMetadataAttributes: List[FieldMetadataAttribute] = List()
  var idField: Field = _


  def getValue(json: JsonObject, id: String): T = {
    val t = cls.newInstance()
    fieldMetadataAttributes.foreach((u) => {
      setValue(t, json, u)
    })
    FieldUtils.writeField(idField, t, id, true)
    t
  }

  private def setValue(t: T, json: JsonObject, fieldMetadataAttribute: FieldMetadataAttribute): Unit = {
    val name = fieldMetadataAttribute.fieldName()
    val jsonEle = json.get(name)
    if (jsonEle != null && !jsonEle.isJsonNull) {
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

  private def addField(field: Field): Unit = {
    if (field.getAnnotation(classOf[Id]) != null) {
      this.idField = field;
    } else {
      val fieldMetadataAttribute = new FieldMetadataAttribute(field)
      this.fieldMetadataAttributes = this.fieldMetadataAttributes.+:(fieldMetadataAttribute)
    }
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