package com.gupaoedu.vip.spring.formework.aop.aspect;

import java.lang.reflect.Method;

/***
 * 封装拦截器回调的通用逻辑 在Mini版本中主要封装了反射动态调用方法
 * */
public class GPAbstractAspectJAdvice implements GPAdvice {
    private Method aspectMethod;

    private Object aspectTarget;

    public GPAbstractAspectJAdvice(Method aspectMethod, Object aspectTarget) {
        this.aspectMethod = aspectMethod;
        this.aspectTarget = aspectTarget;
    }


    //反射动态调用方法
    protected Object invokeAdviceMethod(GPJoinPoint joinPoint, Object returnValue, Throwable ex) throws Throwable {
        Class<?>[] paramsTypes = this.aspectMethod.getParameterTypes();
        if (null == paramsTypes || paramsTypes.length == 0) {
            return this.aspectMethod.invoke(aspectTarget);
        } else {
            Object[] args = new Object[paramsTypes.length];
            for (int i = 0; i < paramsTypes.length; i++) {
                if (GPJoinPoint.class == paramsTypes[i]) {
                    args[i] = joinPoint;
                } else if (Throwable.class == paramsTypes[i]) {
                    args[i] = ex;
                } else if (Object.class == paramsTypes[i]) {
                    args[i] = returnValue;
                }
            }
            return this.aspectMethod.invoke(aspectTarget,args);
        }
    }

}
