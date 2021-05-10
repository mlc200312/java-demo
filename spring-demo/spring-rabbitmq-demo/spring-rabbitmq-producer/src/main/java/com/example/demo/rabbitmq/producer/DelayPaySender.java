package com.example.demo.rabbitmq.producer;

import com.example.demo.core.entity.OrderMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Description: 延迟队列-生产者
 * @Author: Mr.Min
 * @Date: 2019-08-02
 **/
@Component
public class DelayPaySender {

    private static final Logger logger = LoggerFactory.getLogger("Message");

    @Autowired
    private RabbitTemplate amqpTemplate;

    public void send(OrderMessage orderMessage) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        CorrelationData correlationData = new CorrelationData(String.valueOf(orderMessage.getId()));
        amqpTemplate.convertAndSend("direct.pay.exchange", "OrderPay", orderMessage, correlationData);
        logger.info(orderMessage.getId() + "：" + sdf.format(new Date()));
    }

}
