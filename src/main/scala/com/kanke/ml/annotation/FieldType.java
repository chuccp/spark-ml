package com.kanke.ml.annotation;

public enum FieldType {

    Text("Text"),
    Long("Long"),
    Auto("Auto"),
    Keyword("Keyword"),
    Double("Double"),
    Date("Date"),
    Boolean("Boolean"),
    Integer("Integer");
    private final String fieldType;
    private FieldType(String fieldType) {
        this.fieldType = fieldType;
    }
}
