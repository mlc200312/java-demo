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
import org.xmin.demo.core.entity.Order;

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
    public void onMessage(Order order, Message message, Channel channel) throws Exception {

        order.setRemark("---------消息正常执行-----------");
        processMessage(order);
    }

    private void processMessage(Order order) throws Exception {

        if (order.getId() % 3 == 0) {
            logger.error("消息执行时失败:", order);
            order.setRemark(order.getRemark() + ":重试！");
            throw new BusinessException("消息执行失败！");
        }

        logger.info("Received <" + order + ">");
    }

}
