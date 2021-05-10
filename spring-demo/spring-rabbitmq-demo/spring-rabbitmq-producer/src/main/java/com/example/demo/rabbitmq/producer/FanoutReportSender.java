package com.example.demo.rabbitmq.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Description: 功能描述
 * @Author: Mr.Min
 * @Date: 2019-07-29
 **/
@Component
public class FanoutReportSender {

    private static final Logger logger = LoggerFactory.getLogger("Message");

    @Autowired
    private AmqpTemplate amqpTemplate;

    public void send(String msg) {
        logger.info("fanout.generate.reports send message: " + msg);
        amqpTemplate.convertAndSend("fanout.report.exchange", "fanout.generate.reports", msg);
    }

}