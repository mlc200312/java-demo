package org.xmin.demo.boot.amqp.consumer;

import com.rabbitmq.client.Channel;
import com.xmin.lab.base.core.BusinessException;
import com.xmin.lab.common.logging.Logger;
import com.xmin.lab.common.logging.LoggerFactory;
import com.xmin.lab.common.logging.LoggerType;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.xmin.demo.core.entity.OrderMessage;

/**
 * @Description: 重试死信队列
 * @Author: Mr.Min
 * @Date: 2019-08-11
 **/
@Component
public class RetryReceive {

    private Logger logger = LoggerFactory.getLogger(LoggerType.MESSAGE);

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
            throw new BusinessException("消息执行失败！");
        }

        logger.info("Received <" + orderMessage + ">");
    }

}
