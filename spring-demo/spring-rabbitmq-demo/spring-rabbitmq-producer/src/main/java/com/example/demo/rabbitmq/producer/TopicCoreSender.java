package com.example.demo.rabbitmq.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Description: 功能描述
 * @Author: Mr.Min
 * @Date: 2019-07-25
 **/
@Component
public class TopicCoreSender {

    private static final Logger logger = LoggerFactory.getLogger("Message");

    @Autowired
    private AmqpTemplate amqpTemplate;

    public void user(String msg) {
        logger.info("topic.core.user send message: " + msg);
        amqpTemplate.convertAndSend("topic.core.exchange", "topic.core.user", msg);
    }

    public void userQuery(String msg) {
        logger.info("topic.core.user.query send message: " + msg);
        amqpTemplate.convertAndSend("topic.core.exchange", "topic.core.user.query", msg);
    }

}
