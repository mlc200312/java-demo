package org.xmin.demo.boot.amqp.producer;


import com.xmin.lab.common.logging.Logger;
import com.xmin.lab.common.logging.LoggerFactory;
import com.xmin.lab.common.logging.LoggerType;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.xmin.demo.core.entity.Order;

/**
 * @Description: 功能描述
 * @Author: Mr.Min
 * @Date: 2019-07-25
 **/
@Component
public class DirectRpcSender {

    private final Logger logger = LoggerFactory.getLogger(LoggerType.MESSAGE);

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
    public Order send(Long orderId) {
        logger.info("direct.rpc send message:" + orderId);
        Order order = (Order) amqpTemplate.convertSendAndReceive("direct.rpc", orderId);
        return order;
    }

}
