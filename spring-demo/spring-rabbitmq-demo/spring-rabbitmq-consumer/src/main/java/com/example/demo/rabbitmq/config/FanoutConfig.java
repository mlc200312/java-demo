package com.example.demo.rabbitmq.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description: FanoutExchange交换机是转发消息到所有绑定队列（广播模式，和routingKey没有关系）。
 * @Author: Mr.Min
 * @Date: 2019-07-29
 **/
@Configuration
public class FanoutConfig {
    @Bean
    Queue reportPaymentQueue() {
        return new Queue("fanout.report.payment");
    }

    @Bean
    Queue reportRefundQueue() {
        return new Queue("fanout.report.refund");
    }

    @Bean
    FanoutExchange reportExchange() {
        return new FanoutExchange("fanout.report.exchange");
    }

    @Bean
    Binding bindingReportPaymentExchange(Queue reportPaymentQueue, FanoutExchange reportExchange) {
        return BindingBuilder.bind(reportPaymentQueue).to(reportExchange);
    }

    @Bean
    Binding bindingReportRefundExchange(Queue reportRefundQueue, FanoutExchange reportExchange) {
        return BindingBuilder.bind(reportRefundQueue).to(reportExchange);
    }
}