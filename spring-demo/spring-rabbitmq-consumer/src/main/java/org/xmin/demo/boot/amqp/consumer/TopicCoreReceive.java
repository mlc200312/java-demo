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
 * @Description: Topic 模式
 * @Author: Mr.Min
 * @Date: 2019-07-29
 **/
@Component
public class TopicCoreReceive {

    private final Logger logger = LoggerFactory.getLogger(LoggerType.MESSAGE);

    @RabbitHandler
    @RabbitListener(queuesToDeclare = @Queue("topic.core"))
    public void receive(String content, Message message, Channel channel) throws IOException {
        logger.info(String.format("topic.core receive message: %s", content));
    }

}
