package com.gupaoedu.vip.spring.formework.aop.aspect;

import com.gupaoedu.vip.spring.formework.aop.intercept.GPMethodIntercept;
import com.gupaoedu.vip.spring.formework.aop.intercept.GPMethodInvocation;

import java.lang.reflect.Method;

/***
 * 前置通知具体实现
 * */
public class GPMethodBeforeAdvice extends GPAbstractAspectJAdvice implements GPAdvice, GPMethodIntercept {

    private GPJoinPoint joinPoint;

    public GPMethodBeforeAdvice(Method aspectMethod, Object target) {
        super(aspectMethod, target);
    }


    public void before(Method method, Object[] args, Object target) throws Throwable {
        invokeAdviceMethod(this.joinPoint, null, null);
    }

    @Override
    public Object invoke(GPMethodInvocation mi) throws Throwable {
        this.joinPoint = mi;
        this.before(mi.getMethod(), mi.getArguments(), mi.getThis());
        return mi.proceed();
    }


}
