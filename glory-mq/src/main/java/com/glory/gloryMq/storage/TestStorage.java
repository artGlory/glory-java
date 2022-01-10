package com.glory.gloryMq.storage;

import com.glory.gloryMq.domain.TestDto;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * 线程池存储
 */
public class TestStorage {
    static private LinkedBlockingQueue<TestDto> queue = new LinkedBlockingQueue<>(1000);

    /**
     * 进
     *
     * @param e
     * @return
     */
    static public boolean in(TestDto e) {
        return queue.offer(e);
    }

    static public int size() {
        return queue.size();
    }

    /**
     * 出
     *
     * @return
     */
    static public TestDto out() {
        return queue.poll();
    }
}
