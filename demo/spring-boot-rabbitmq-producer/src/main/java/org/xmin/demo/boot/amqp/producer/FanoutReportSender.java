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
public class FanoutReportSender {

    private Logger logger = LoggerFactory.getLogger(LoggerType.MESSAGE);

    @Autowired
    private AmqpTemplate amqpTemplate;

    public void send(String msg) {
        logger.info("fanout.generate.reports send message: " + msg);
        amqpTemplate.convertAndSend("fanout.report.exchange", "fanout.generate.reports", msg);
    }

}