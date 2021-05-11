package com.example.demo.rabbitmq.config;

import com.example.demo.core.constant.QueueConstants;
import com.example.demo.rabbitmq.bean.Amqp;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description: TopicExchange是按规则转发消息，是交换机中最灵活的一个。也是最常用的一个。
 * <p>
 * TopicExchange交换机支持使用通配符*、#
 * *号只能向后多匹配一层路径。
 * #号可以向后匹配多层路径。
 * </p>
 * @Author: Mr.Min
 * @Date: 2019-08-11
 **/
@Configuration
public class TopicConfig {

    @Value("${xmin.amqp.prefix}")
    private String prefix;

    static final String NORMAL_EXCHANGE = "retry.order.exchange";

    static final String NORMAL_QUEUE = "retry.order.queue";

    static final String NORMAL_ROUTING_KEY = "retry.order";

    private Amqp xminAmqp() {
        return new Amqp(NORMAL_EXCHANGE, NORMAL_QUEUE, NORMAL_ROUTING_KEY);
    }

    @Bean
    Queue coreQueue() {
        return new Queue("topic.core");
    }

    @Bean
    Queue paymentQueue() {
        return new Queue("topic.payment");
    }

    @Bean
    TopicExchange coreExchange() {
        return new TopicExchange("topic.core.exchange");
    }

    @Bean
    TopicExchange paymentExchange() {
        return new TopicExchange("topic.payment.exchange");
    }

    /**
     * 通配符“*”只能向后多匹配一层路径
     *
     * @param coreQueue
     * @param coreExchange
     * @return
     */
    @Bean
    Binding bindingCoreExchange(Queue coreQueue, TopicExchange coreExchange) {
        return BindingBuilder.bind(coreQueue).to(coreExchange).with("topic.core.*");
    }

    /**
     * 通配符“#”能向后匹配任意层
     *
     * @param paymentQueue
     * @param paymentExchange
     * @return
     */
    @Bean
    Binding bindingPaymentExchange(Queue paymentQueue, TopicExchange paymentExchange) {
        return BindingBuilder.bind(paymentQueue).to(paymentExchange).with("topic.payment.#");
    }

    @Bean
    TopicExchange normalExchange() {
        return new TopicExchange(xminAmqp().getMessageQueue().getExchange());
    }

    @Bean
    Queue normalQueue() {
        return QueueBuilder.durable(xminAmqp().getMessageQueue().getQueue())
                .withArgument(QueueConstants.X_DEAD_LETTER_EXCHANGE, xminAmqp().getDlxMessageQueue().getExchange())
                .withArgument(QueueConstants.X_DEAD_LETTER_ROUTING_KEY, xminAmqp().getDlxMessageQueue().getRoutingKey())
                .build();
    }

    @Bean
    Binding normalBinding(Queue normalQueue, TopicExchange normalExchange) {
        return BindingBuilder.bind(normalQueue).to(normalExchange).with(xminAmqp().getMessageQueue().getRoutingKey());
    }

    @Bean
    TopicExchange dlxExchange() {
        return new TopicExchange(xminAmqp().getDlxMessageQueue().getExchange());
    }

    @Bean
    Queue dlxQueue() {
        return QueueBuilder.durable(xminAmqp().getDlxMessageQueue().getQueue()).build();
    }

    @Bean
    Binding dlxBinding(Queue dlxQueue, TopicExchange dlxExchange) {
        return BindingBuilder.bind(dlxQueue).to(dlxExchange).with(xminAmqp().getDlxMessageQueue().getRoutingKey());
    }

}
