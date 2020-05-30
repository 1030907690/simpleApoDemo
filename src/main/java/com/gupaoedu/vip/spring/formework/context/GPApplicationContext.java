package com.gupaoedu.vip.spring.formework.context;

import com.gupaoedu.vip.spring.formework.beans.support.GPBeanDefinitionReader;
import com.gupaoedu.vip.spring.formework.beans.support.GPDefaultListableBeanFactory;
import com.gupaoedu.vip.spring.formework.core.GPBeanFactory;

/**
 * 按之前源码分析的套路 ioc，di ，mvc，aop
 * */
public class GPApplicationContext extends GPDefaultListableBeanFactory implements GPBeanFactory {

    private String [] configLocations;

    private GPBeanDefinitionReader reader;

    @Override
    public Object getBean(String beanName) throws Exception {
        return null;
    }

    @Override
    public Object getBean(Class<?> beanClass) throws Exception {
        return null;
    }
}
