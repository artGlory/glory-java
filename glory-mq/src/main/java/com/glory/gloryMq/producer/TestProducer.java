package com.glory.gloryMq.producer;

import com.glory.gloryMq.domain.TestDto;
import com.glory.gloryMq.storage.TestStorage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestProducer {

    /**
     * 重试次数
     */
    private static final int try_again_num = 3;

    public static boolean sendInfo(TestDto entity) {
        boolean result = false;
        int i = 0;
        for (; i < try_again_num; i++) {
            result = TestStorage.in(entity);
            if (result) return true;
        }
        log.error("向队列中插入数据失败；重试次数{}，失败数据{}", i, entity);
        return false;
    }

}
