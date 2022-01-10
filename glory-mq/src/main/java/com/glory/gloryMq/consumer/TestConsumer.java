package com.glory.gloryMq.consumer;

import com.glory.gloryMq.domain.TestDto;
import com.glory.gloryMq.storage.TestStorage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
@Slf4j
public class TestConsumer {

    private static final ExecutorService executorService = Executors.newSingleThreadExecutor();

    @PostConstruct
    public void consumer() {
        log.info("TestConsumer-start");
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    if (TestStorage.size() > 0) {
                        executorService.execute(new Runnable() {
                            @Override
                            public void run() {
                                TestDto testDto = TestStorage.out();
                                if (testDto != null) {
                                    log.info("模拟消费{}", testDto);
                                }
                            }
                        });
                    }
                }
            }
        }).start();
        log.info("TestConsumer-end");
    }
}
