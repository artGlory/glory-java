package com.glory.gloryStorageEs.enums;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;

/**
 * 分页查询参数
 */
@Data
public class EsSearchPage {
    /**
     * 单页最大数据量
     */
    private static final int max_page_size = 100;
    /**
     * 最大页码
     */
    private static final int max_page_num = 100;
    /**
     * >=1
     */
    @Getter(value = AccessLevel.NONE)
    private Integer pageNum;

    @Getter(value = AccessLevel.NONE)
    private Integer pageSize;

    public EsSearchPage(Integer pageNum, Integer pageSize) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }


    public Integer getEsPageFrom() {
        Integer result;
        if (this.pageNum > max_page_num) {
            result = max_page_num;
        } else {
            result = this.pageNum;
        }
        result = (result - 1) * this.pageSize;
        return result;
    }

    public Integer getEsPageSize() {
        if (this.pageSize > max_page_size) {
            return max_page_size;
        }
        return pageSize;
    }
}
