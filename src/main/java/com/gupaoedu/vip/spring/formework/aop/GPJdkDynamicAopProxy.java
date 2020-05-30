package com.gupaoedu.vip.spring.formework.aop;

import com.gupaoedu.vip.spring.formework.aop.aspect.GPAdvice;
import com.gupaoedu.vip.spring.formework.aop.intercept.GPMethodInvocation;
import com.gupaoedu.vip.spring.formework.aop.support.GPAdvisedSupport;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

/***
 * 使用jdk proxy api生成代理类
 * */
public class GPJdkDynamicAopProxy implements GPAopProxy, InvocationHandler {


    private GPAdvisedSupport config;

    public GPJdkDynamicAopProxy(GPAdvisedSupport config) {
        this.config = config;
    }


    @Override
    public Object getProxy() {
        return getProxy(this.config.getTargetClass().getClassLoader());
    }

    @Override
    public Object getProxy(ClassLoader classLoader) {
        return Proxy.newProxyInstance(classLoader,this.config.getTargetClass().getInterfaces(),this);
    }

    //invoke 方法是执行代理的关键入口
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if("toString".equals(method.getName())){
            return "toString";
        }

        //将每一个JoinPoint 也就是被代理的业务方法(Method) 封装成一个拦截器 ,组合成一个拦截器链
        List<Object> interceptorsAndDynamicMethodMatchers = config.getInterceptorsAndDynamicInterceptionAdvice(method,this.config.getTargetClass());
        //交给拦截器链  MethodInvocation 的proceed方法执行
        GPMethodInvocation invocation = new GPMethodInvocation(proxy,this.config.getTarget(),method,args,this.config.getTargetClass(),interceptorsAndDynamicMethodMatchers);
        return invocation.proceed();
    }

}
