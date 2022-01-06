package com.glory.gloryStorageEs.enums;

import co.elastic.clients.elasticsearch._types.mapping.*;
import lombok.Getter;


@Getter
public enum EsFieldType {
    BOOLEAN(new BooleanProperty.Builder().build(), "boolean"),
    TEXT(new TextProperty.Builder().build(), "文本；全文搜索"),
    KEYWORD(new KeywordProperty.Builder().build(), "关键词；精度搜索"),
    INTEGER(new IntegerNumberProperty.Builder().build(), "integer"),
    LONG(new LongNumberProperty.Builder().build(), "long"),
    FLOAT(new FloatNumberProperty.Builder().build(), "float"),
    DOUBLE(new DoubleNumberProperty.Builder().build(), "double"),
    BINARY(new BinaryProperty.Builder().build(), "二进制"),
    DATE(new DateProperty.Builder().build(), "日期"),
    OBJECT(new ObjectProperty.Builder().build(), "对象"),
    ;

    PropertyVariant type;
    String msg;

    EsFieldType(PropertyVariant type, String msg) {
        this.type = type;
        this.msg = msg;
    }
}
