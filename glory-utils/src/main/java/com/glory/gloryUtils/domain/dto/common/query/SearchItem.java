package com.glory.gloryUtils.domain.dto.common.query;

import com.glory.gloryUtils.constants.ValueMatchEnum;
import lombok.Data;


/**
 * 查询对比的某一个属性的对比方法
 */
@Data
public class SearchItem {
    String key;
    Object value;
    ValueMatchEnum valueMatchEnum;
}
