package org.xmin.demo.rabbit.sdk.consumer;

import com.xmin.lab.common.logging.Logger;
import com.xmin.lab.common.logging.LoggerFactory;
import com.xmin.lab.common.logging.LoggerType;
import mxc.sdk.rabbit.annotation.MqListener;
import org.springframework.stereotype.Component;
import org.xmin.demo.core.entity.OrderMessage;

/**
 * @Description: 功能描述
 * @Author: Mr.Min
 * @Date: 2019-08-30
 **/
@Component
public class OrderReceive {
    private final Logger logger = LoggerFactory.getLogger(LoggerType.MESSAGE);

    @MqListener(exchange = "topic.order.exchange", queue = "topic.order", routingKey = "trade")
    public void onMessage(OrderMessage message) throws Exception {
        logger.info(String.format("topic.core receive message: %s", message));
        System.out.println(1 / 0);
    }

}
