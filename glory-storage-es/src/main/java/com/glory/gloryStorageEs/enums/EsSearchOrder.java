package com.glory.gloryStorageEs.enums;

import lombok.Data;

/**
 * 排序
 */
@Data
public class EsSearchOrder {
    /**
     * 倒序
     */
    public static final String order_by_type__desc = "desc";
    /**
     * 正序
     */
    public static final String order_by_type__asc = "asc";

    private String field;

    private String orderByType = order_by_type__desc;
}
