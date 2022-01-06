package com.glory.gloryStorageEs.enums;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EsSearchField {
    /**
     * 列名称
     */
    private String field;
    /**
     * 列参数
     */
    private Object value;
    /**
     * 列映射
     */
    private EsFieldType esFieldType;
    /**
     * 查询方式
     * 1=term
     * 2=match
     * 3=match_phrase ；如果为text，这个可以代替mysql的 '%value%'
     */
    private int searchType = 1;
}
