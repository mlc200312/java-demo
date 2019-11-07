package org.xmin.demo.boot.amqp.consumer;

import com.rabbitmq.client.Channel;
import com.xmin.lab.common.logging.Logger;
import com.xmin.lab.common.logging.LoggerFactory;
import com.xmin.lab.common.logging.LoggerType;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.xmin.demo.core.entity.OrderMessage;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Description: 延迟消费队列
 * @Author: Mr.Min
 * @Date: 2019-08-02
 **/
@Component
public class DelayPayReceive {

    private final Logger logger = LoggerFactory.getLogger(LoggerType.MESSAGE);

    /**
     * 延迟消费
     *
     * @param orderMessage
     * @param channel
     * @param message
     * @throws InterruptedException
     * @throws IOException
     */
    @RabbitHandler
    @RabbitListener(queues = "direct.delay.pay")
    public void onMessage(@Payload OrderMessage orderMessage, Channel channel, Message message) throws InterruptedException, IOException {

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            // 模拟执行任务
            logger.warn("这是延迟队列消费：" + orderMessage + "：" + sdf.format(new Date()));
        } catch (Exception e) {
            logger.error(String.format("延迟队列消费者失败[%s]！", orderMessage.getId()));
        }

    }

}
