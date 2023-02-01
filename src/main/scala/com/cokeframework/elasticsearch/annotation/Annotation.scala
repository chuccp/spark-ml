package com.cokeframework.elasticsearch.annotation

import com.cokeframework.elasticsearch.annotation.FieldType.FieldType

class Document(indexName: String, typeName: String = "") extends annotation.Annotation


class Id extends annotation.Annotation

class Field(fieldType: FieldType, fieldName: String = "") extends annotation.Annotation


class Bean extends annotation.Annotation