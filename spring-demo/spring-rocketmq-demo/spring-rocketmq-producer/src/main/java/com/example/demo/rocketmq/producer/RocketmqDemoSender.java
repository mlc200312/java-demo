package com.example.demo.rocketmq.producer;

import com.example.demo.core.entity.OrderMessage;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @description: 业务描述
 * @author: Mr.Min
 * @date: 2021/5/10
 **/
@Component
public class RocketmqDemoSender {

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    /**
     * Producer端发送同步消息
     * <p>
     * 这种可靠性同步地发送方式使用的比较广泛，比如：重要的消息通知，短信通知。
     */
    public void sendMsg() {
        OrderMessage random = OrderMessage.random();
        rocketMQTemplate.convertAndSend("test-topic", random);
    }

    /**
     * 发送异步消息
     * <p>
     * 异步消息通常用在对响应时间敏感的业务场景，即发送端不能容忍长时间地等待Broker的响应。
     */
    public void sendSyncMsg() {
        OrderMessage random = OrderMessage.random();
        rocketMQTemplate.asyncSend("test-topic", random, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                System.out.println("执行成功！" + sendResult.toString());
            }

            @Override
            public void onException(Throwable throwable) {
                System.out.println("执行失败！" + throwable.getMessage());
            }
        });
    }

    /**
     * 单向发送消息
     * <p>
     * 这种方式主要用在不特别关心发送结果的场景，例如日志发送。
     */
    public void sendOnewayMsg() {
        OrderMessage random = OrderMessage.random();
        rocketMQTemplate.sendOneWay("test-topic", random);
    }

    /**
     * 发送延迟消息
     */
    public void sendDelayedMsg() {

    }

    /**
     * 发送顺序消息
     */
    public void sendOrderlyMsg() {

    }
}
