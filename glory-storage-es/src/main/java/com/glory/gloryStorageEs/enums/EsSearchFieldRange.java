package com.glory.gloryStorageEs.enums;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 范围搜索
 *
 * @param <T>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EsSearchFieldRange<T> {
    /**
     * 列名称
     */
    private String field;
    /**
     * 最大值
     */
    private T maxValue;
    /**
     * 最小值
     */
    private T minValue;
    /**
     * 列映射
     */
    private EsFieldType esFieldType;

}
