package com.gupaoedu.vip.spring.formework.core;

/***
 * 单例工厂的顶层设计
 * */
public interface GPBeanFactory {

    /***
     * 根据beanName从ioc容器中获得一个实例的bean
     * @param beanName
     * @return
     * */
    Object getBean(String beanName)throws Exception;

    public Object getBean(Class<?> beanClass)throws Exception;

}
