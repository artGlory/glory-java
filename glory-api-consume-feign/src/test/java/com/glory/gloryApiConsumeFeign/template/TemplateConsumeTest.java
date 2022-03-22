package com.glory.gloryApiConsumeFeign.template;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TemplateConsumeTest {

    @Autowired
    private TemplateConsume templateConsume;

    @Test
    void getMyIp() {
        System.err.println(templateConsume.getMyIp());
    }
}