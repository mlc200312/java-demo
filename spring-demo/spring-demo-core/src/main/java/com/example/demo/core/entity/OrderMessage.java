package com.example.demo.core.entity;

import lombok.Data;

import java.util.Date;

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
}
