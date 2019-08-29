package org.xmin.demo.boot.amqp.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.xmin.demo.core.constant.QueueConstants;

/**
 * @Description: DirectExchange是RabbitMQ的默认交换机，直接使用routingKey匹配队列。
 * @Author: Mr.Min
 * @Date: 2019-07-25
 **/
@Configuration
public class DirectConfig {

    static final String DIRECT_PAY_EXCHANGE = "direct.pay.exchange";
    static final String DIRECT_DELAY_EXCHANGE = "direct.delay.exchange";

    static final String DIRECT_PAY_QUEUE = "direct.pay.queue";
    static final String DIRECT_DELAY_QUEUE = "direct.delay.pay";

    static final String DIRECT_ORDER_PAY_ROUTING_KEY = "OrderPay";
    static final String DIRECT_DELAY_ROUTING_KEY = "DelayKey";

    /**
     * 延迟队列
     *
     * @return
     */
    @Bean
    DirectExchange directDelayExchange() {
        return new DirectExchange(DIRECT_DELAY_EXCHANGE);
    }

    @Bean
    Queue directDelayQueue() {
        return new Queue(DIRECT_DELAY_QUEUE, true, false, false);
    }

    @Bean
    Binding bindingDelayDirect() {
        return BindingBuilder.bind(directDelayQueue()).to(directDelayExchange()).with("DelayKey");
    }

    /**
     * @Description 定义支付交换器
     * @Author jxb
     * @Date 2019-04-02 14:39:31
     */
    @Bean
    DirectExchange directPayExchange() {
        return new DirectExchange(DIRECT_PAY_EXCHANGE);
    }

    /**
     * 定义支付队列 绑定死信队列(其实是绑定的交换器，然后通过交换器路由键绑定队列) 设置过期时间
     * <p>
     * 消息被拒绝(basic.reject/basic.nack)并且requeue=false
     * 消息TTL过期
     * 队列达到最大长度
     * </p>
     *
     * @return
     */
    @Bean
    Queue directPayQueue() {
        return QueueBuilder.durable(DIRECT_PAY_QUEUE)
                //声明死信交换器
                .withArgument(QueueConstants.X_DEAD_LETTER_EXCHANGE, DIRECT_DELAY_EXCHANGE)
                // x-dead-letter-routing-key 如果没有指定，那么消息本身使用的 routing key 将被使用。
                .withArgument(QueueConstants.X_DEAD_LETTER_ROUTING_KEY, DIRECT_DELAY_ROUTING_KEY)
                //声明队列消息过期时间
                .withArgument(QueueConstants.X_MESSAGE_TTL, 10000)
                .build();
    }

    /**
     * 定义支付绑定
     *
     * @return
     */
    @Bean
    Binding bindingPayDirect() {
        return BindingBuilder.bind(directPayQueue()).to(directPayExchange()).with(DIRECT_ORDER_PAY_ROUTING_KEY);
    }

}
