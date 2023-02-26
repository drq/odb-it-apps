package org.odb.it.apps.rabbitmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class OdbItAppsRabbitmqConfig {

    private static final Logger logger = LoggerFactory.getLogger(OdbItAppsRabbitmqConfig.class);

    @Value("${odbitappsrabbitmq.queueName}")
    private String queueName;

    @Value("${odbitappsrabbitmq.topicExchangeName}")
    private String topicExchangeName;

    @Value("${odbitappsrabbitmq.payload}")
    private String payload;

    private final RabbitTemplate rabbitTemplate;

    public OdbItAppsRabbitmqConfig(final RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Bean
    Queue dnp3ControlQueue() {
        return new Queue(queueName, false);
    }

    @Bean
    TopicExchange dnp3Exchange() {
        return new TopicExchange(topicExchangeName);
    }

    @Bean
    Binding binding(Queue dnp3ControlQueue, TopicExchange dnp3Exchange) {
        return BindingBuilder.bind(dnp3ControlQueue).to(dnp3Exchange).with("dnp3.#");
    }

    @Bean
    SimpleMessageListenerContainer container(
            ConnectionFactory connectionFactory,
            MessageListenerAdapter listenerAdapter
    ) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(queueName);
        container.setMessageListener(listenerAdapter);
        return container;
    }

    @Bean
    MessageListenerAdapter listenerAdapter(OdbItAppsRabbitmqMessageReceiver receiver) {
        return new MessageListenerAdapter(receiver, "receiveMessage");
    }

    @Scheduled(fixedDelay = 30000, initialDelay = 10000)
    public void scheduleMessagePublishTask() {
        logger.info("=================================================================================");
        logger.info("Publishing message {} to {}", payload, queueName);
        logger.info("=================================================================================");

        Message<String> testMessage = MessageBuilder.withPayload(payload).setHeader("topic", queueName).build();

        rabbitTemplate.convertAndSend(topicExchangeName, "dnp3.control", testMessage);
    }
}
