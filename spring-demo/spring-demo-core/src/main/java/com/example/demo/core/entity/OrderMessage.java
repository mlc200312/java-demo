package com.example.demo.core.entity;

import lombok.Data;

import java.util.Date;
import java.util.Random;

/**
 * @Description: 功能描述
 * @Author: Mr.Min
 * @Date: 2019-07-30
 **/
@Data
public class OrderMessage {

    private Long id;
    private Long buyerId;
    private Date orderDate;
    private String remark;

    public static OrderMessage random() {
        OrderMessage orderMessage = new OrderMessage();
        orderMessage.setId(new Random().nextLong());
        orderMessage.setBuyerId(new Random().nextLong());
        orderMessage.setOrderDate(new Date());
        orderMessage.setRemark(new Random().nextLong() + "");
        return orderMessage;
    }
}
