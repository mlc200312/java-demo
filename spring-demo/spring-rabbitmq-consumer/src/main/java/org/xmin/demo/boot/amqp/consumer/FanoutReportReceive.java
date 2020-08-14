package org.xmin.demo.boot.amqp.consumer;

import com.rabbitmq.client.Channel;
import com.xmin.lab.common.logging.Logger;
import com.xmin.lab.common.logging.LoggerFactory;
import com.xmin.lab.common.logging.LoggerType;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @Description: 交换机——FanoutExchange
 * @Author: Mr.Min
 * @Date: 2019-07-29
 **/
@Component
public class FanoutReportReceive {

    private Logger logger = LoggerFactory.getLogger(LoggerType.MESSAGE);

    @RabbitHandler
    @RabbitListener(queuesToDeclare = @Queue("fanout.report.payment"))
    public void payment(String content, Message message, Channel channel) throws IOException {
        logger.info(String.format("fanout.report.payment receive message: %s", content));
    }

    @RabbitHandler
    @RabbitListener(queuesToDeclare = @Queue("fanout.report.refund"))
    public void refund(String content, Message message, Channel channel) throws IOException {
        logger.info(String.format("fanout.report.refund receive message: %s", content));
    }
}