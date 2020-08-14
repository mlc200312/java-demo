package org.xmin.demo.rabbit.sdk;

import mxc.sdk.rabbit.annotation.EnableMqConsumer;
import mxc.sdk.rabbit.annotation.EnableMqProducer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Description: 功能描述
 * @Author: Mr.Min
 * @Date: 2019-08-30
 **/
@EnableMqProducer
@EnableMqConsumer
@SpringBootApplication
public class SpringRabbitSdkDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringRabbitSdkDemoApplication.class, args);
    }

}
