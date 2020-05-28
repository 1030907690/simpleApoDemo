package com.gupaoedu.vip.spring.formework.aop.aspect;

import com.gupaoedu.vip.spring.formework.aop.intercept.GPMethodIntercept;
import com.gupaoedu.vip.spring.formework.aop.intercept.GPMethodInvocation;

import java.lang.reflect.Method;

/***
 * 异常通知具体实现
 * */
public class GPAfterThrowingAdvice extends GPAbstractAspectJAdvice implements GPAdvice, GPMethodIntercept {

    private String throwingName;

    private GPMethodInvocation mi;


    public GPAfterThrowingAdvice(Method aspectMethod, Object aspectTarget) {
        super(aspectMethod, aspectTarget);
    }

    @Override
    public Object invoke(GPMethodInvocation mi) throws Throwable {
        try {
            return mi.proceed();
        } catch (Throwable ex) {
            invokeAdviceMethod(mi, null, ex.getCause());
            throw ex;
        }
    }

    public void setThrowingName(String throwingName) {
        this.throwingName = throwingName;
    }
}
