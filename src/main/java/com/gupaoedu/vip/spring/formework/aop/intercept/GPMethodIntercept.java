package com.gupaoedu.vip.spring.formework.aop.intercept;

/**
* 方法拦截器顶层接口
* */
public interface GPMethodIntercept {

    Object invoke(GPMethodInvocation mi) throws Throwable;

}
