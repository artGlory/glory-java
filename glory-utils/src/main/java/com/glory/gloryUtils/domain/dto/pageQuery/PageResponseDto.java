package com.glory.gloryUtils.domain.dto.pageQuery;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;

import java.io.Serializable;
import java.util.List;


@Data
public class PageResponseDto<T> implements Serializable {
    private static final long serialVersionUID = 4042956845807247626L;
    private int pageNo = 1;
    private int pageSize = 20;
    private long total;
    @Getter(AccessLevel.NONE)
    private int pages = 1;
    private List<T> pageData;
    private Object paramData;

    /**
     * @param total          总条数
     * @param pageData       分页数据
     * @param pageRequestDto 分页参数
     */
    public PageResponseDto(long total, List<T> pageData, PageRequestDto pageRequestDto) {
        this.total = total;
        this.pageData = pageData;
        this.pageNo = pageRequestDto.getPageNo();
        this.pageSize = pageRequestDto.getPageSize();
        if (this.total % this.pageSize == 0) { //整数页码
            this.pages = (int) (this.total / this.pageSize);
        } else {
            this.pages = (int) (this.total / this.pageSize) + 1;
        }
        this.paramData = pageRequestDto.getParamData();
    }
}

