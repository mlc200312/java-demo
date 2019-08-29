package org.xmin.demo.boot.amqp.producer;

import com.xmin.lab.common.logging.Logger;
import com.xmin.lab.common.logging.LoggerFactory;
import com.xmin.lab.common.logging.LoggerType;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.xmin.demo.core.entity.Order;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Description: 延迟队列-生产者
 * @Author: Mr.Min
 * @Date: 2019-08-02
 **/
@Component
public class DelayPaySender {

    private Logger logger = LoggerFactory.getLogger(LoggerType.MESSAGE);

    @Autowired
    private RabbitTemplate amqpTemplate;

    public void send(Order order) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        CorrelationData correlationData = new CorrelationData(String.valueOf(order.getId()));
        amqpTemplate.convertAndSend("direct.pay.exchange", "OrderPay", order, correlationData);
        logger.info(order.getId() + "：" + sdf.format(new Date()));
    }

}
