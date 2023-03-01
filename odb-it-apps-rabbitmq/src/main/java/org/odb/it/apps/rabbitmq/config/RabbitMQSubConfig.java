package org.odb.it.apps.rabbitmq.config;

import lombok.Data;

@Data
public class RabbitMQSubConfig {
     private String queueName;
     private String topicExchangeName;
     private String routingKey;
}
