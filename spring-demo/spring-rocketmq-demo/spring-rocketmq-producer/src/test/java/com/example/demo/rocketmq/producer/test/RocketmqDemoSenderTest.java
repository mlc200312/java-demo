package com.example.demo.rocketmq.producer.test;

import com.example.demo.rocketmq.SpringRocketmqProducerApplication;
import com.example.demo.rocketmq.producer.RocketmqDemoSender;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @description: 业务描述
 * @author: Mr.Min
 * @date: 2021/5/10
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SpringRocketmqProducerApplication.class)
public class RocketmqDemoSenderTest {

    @Autowired
    private RocketmqDemoSender rocketmqDemoSender;

    @Test
    public void sendMsgTest() {

        rocketmqDemoSender.sendMsg();
    }

    @Test
    public void sendSyncMsgTest() {

        rocketmqDemoSender.sendSyncMsg();
    }

    @Test
    public void sendOnewayMsgTest() {

        rocketmqDemoSender.sendOnewayMsg();
    }
}
