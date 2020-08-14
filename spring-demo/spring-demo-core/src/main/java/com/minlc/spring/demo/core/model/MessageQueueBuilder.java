package com.minlc.spring.demo.core.model;

import java.util.StringJoiner;

/**
 * @Description: 功能描述
 * @Author: Mr.Min
 * @Date: 2019-08-11
 **/
public class MessageQueueBuilder {

    private String exchangeType;
    private String exchange;
    private String queue;
    private String routingKey;

    private MessageQueueBuilder(String queue) {
        this.queue = queue;
    }

    public static MessageQueueBuilder queue(String queue) {
        return new MessageQueueBuilder(queue).to(ExchangeTypes.TOPIC, queue).with(queue);
    }

    public MessageQueueBuilder to(String exchangeType, String exchange) {
        this.exchangeType = exchangeType;
        this.exchange = exchange;
        return this;
    }

    public MessageQueueBuilder with(String routingKey) {
        this.routingKey = routingKey;
        return this;
    }

    public MessageQueue build() {
        return new MessageQueue(this.exchangeType, this.exchange, this.queue, this.routingKey);
    }

    public MessageQueue build(String prefix) {
        String exchangeType = this.prefixValue(prefix, this.exchangeType);
        String exchange = this.prefixValue(prefix, this.exchange);
        String queue = this.prefixValue(prefix, this.queue);
        String routingKey = this.prefixValue(prefix, this.routingKey);
        return new MessageQueue(exchangeType, exchange, queue, routingKey);
    }

    private String prefixValue(String prefix, String str) {
        return new StringJoiner(".").add(prefix).add(str).toString();
    }

}
