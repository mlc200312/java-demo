package com.minlc.demo.laboratory.design;

/**
 * @Description: 功能描述
 * @Author: Mr.Min
 * @Date: 2020-08-31
 **/
public class SingletonDemo {

    private static class Singleton {
        static final SingletonDemo instance = new SingletonDemo();
    }

    private SingletonDemo() {
    }

    public static SingletonDemo getInstance() {
        return Singleton.instance;
    }

    public String getId() {
        return "";
    }
}
