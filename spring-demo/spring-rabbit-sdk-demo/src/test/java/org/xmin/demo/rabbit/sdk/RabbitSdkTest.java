package org.xmin.demo.rabbit.sdk;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.Order;
import org.springframework.test.context.junit4.SpringRunner;
import org.xmin.demo.rabbit.sdk.producer.OrderSender;

/**
 * @Description: 功能描述
 * @Author: Mr.Min
 * @Date: 2019-08-30
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringRabbitSdkDemoApplication.class)
public class RabbitSdkTest {

    @Autowired
    private OrderSender orderSender;

    @Test
    public void orderSenderTest() {
        orderSender.sendMessage();
    }

}
