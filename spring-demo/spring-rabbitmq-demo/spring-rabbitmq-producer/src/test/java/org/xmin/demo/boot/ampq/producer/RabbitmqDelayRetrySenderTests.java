package org.xmin.demo.boot.ampq.producer;

import com.example.demo.core.entity.OrderMessage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.example.demo.rabbitmq.SpringBootRabbitmqProducerApplication;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Description: 功能描述
 * @Author: Mr.Min
 * @Date: 2019-08-11
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringBootRabbitmqProducerApplication.class)
public class RabbitmqDelayRetrySenderTests {

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Test
    public void sendAll() {
        for (int i = 0; i < 10; i++) {
            OrderMessage orderMessage = new OrderMessage();
            orderMessage.setId(0L + i);
            orderMessage.setBuyerId(2000000L + i);
            orderMessage.setOrderDate(new Date());
            amqpTemplate.convertAndSend("retry.order.exchange", "retry.order", orderMessage);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            System.out.println(orderMessage.getId() + "：" + sdf.format(new Date()));
        }
    }

    @Test
    public void sendOne() {
        OrderMessage orderMessage = new OrderMessage();
        orderMessage.setId(6L);
        orderMessage.setBuyerId(6L);
        orderMessage.setOrderDate(new Date());
        amqpTemplate.convertAndSend("retry.order.exchange", "retry.order", orderMessage);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(orderMessage.getId() + "：" + sdf.format(new Date()));
    }

}
