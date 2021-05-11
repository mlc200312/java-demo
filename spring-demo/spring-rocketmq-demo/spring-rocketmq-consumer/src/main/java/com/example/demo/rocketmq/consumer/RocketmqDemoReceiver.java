package com.example.demo.rocketmq.consumer;

import com.example.demo.core.entity.OrderMessage;
import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * @description: 业务描述
 * @author: Mr.Min
 * @date: 2021/5/10
 **/
@Component
@RocketMQMessageListener(topic = "test-topic", consumerGroup = "test-consumer", consumeMode = ConsumeMode.ORDERLY)
public class RocketmqDemoReceiver implements RocketMQListener<OrderMessage> {

    @Override
    public void onMessage(OrderMessage orderMessage) {

        System.out.println("接受到消息了");
        System.out.println(orderMessage);
        System.out.println(1 / 0);
    }
}
