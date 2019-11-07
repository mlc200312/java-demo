package org.xmin.demo.boot.ampq.producer;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.xmin.demo.boot.amqp.SpringBootRabbitmqProducerApplication;
import org.xmin.demo.boot.amqp.producer.*;
import org.xmin.demo.core.entity.OrderMessage;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 功能描述
 * @Author: Mr.Min
 * @Date: 2019-07-25
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringBootRabbitmqProducerApplication.class)
public class RabbitmqSenderTests {

    @Autowired
    private DirectRpcSender sender;
    @Autowired
    private TopicCoreSender coreSender;
    @Autowired
    private TopicPaymentSender paymentSender;
    @Autowired
    private HeadersCreditSender creditSender;
    @Autowired
    private FanoutReportSender reportSender;
    @Autowired
    private DelayPaySender orderSender;

    @Test
    public void directNotifyTest() {
        sender.sendA("测试（交换机——DirectExchange）：" + System.currentTimeMillis());
        sender.sendB("测试（交换机——DirectExchange）：" + System.currentTimeMillis());
        OrderMessage orderMessage = sender.send(900000001L);
        System.out.println(orderMessage);
    }

    @Test
    public void topicUserTest() {
        coreSender.user("测试（交换机——TopicExchange_User）：" + System.currentTimeMillis());
        coreSender.userQuery("测试（交换机——TopicExchange_UserQuery）：" + System.currentTimeMillis());
    }

    @Test
    public void topicPaymentTest() {
        paymentSender.order("测试（交换机——TopicExchange_Order）：" + System.currentTimeMillis());
        paymentSender.orderQuery("测试（交换机——TopicExchange_OrderQuery）：" + System.currentTimeMillis());
        paymentSender.orderDetailQuery("测试（交换机——TopicExchange_OrderDetailQuery）：" + System.currentTimeMillis());
    }

    @Test
    public void headersCreditTest() {
        Map<String, Object> cashHeaders = new HashMap<>();
        cashHeaders.put("type", "cash");
        Map<String, Object> fastHeaders = new HashMap<>();
        fastHeaders.put("aging", "fast");
        Map<String, Object> allHeaders = new HashMap<>();
        allHeaders.put("type", "cash");
        allHeaders.put("aging", "fast");
        creditSender.creditBank(cashHeaders, "银行授信(部分匹配)");
        creditSender.creditBank(allHeaders, "银行授信(全部匹配)");

        creditSender.creditFinance(fastHeaders, "金融公司授权(部分匹配)");
        creditSender.creditFinance(allHeaders, "金融公司授权(全部匹配)");
    }

    @Test
    public void fanoutReportsTest() {
        reportSender.send("开始生成报表！");
    }

    @Test
    public void delayPayTest() {
        for (int i = 0; i < 10; i++) {
            OrderMessage orderMessage = new OrderMessage();
            orderMessage.setId(1000000L + i);
            orderMessage.setBuyerId(2000000L + i);
            orderMessage.setOrderDate(new Date());
            orderSender.send(orderMessage);
        }
    }

}
