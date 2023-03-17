package org.odb.it.apps.rabbitmq.config;

import lombok.Data;

@Data
public class RabbitMQPubConfig {
     private String queueName;
     private String topicExchangeName;
     private String payload;
     private String routingKey;
     private String path;
}
