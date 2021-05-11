package com.example.demo.rabbitmq.bean;

import lombok.Data;

/**
 * @Description: 功能描述
 * @Author: Mr.Min
 * @Date: 2019-08-11
 **/
@Data
public class Amqp {

    private MessageQueue messageQueue;
    private MessageQueue dlxMessageQueue;

    public Amqp(MessageQueue messageQueue) {
        this.messageQueue = messageQueue;
    }

    public Amqp(String exchange, String queue, String routingKey) {
        this.messageQueue = this.messageQueue(exchange, queue, routingKey);
        this.dlxMessageQueue = this.dlxMessageQueue();
    }

    private MessageQueue messageQueue(String exchange, String queue, String routingKey) {
        return this.messageQueue(ExchangeTypes.TOPIC, exchange, queue, routingKey);
    }

    private MessageQueue messageQueue(String exchangeType, String exchange, String queue, String routingKey) {
        String prefix = "xmin";
        return MessageQueueBuilder.queue(queue).to(exchangeType, exchange).with(routingKey).build(prefix);
    }

    public MessageQueue dlxMessageQueue() {
        String suffix = ".dlx";
        MessageQueue messageQueue = this.getMessageQueue();
        String exchange = messageQueue.getExchange() + suffix;
        String queue = messageQueue.getQueue() + suffix;
        String routingKey = messageQueue.getRoutingKey() + suffix;
        return MessageQueueBuilder.queue(queue).to(messageQueue.getExchangeType(), exchange).with(routingKey).build();
    }
}
