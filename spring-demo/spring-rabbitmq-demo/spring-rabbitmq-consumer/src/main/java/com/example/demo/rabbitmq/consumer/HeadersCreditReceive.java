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
 * @Description: 交换机——HeadersExchange
 * @Author: Mr.Min
 * @Date: 2019-07-29
 **/
@Component
public class HeadersCreditReceive {

    private final Logger logger = LoggerFactory.getLogger("Messge");

    @RabbitHandler
    @RabbitListener(queuesToDeclare = @Queue("headers.credit.bank"))
    public void creditBank(Message message, Channel channel) throws IOException {

        logger.info("headers.credit.bank:" + new String(message.getBody()));
    }

    @RabbitHandler
    @RabbitListener(queuesToDeclare = @Queue("headers.credit.finance"))
    public void creditFinance(Message message, Channel channel) throws IOException {

        logger.info("headers.credit.finance:" + new String(message.getBody()));
    }

}