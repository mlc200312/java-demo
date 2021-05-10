package com.example.demo.rabbitmq.consumer;

import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @Description: 功能描述
 * @Author: Mr.Min
 * @Date: 2019-07-29
 **/
@Component
public class TopicPaymentReceive {

    private final Logger logger = LoggerFactory.getLogger("Message");

    @RabbitHandler
    @RabbitListener(queuesToDeclare = @Queue("${xmin.amqp.prefix}.topic.payment"))
    public void receive(String content, Message message, Channel channel) throws IOException {
        logger.info(String.format("topic.payment receive message: %s", content));
    }

}
