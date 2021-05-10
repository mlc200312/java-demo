package com.example.demo.rabbitmq.consumer.listener;

import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.stereotype.Component;

/**
 * @Description: 功能描述
 * @Author: Mr.Min
 * @Date: 2019-08-28
 **/
@Component
public class MessageListener implements ChannelAwareMessageListener {

    private static final Logger logger = LoggerFactory.getLogger("Message");

    /**
     * 1、处理成功，这种时候用basicAck确认消息；
     * 2、可重试的处理失败，这时候用basicNack将消息重新入列；
     * 3、不可重试的处理失败，这时候使用basicNack将消息丢弃。
     * <p>
     * basicNack(long deliveryTag, boolean multiple, boolean requeue)
     * deliveryTag:该消息的index
     * multiple：是否批量.true:将一次性拒绝所有小于deliveryTag的消息。
     * requeue：被拒绝的是否重新入队列
     */
    @Override
    public void onMessage(Message message, Channel channel) throws Exception {

        byte[] body = message.getBody();
        String json = new String(body);
        logger.info("接收到消息:" + json);
        try {
            if (true) {
                logger.info("消息消费成功");

                //确认消息消费成功
                channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            } else if (true) {

                channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
            } else { //消费失败

                channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
            }
        } catch (Exception e) {
            //消息丢弃
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
            logger.error("This message:" + json + " conversion JSON error ");
        }
    }
}