package org.xmin.demo.boot.amqp.config;

import com.xmin.lab.common.logging.Logger;
import com.xmin.lab.common.logging.LoggerFactory;
import com.xmin.lab.common.logging.LoggerType;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description: 功能描述
 * @Author: Mr.Min
 * @Date: 2019-07-29
 **/
@Configuration
class RabbitmqConfig {

    private final Logger logger = LoggerFactory.getLogger(LoggerType.MESSAGE);

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

    @Bean
    AmqpTemplate amqpTemplate() {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory());

        //使用 JSON 序列化与反序列化
        rabbitTemplate.setMessageConverter(messageConverter());

        // 消息发送失败返回到队列中, 需要配置 publisher-returns: true
        rabbitTemplate.setMandatory(true);

        // 消息返回, 需要配置 publisher-returns: true
        rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey) -> {
            String correlationId = message.getMessageProperties().getCorrelationId();
            logger.info(String.format("消息：{%s} 发送失败, 应答码：{%s} 原因：{%s} 交换机: {%s}  路由键: {%s}", correlationId, replyCode, replyText, exchange, routingKey));
        });

        // 消息确认, 需要配置 publisher-confirms: true
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            if (ack) {
                logger.info(String.format("消息发送到exchange成功: {%s}", correlationData));
            } else {
                logger.info(String.format("消息发送到exchange失败,原因: {%s}", cause));
            }
        });

        return rabbitTemplate;
    }

}
