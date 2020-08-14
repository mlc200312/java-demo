package org.xmin.demo.boot.amqp.producer;

import com.xmin.lab.common.logging.Logger;
import com.xmin.lab.common.logging.LoggerFactory;
import com.xmin.lab.common.logging.LoggerType;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @Description: 功能描述
 * @Author: Mr.Min
 * @Date: 2019-07-29
 **/
@Component
public class HeadersCreditSender {

    private final Logger logger = LoggerFactory.getLogger(LoggerType.MESSAGE);

    @Autowired
    private AmqpTemplate amqpTemplate;

    public void creditBank(Map<String, Object> head, String msg) {
        logger.info("headers.credit.bank send message: " + msg);
        amqpTemplate.convertAndSend("headers.credit.bank.exchange", "headers.credit.bank", this.getMessage(head, msg));
    }

    public void creditFinance(Map<String, Object> head, String msg) {
        logger.info("headers.credit.finance send message: " + msg);
        amqpTemplate.convertAndSend("headers.credit.finance.exchange", "headers.credit.finance", this.getMessage(head, msg));
    }

    private Message getMessage(Map<String, Object> head, String msg) {
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.getHeaders().putAll(head);

        Message message = new Message(msg.getBytes(), messageProperties);
        return message;
    }

}
