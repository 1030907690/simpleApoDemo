package com.gupaoedu.vip.spring.demo.service.impl;

import com.gupaoedu.vip.spring.demo.service.ITestService;

public class TestServiceImpl implements ITestService {

    @Override
    public Integer test() {
        System.out.println("测试aop方法");
        return 2;
    }
}
