package com.example.demo.lab.design;

/**
 * @Description: 功能描述
 * @Author: Mr.Min
 * @Date: 2020-08-31
 **/
public class SingletonDemo2 {
    private static final SingletonDemo2 instance = new SingletonDemo2();

    private SingletonDemo2() {
    }

    public static SingletonDemo2 getInstance() {
        return SingletonDemo2.instance;
    }

    public String getId() {
        return "";
    }
}
