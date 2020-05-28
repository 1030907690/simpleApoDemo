package com.gupaoedu.vip.spring.formework.aop.aspect;

import java.lang.reflect.Method;

/**
 * 回调切点 通过他可以获得被代理的业务方法的所有信息
 * */
public interface GPJoinPoint {

    Method getMethod(); //业务方法本身

    Object[] getArguments(); //该方法的实参列表

    Object getThis();// 该方法所属实例对象

    //在joinpoint中添加自定义属性
    void setUserAttribute(String key, Object value);

    //从已添加的自定义属性中获取一个属性值
    Object getUserAttribute(String key);
}
