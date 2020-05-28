package com.gupaoedu.vip.spring.formework.aop.aspect;

import java.lang.reflect.Array;
import java.util.Arrays;

/***
 * 定义一个织入的切面逻辑  也就是针对目标代理对象增强的逻辑
 * 本类主要完成对方法调用的监控，监听目标方法每次执行所消耗的时间
 * */
public class LogAspect {
    //在调用一个方法前执行 before 方法
    public void before(GPJoinPoint joinPoint) {
        joinPoint.setUserAttribute("startTime_" + joinPoint.getMethod().getName(), System.currentTimeMillis());
        //这个方法中逻辑是由我们自己写的
        System.out.println(" Invoker before method " + " \n TargetObjet :" + joinPoint.getThis() + "\nArgs:" + Arrays.toString(joinPoint.getArguments()));
    }

    //在调用一个方法之后 执行after方法
    public void after(GPJoinPoint joinPoint) {
        System.out.println(" Invoker after method " + " \n TargetObjet :" + joinPoint.getThis() + "\nArgs:" + Arrays.toString(joinPoint.getArguments()));
        //TODO 时间相减
    }

    public void afterThrowing(GPJoinPoint joinPoint, Throwable ex) {
        System.out.println("出现异常 " + joinPoint.getThis());
    }
}
