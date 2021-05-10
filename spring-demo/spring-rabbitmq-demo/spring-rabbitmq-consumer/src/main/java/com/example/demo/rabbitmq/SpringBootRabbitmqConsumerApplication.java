package com.example.demo.rabbitmq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Description: Spring boot 整合消息队列 Rabbitmq
 * @Author: Mr.Min
 * @Date: 2019-07-25
 **/
@SpringBootApplication
public class SpringBootRabbitmqConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootRabbitmqConsumerApplication.class, args);
    }

}
