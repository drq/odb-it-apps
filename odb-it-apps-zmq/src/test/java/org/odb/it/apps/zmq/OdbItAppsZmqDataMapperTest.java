package org.odb.it.apps.zmq;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestApp.class)
public class OdbItAppsZmqDataMapperTest {
    private static final Logger logger = LoggerFactory.getLogger(OdbItAppsZmqDataMapperTest.class);

    @Autowired
    private OdbItAppsZmqDataMapper odbItAppsZmqDataMapper;

    @Test
    void toJson() {
        Map<String, String> mapObject = new HashMap<>();
        mapObject.put("key", "value");
        try {
            String json = odbItAppsZmqDataMapper.toJson(mapObject, Map.class);
            logger.info("Json: {}", json);
        } catch (Exception ex) {
            logger.error("fail to deserialize", ex);
            fail("Should not fail");
        }
    }

    @Test
    void fromJson() {
        String json = "{\"key\": \"value\"}";
        try {
            Map<String, String> payloadObject = odbItAppsZmqDataMapper.fromJson(json, Map.class);
            assertEquals("value", payloadObject.get("key"), "Must return expected value");
            logger.info("Value: {}", payloadObject.get("key"));
        } catch (Exception ex) {
            logger.error("fail to deserialize", ex);
            fail("Should not fail");
        }
    }

    @Test
    void toMapJson() {
        Map<String, Object> mapObject = new HashMap<>();
        mapObject.put("key", "value");
        try {
            String json = odbItAppsZmqDataMapper.toMapJson(mapObject);
            logger.info("Json: {}", json);
        } catch (Exception ex) {
            logger.error("fail to deserialize", ex);
            fail("Should not fail");
        }
    }

    @Test
    void fromMapJson() {
        String json = "{\"key\": \"value\"}";
        try {
            Map<String, Object> payloadObject = odbItAppsZmqDataMapper.fromMapJson(json);
            assertEquals("value", payloadObject.get("key"), "Must return expected value");
            logger.info("Value: {}", payloadObject.get("key"));
        } catch (Exception ex) {
            logger.error("fail to deserialize", ex);
            fail("Should not fail");
        }
    }
}
