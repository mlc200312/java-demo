package com.example.demo.rabbitmq.consumer;

import com.example.demo.core.entity.OrderMessage;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @Description: 重试死信队列
 * @Author: Mr.Min
 * @Date: 2019-08-11
 **/
@Component
public class RetryReceive {

    private Logger logger = LoggerFactory.getLogger("Message");

    @RabbitHandler
    @RabbitListener(queues = "retry.order.queue")
    public void onMessage(OrderMessage orderMessage, Message message, Channel channel) throws Exception {

        orderMessage.setRemark("---------消息正常执行-----------");
        processMessage(orderMessage);
    }

    private void processMessage(OrderMessage orderMessage) throws Exception {

        if (orderMessage.getId() % 3 == 0) {
            logger.error("消息执行时失败:", orderMessage);
            orderMessage.setRemark(orderMessage.getRemark() + ":重试！");
            throw new RuntimeException("消息执行失败！");
        }

        logger.info("Received <" + orderMessage + ">");
    }

}
