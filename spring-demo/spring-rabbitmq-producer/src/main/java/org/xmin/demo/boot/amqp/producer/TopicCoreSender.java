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
 * @Date: 2019-07-25
 **/
@Component
public class TopicCoreSender {

    private final Logger logger = LoggerFactory.getLogger(LoggerType.MESSAGE);

    @Autowired
    private AmqpTemplate amqpTemplate;

    public void user(String msg) {
        logger.info("topic.core.user send message: " + msg);
        amqpTemplate.convertAndSend("topic.core.exchange", "topic.core.user", msg);
    }

    public void userQuery(String msg) {
        logger.info("topic.core.user.query send message: " + msg);
        amqpTemplate.convertAndSend("topic.core.exchange", "topic.core.user.query", msg);
    }

}
