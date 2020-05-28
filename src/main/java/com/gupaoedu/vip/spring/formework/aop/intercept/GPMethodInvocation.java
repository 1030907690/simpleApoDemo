package com.gupaoedu.vip.spring.formework.aop.intercept;

import com.gupaoedu.vip.spring.formework.aop.aspect.GPJoinPoint;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/***
 *执行拦截器链 相当于spring中的ReflectiveMethodInvocation
 *
 */
public class GPMethodInvocation implements GPJoinPoint {


    private Object proxy;// 代理对象

    private Method method;// 代理的目标方法

    private Object target;// 代理的目标对象

    private Class<?> targetClass;// 代理的目标类

    private Object[] arguments;//代理的方法的实参列表

    private List<Object> interceptorsAndDynamicMethodMatchers;//回调方法链

    //保存自定义属性
    private Map<String, Object> userAttributes;

    private int currentInterceptorIndex = -1;

    public GPMethodInvocation(Object proxy, Method method, Object target, Class<?> targetClass, Object[] arguments, List<Object> interceptorsAndDynamicMethodMatchers) {
        this.proxy = proxy;
        this.method = method;
        this.target = target;
        this.targetClass = targetClass;
        this.arguments = arguments;
        this.interceptorsAndDynamicMethodMatchers = interceptorsAndDynamicMethodMatchers;
    }

    public Object proceed() throws Throwable {
        //如果Intercept执行完了，则执行joinPoint
        if (this.currentInterceptorIndex == this.interceptorsAndDynamicMethodMatchers.size() - 1) {
            return method.invoke(this.target, this.arguments);
        }

        Object interceptorOrInterceptionAdvice = this.interceptorsAndDynamicMethodMatchers.get(++currentInterceptorIndex);
        // 如果要动态匹配joinPoint
        if (interceptorOrInterceptionAdvice instanceof GPMethodIntercept) {
            GPMethodIntercept mi = (GPMethodIntercept) interceptorOrInterceptionAdvice;
            return mi.invoke(this);
        } else {
            // 执行当前intercetpor
            return proceed();
        }
    }

    @Override
    public Method getMethod() {
        return this.method;
    }

    @Override
    public Object[] getArguments() {
        return this.arguments;
    }

    @Override
    public Object getThis() {
        return this.target;
    }

    @Override
    public void setUserAttribute(String key, Object value) {
        if (null != value) {
            if (this.userAttributes == null) {
                this.userAttributes = new HashMap<>();
            }

            this.userAttributes.put(key, value);
        } else {
            if (this.userAttributes != null) {
                this.userAttributes.remove(key);
            }
        }

    }


    @Override
    public Object getUserAttribute(String key) {
        return (this.userAttributes != null ? this.userAttributes.get(key) : null);
    }

}
