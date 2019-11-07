package org.xmin.demo.rabbit.sdk.producer;

import com.xmin.lab.common.util.DateUtils;
import com.xmin.lab.common.util.RandomUtils;
import mxc.sdk.rabbit.annotation.MqSender;
import org.springframework.stereotype.Component;
import org.xmin.demo.core.entity.OrderMessage;

/**
 * @Description: 功能描述
 * @Author: Mr.Min
 * @Date: 2019-08-30
 **/
@Component
public class OrderSender {

    @MqSender(exchange = "topic.order.exchange")
    public OrderMessage sendMessage() {
        OrderMessage message = new OrderMessage();
        message.setId(RandomUtils.nextLong());
        message.setBuyerId(RandomUtils.nextLong());
        message.setOrderDate(DateUtils.now());
        message.setRemark("测试spring.rabbit.sdk");
        return message;
    }

}
