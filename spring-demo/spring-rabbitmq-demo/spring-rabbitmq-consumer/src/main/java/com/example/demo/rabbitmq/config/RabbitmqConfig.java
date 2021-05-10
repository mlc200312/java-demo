package com.example.demo.rabbitmq.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description: 针对消费者配置
 * <p>
 * 1. 设置交换机类型
 * 2. 将队列绑定到交换机
 * FanoutExchange: 将消息分发到所有的绑定队列，无routingkey的概念
 * HeadersExchange ：通过添加属性key-value匹配
 * DirectExchange:按照routingkey分发到指定队列
 * TopicExchange:多关键字匹配
 * </p>
 * @Author: Mr.Min
 * @Date: 2019-07-29
 **/
@Configuration
public class RabbitmqConfig {

    private final Logger logger = LoggerFactory.getLogger("Message");

    @Value("${spring.rabbitmq.host}")
    private String host;

    @Value("${spring.rabbitmq.port}")
    private int port;

    @Value("${spring.rabbitmq.username}")
    private String username;

    @Value("${spring.rabbitmq.password}")
    private String password;

    @Bean
    ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(host, port);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        connectionFactory.setVirtualHost("/");
        connectionFactory.setPublisherConfirms(true);

        logger.trace(String.format("消息队列[%s:%d]创建成功！", host, port));
        return connectionFactory;
    }

    @Bean
    MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    /**
     * Spring AMQP 提供了 RabbitTemplate 来简化 RabbitMQ 发送和接收消息操作
     *
     * @return
     */
    @Bean
    AmqpTemplate amqpTemplate() {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory());

        //使用 JSON 序列化与反序列化
        rabbitTemplate.setMessageConverter(messageConverter());

        return rabbitTemplate;
    }

    /**
     * 该类封装了对 RabbitMQ 的管理操作
     *
     * @return
     */
    @Bean
    RabbitAdmin rabbitAdmin() {
        RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory());
        rabbitAdmin.setAutoStartup(true);
        return rabbitAdmin;
    }

}
