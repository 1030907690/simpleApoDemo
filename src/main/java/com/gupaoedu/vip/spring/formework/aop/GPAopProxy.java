package com.gupaoedu.vip.spring.formework.aop;

/***
 * 代理工厂的顶层接口  提供获取代理对象的顶层入口
 * */
//默认使用jdk代理
public interface GPAopProxy {


    //获得一个代理对象
    Object getProxy();

    // 提供自定义类加载器获得一个代理对象
    Object getProxy(ClassLoader classLoader);


}
