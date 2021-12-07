package com.glory.gloryUtils.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseData<T> implements Serializable {
    private static final long serialVersionUID = 4177313172779343309L;
    /**
     *responseUUID
     */
    private String responseUUID;
    /**
     * 是否成功
     */
    protected Boolean success;
    /**
     * 返回信息
     */
    private String message;
    /**
     * 返回对象
     */
    private T data;

}
