package org.odb.it.apps.rabbitmq;

import org.odb.it.apps.rabbitmq.config.RabbitMQConfig;
import org.odb.it.apps.rabbitmq.config.RabbitMQPubConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Random;

@Configuration
@EnableScheduling
@EnableConfigurationProperties({RabbitMQConfig.class})
public class OdbItAppsRabbitmqConfig {

    private static final Logger logger = LoggerFactory.getLogger(OdbItAppsRabbitmqConfig.class);

    private final RabbitTemplate rabbitTemplate;
    private final RabbitMQConfig rabbitMQConfig;

    public OdbItAppsRabbitmqConfig(
            final RabbitTemplate rabbitTemplate,
            final RabbitMQConfig rabbitMQConfig
            ) {
        this.rabbitTemplate = rabbitTemplate;
        this.rabbitMQConfig = rabbitMQConfig;
    }

    @Bean
    Queue dnp3SubQueue() {
        return new Queue(rabbitMQConfig.getSubscribe().getQueueName(), false);
    }

    @Bean
    Queue dnp3PubQueue() {
        return new Queue(rabbitMQConfig.getPublish().getQueueName(), false);
    }

    @Bean
    TopicExchange dnp3SubExchange() {
        return new TopicExchange(rabbitMQConfig.getSubscribe().getTopicExchangeName());
    }

    @Bean
    TopicExchange dnp3PubExchange() {
        return new TopicExchange(rabbitMQConfig.getPublish().getTopicExchangeName());
    }

    @Bean
    Binding binding(Queue dnp3SubQueue, TopicExchange dnp3SubExchange) {
        return BindingBuilder.bind(dnp3SubQueue).to(dnp3SubExchange).with(rabbitMQConfig.getSubscribe().getRoutingKey());
    }

/*
    @Bean
    SimpleMessageListenerContainer container(
            ConnectionFactory connectionFactory,
            MessageListenerAdapter listenerAdapter
    ) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(rabbitMQConfig.getSubscribe().getQueueName());
        container.setMessageListener(listenerAdapter);
        return container;
    }

    @Bean
    MessageListenerAdapter listenerAdapter(OdbItAppsRabbitmqMessageReceiver receiver) {
        return new MessageListenerAdapter(receiver, "receiveMessage");
    }
*/
    
    @RabbitListener(queues = "${odbitappsrabbitmq.subscribe.queueName}")
    public void consume(Message<String> message) {
        logger.info("=================================================================================");
        logger.info("Received message: {} with headers {}", message.getPayload(), message.getHeaders());
        logger.info("=================================================================================");

    }

    @Scheduled(fixedDelay = 30000, initialDelay = 10000)
    public void scheduleMessagePublishTask() {
        RabbitMQPubConfig rabbitMQPubConfig = rabbitMQConfig.getPublish();
        String[] payloads = rabbitMQPubConfig.getPayload().split(",");
        String[] paths = rabbitMQPubConfig.getPath().split(",");

        int size = payloads.length;

        Random rand = new Random();
        int randomIndex = rand.nextInt(size);
        int randomValue = rand.nextInt(100) + 1;
        String path = paths[randomIndex];
        String payload = payloads[randomIndex].replace("10", String.valueOf(randomValue));

        String queueName = rabbitMQPubConfig.getQueueName();
        logger.info("=================================================================================");
        logger.info("Publishing message {} to {} with path {}", payload, queueName, path);
        logger.info("=================================================================================");

        rabbitTemplate.convertAndSend(rabbitMQPubConfig.getTopicExchangeName(), rabbitMQPubConfig.getRoutingKey(), payload, m -> {
            m.getMessageProperties().setHeader("path", path);
            return m;
        });
    }
}
