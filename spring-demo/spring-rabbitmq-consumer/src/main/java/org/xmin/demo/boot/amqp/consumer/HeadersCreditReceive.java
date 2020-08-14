package org.xmin.demo.boot.amqp.consumer;

import com.rabbitmq.client.Channel;
import com.xmin.lab.common.logging.Logger;
import com.xmin.lab.common.logging.LoggerFactory;
import com.xmin.lab.common.logging.LoggerType;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * @Description: 交换机——HeadersExchange
 * @Author: Mr.Min
 * @Date: 2019-07-29
 **/
@Component
public class HeadersCreditReceive {

    private final Logger logger = LoggerFactory.getLogger(LoggerType.MESSAGE);

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