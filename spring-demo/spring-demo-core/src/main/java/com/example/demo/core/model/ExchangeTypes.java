package com.example.demo.core.model;

/**
 * @Description: 交换机类型
 * @Author: Mr.Min
 * @Date: 2019-08-28
 **/
public abstract class ExchangeTypes {
    public static final String DIRECT = "direct";
    public static final String TOPIC = "topic";
    public static final String FANOUT = "fanout";
    public static final String HEADERS = "headers";
    public static final String SYSTEM = "system";

    public ExchangeTypes() {
    }
}