package com.example.demo.rabbitmq.consumer;

import com.example.demo.core.entity.OrderMessage;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;

/**
 * @Description: 交换机——DirectExchange
 * @Author: Mr.Min
 * @Date: 2019-07-25
 **/
@Component
public class DirectRpcReceive {

    private final Logger logger = LoggerFactory.getLogger("Message");

    @RabbitHandler
    @RabbitListener(queuesToDeclare = @Queue("direct.a"))
    public void onMessageA(String content, Message message, Channel channel) throws IOException {
        logger.info(String.format("direct.a receive message: %s", content));
    }

    @RabbitHandler
    @RabbitListener(queuesToDeclare = @Queue("direct.b"))
    public void onMessageB(String content, Message message, Channel channel) throws IOException {
        logger.info(String.format("direct.b receive message: %s", content));
    }

    /**
     * 虽然RabbitMQ支持RPC接口调用，但不推荐使用。
     * <p>
     * 原因：
     * 1）RPC默认为单线程阻塞模型，效率极低。
     * 2）需要手动实现多线程消费。
     *
     * @param message
     * @param channel
     * @return
     * @throws IOException
     */
    @RabbitHandler
    @RabbitListener(queuesToDeclare = @Queue("direct.rpc"))
    public OrderMessage onMessage(Long orderId, Message message, Channel channel) throws IOException {

        logger.info(String.format("direct.rpc receive orderId: %d", orderId));

        OrderMessage orderMessage = new OrderMessage();
        orderMessage.setId(orderId);
        orderMessage.setBuyerId(1L);
        orderMessage.setOrderDate(new Date());

        return orderMessage;
    }

}
