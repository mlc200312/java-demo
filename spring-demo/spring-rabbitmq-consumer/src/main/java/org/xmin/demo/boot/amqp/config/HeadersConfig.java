package org.xmin.demo.boot.amqp.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.HeadersExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;


/**
 * @Description: HeadersExchange交换机是根据请求消息中设置的header attribute参数类型来匹配的（和routingKey没有关系）。
 * @Author: Mr.Min
 * @Date: 2019-07-29
 **/
@Configuration
public class HeadersConfig {

    @Bean
    Queue creditBankQueue() {
        return new Queue("headers.credit.bank");
    }

    @Bean
    Queue creditFinanceQueue() {
        return new Queue("headers.credit.finance");
    }

    @Bean
    HeadersExchange creditBankExchange() {
        return new HeadersExchange("headers.credit.bank.exchange");
    }

    @Bean
    HeadersExchange creditFinanceExchange() {
        return new HeadersExchange("headers.credit.finance.exchange");
    }

    @Bean
    Binding bindingCreditAExchange(Queue creditBankQueue, HeadersExchange creditBankExchange) {
        Map<String, Object> headerValues = new HashMap<>(2);
        headerValues.put("type", "cash");
        headerValues.put("aging", "fast");
        //全部匹配
        return BindingBuilder.bind(creditBankQueue).to(creditBankExchange).whereAll(headerValues).match();
    }

    @Bean
    Binding bindingCreditBExchange(Queue creditFinanceQueue, HeadersExchange creditFinanceExchange) {
        Map<String, Object> headerValues = new HashMap<>(2);
        headerValues.put("type", "cash");
        headerValues.put("aging", "fast");
        //部分匹配
        return BindingBuilder.bind(creditFinanceQueue).to(creditFinanceExchange).whereAny(headerValues).match();
    }
}