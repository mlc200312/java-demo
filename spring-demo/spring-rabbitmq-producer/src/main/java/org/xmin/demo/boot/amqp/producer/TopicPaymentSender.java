package org.xmin.demo.boot.amqp.producer;

import com.xmin.lab.common.logging.Logger;
import com.xmin.lab.common.logging.LoggerFactory;
import com.xmin.lab.common.logging.LoggerType;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Description: 功能描述
 * @Author: Mr.Min
 * @Date: 2019-07-29
 **/
@Component
public class TopicPaymentSender {

    private final Logger logger = LoggerFactory.getLogger(LoggerType.MESSAGE);

    private static final String TOPIC_PAYMENT_EXCHANGE = "topic.payment.exchange";

    @Autowired
    private AmqpTemplate amqpTemplate;

    public void order(String msg) {
        logger.info("xmin.topic.payment.order send message: " + msg);
        amqpTemplate.convertAndSend(TOPIC_PAYMENT_EXCHANGE, "topic.payment.order", msg);
    }

    public void orderQuery(String msg) {
        logger.info("xmin.topic.payment.order.query send message: " + msg);
        amqpTemplate.convertAndSend(TOPIC_PAYMENT_EXCHANGE, "topic.payment.order.query", msg);
    }

    public void orderDetailQuery(String msg) {
        logger.info("xmin.topic.payment.order.detail.query send message: " + msg);
        amqpTemplate.convertAndSend(TOPIC_PAYMENT_EXCHANGE, "topic.payment.order.detail.query", msg);
    }

}