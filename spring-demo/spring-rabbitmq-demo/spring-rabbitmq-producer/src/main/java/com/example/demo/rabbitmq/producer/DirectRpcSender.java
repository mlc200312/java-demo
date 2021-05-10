package com.example.demo.rabbitmq.producer;


import com.example.demo.core.entity.OrderMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Description: 功能描述
 * @Author: Mr.Min
 * @Date: 2019-07-25
 **/
@Component
public class DirectRpcSender {

    private static final Logger logger = LoggerFactory.getLogger("Message");

    @Autowired
    private RabbitTemplate amqpTemplate;

    /**
     * 60秒过期时间
     *
     * @param msg
     */
    public void sendA(String msg) {
        CorrelationData correlationData = new CorrelationData(msg);
        logger.info("direct.b send message: " + msg);
        amqpTemplate.convertAndSend("direct.a", msg,
                message -> {
                    // 设置60秒过期
                    message.getMessageProperties().setExpiration("60000");
                    return message;
                },
                correlationData);
    }

    /**
     * 60秒过期时间
     *
     * @param msg
     */
    public void sendB(String msg) {
        CorrelationData correlationData = new CorrelationData(msg);
        logger.info("direct.b send message: " + msg);
        amqpTemplate.convertAndSend("direct.b", msg,
                message -> {
                    // 设置60秒过期
                    message.getMessageProperties().setExpiration("60000");
                    return message;
                },
                correlationData);
    }

    /**
     * RPC 同步返回
     *
     * @param orderId
     * @return
     */
    public OrderMessage send(Long orderId) {
        logger.info("direct.rpc send message:" + orderId);
        OrderMessage orderMessage = (OrderMessage) amqpTemplate.convertSendAndReceive("direct.rpc", orderId);
        return orderMessage;
    }

}
