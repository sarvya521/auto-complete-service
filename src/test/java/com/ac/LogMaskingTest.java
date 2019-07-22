package com.ac;

import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.log4j.Log4j2;

@Log4j2
@SpringBootTest
class LogMaskingTest {

    @Test
    void testPlainStringLog() {
        log.info("Passed to server::0084USER:17603,IP:0:0:0:0:0:0:0:1,3425,Credit Card 1:1000002367844224,3425,Credit Card2:1000002367844224 , CVV:234,SSN:123456789");
    }

    @Test
    void testOnlyObjectLog() {
        //@formatter:off
        Map<String, String> demoMaskMap = Map.of(
                "prop1", "1000002367844224,3425", 
                "prop2", "0084USER", 
                "prop3", "123456789", 
                "prop4", "Passed to server::0084USER:17603,IP:0:0:0:0:0:0:0:1,3425,Credit Card 1:1000002367844224,3425,Credit Card2:1000002367844224 , CVV:234,SSN:123456789");
        //@formatter:on
        log.info(demoMaskMap);
    }

    @Test
    void testParameterizedLog() {
      //@formatter:off
        Map<String, String> demoMaskMap = Map.of(
                "prop1", "1000002367844224,3425", 
                "prop2", "0084USER", 
                "prop3", "123456789", 
                "prop4", "Passed to server::0084USER:17603,IP:0:0:0:0:0:0:0:1,3425,Credit Card 1:1000002367844224,3425,Credit Card2:1000002367844224 , CVV:234,SSN:123456789");
        //@formatter:on
        log.info("demo string with placeholder this {} is my object", demoMaskMap);
    }

}
