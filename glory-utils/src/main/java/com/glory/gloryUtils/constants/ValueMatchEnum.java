package com.glory.gloryUtils.constants;

import lombok.Getter;

/**
 * 值对比类型
 */
@Getter
public enum ValueMatchEnum {
    is_null("is_null", "is_null"),
    not_null("not_null", "not_null"),
    exit_not_Blank("exit_not_Blank", "存在,不能为空"),
    exit_trim_not_Blank("exit_trim_not_Blank", "存在,trim不能为空");

    String key;
    String value;

    ValueMatchEnum(String key, String value) {
        this.key = key;
        this.value = value;
    }
}
