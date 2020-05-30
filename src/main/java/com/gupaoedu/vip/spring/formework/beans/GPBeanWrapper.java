package com.gupaoedu.vip.spring.formework.beans;

public class GPBeanWrapper {

    private Object wrappedInstance;

    private Class<?> wrappedClass;


    public void setWrappedInstance(Object wrappedInstance) {
        this.wrappedInstance = wrappedInstance;
    }

    public void setWrappedClass(Class<?> wrappedClass) {
        this.wrappedClass = wrappedClass;
    }

    public Object getWrappedInstance() {
        return wrappedInstance;
    }

    // 返回代理以后的Class
    // 可能会是这个 $Proxy0
    public Class<?> getWrappedClass() {
        return wrappedClass;
    }
}
