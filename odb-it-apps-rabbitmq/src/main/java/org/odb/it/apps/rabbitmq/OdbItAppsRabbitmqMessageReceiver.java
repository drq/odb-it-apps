package org.odb.it.apps.rabbitmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
public class OdbItAppsRabbitmqMessageReceiver {

    private static final Logger logger = LoggerFactory.getLogger(OdbItAppsRabbitmqMessageReceiver.class);

    public void receiveMessage(Message<String> message) {
        logger.info("=================================================================================");
        logger.info("Received message: {} with headers {}", message.getPayload(), message.getHeaders());
        logger.info("=================================================================================");
    }

    public void receiveMessage(Object message) {
        logger.info("=================================================================================");
        if (message instanceof byte[]) {
            String messageAsString = new String((byte[]) message);
            logger.info("Received binary message: {} as {}", message, messageAsString);
        } else {
            logger.info("Received message: {} as {}", message, message.getClass().getName());
        }
        logger.info("=================================================================================");
    }
}
