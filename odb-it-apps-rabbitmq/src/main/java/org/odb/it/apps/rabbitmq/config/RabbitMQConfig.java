package org.odb.it.apps.rabbitmq.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "odbitappsrabbitmq")
@Data
public class RabbitMQConfig {
    private RabbitMQPubConfig publish;
    private RabbitMQSubConfig subscribe;
}
