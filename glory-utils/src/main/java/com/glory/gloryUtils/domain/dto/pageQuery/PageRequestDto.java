package com.glory.gloryUtils.domain.dto.pageQuery;

import lombok.*;

import java.io.Serializable;
import java.util.LinkedHashMap;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PageRequestDto<T> implements Serializable {

    private static final long serialVersionUID = -8941463788357066176L;
    private int pageNo = 1;
    private int pageSize = 20;
    private T paramData;
    //===============mysql====start==page============================
    @Getter(AccessLevel.NONE)
    private int mysqlPageStartId;
    @Getter(AccessLevel.NONE)
    private int mysqlPageEndId;

    public int getMysqlPageStartId() {
        return (this.getPageNo() - 1) * this.getPageSize();
    }

    public int getMysqlPageEndId() {
        return this.getPageSize();
    }
//===============mysql====end==page============================

//===============sort field========start====================
//    使用方法
//        <foreach collection="searchPage.sortMap" separator="," open="order by" index="sortField" item="sortType">
//           ${sortField} ${sortType}
//        </foreach>
    /**
     * 排序字段
     */
    private LinkedHashMap<String, String> sortMap = new LinkedHashMap<>();
//===============sort field========end====================
}

