package com.glory.gloryMq;

import com.glory.gloryMq.domain.TestDto;
import com.glory.gloryMq.producer.TestProducer;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.UUID;

@SpringBootTest
class GloryMqApplicationTests {

    @Test
    void contextLoads() {
        while (true) {
            TestDto testDto = TestDto.builder()
                    .testInfo(UUID.randomUUID().toString())
                    .date(new Date())
                    .build();
            System.err.println("test" + testDto);
            TestProducer.sendInfo(testDto);
            try {
                Thread.sleep(1000L * 3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
