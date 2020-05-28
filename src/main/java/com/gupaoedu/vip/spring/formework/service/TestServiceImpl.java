package com.gupaoedu.vip.spring.formework.service;

public class TestServiceImpl implements ITestService{

    @Override
    public Integer test() {
        System.out.println("测试aop方法");
        return 2;
    }
}
