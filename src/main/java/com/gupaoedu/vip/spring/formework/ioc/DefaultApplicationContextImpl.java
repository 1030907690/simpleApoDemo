package com.gupaoedu.vip.spring.formework.ioc;

import com.gupaoedu.vip.spring.formework.aop.GPAopConfig;
import com.gupaoedu.vip.spring.formework.aop.GPAopProxy;
import com.gupaoedu.vip.spring.formework.aop.GPJdkDynamicAopProxy;
import com.gupaoedu.vip.spring.formework.aop.support.GPAdvisedSupport;
import com.gupaoedu.vip.spring.formework.service.TestServiceImpl;
import com.sun.org.apache.regexp.internal.RE;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class DefaultApplicationContextImpl implements ApplicationContext {

    private final Map<String, Object> beanDefinition = new HashMap<>();


    public DefaultApplicationContextImpl() {
        try {
            instaniateBean("testService", TestServiceImpl.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public Object getBean(String beanName) {
        return null;
    }

    private void instaniateBean(String beanName,Class clz) throws Exception {
        GPAdvisedSupport config = instantionAopConfig();
        config.setTargetClass(clz);
        config.setTarget(clz.newInstance());
        Object instance = createProxy(config).getProxy();
        beanDefinition.put(beanName,instance);
    }

    public GPAdvisedSupport instantionAopConfig() {

        GPAopConfig config = new GPAopConfig();
        //TODO 设置值
        // 第一种
        Properties props = new Properties();
        //查找配置文件的属性 并且都合并到props
        ClassPathResource location = new ClassPathResource("application.properties");
        try {
            PropertiesLoaderUtils.fillProperties(props, new EncodedResource(location, (String) null));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new GPAdvisedSupport(config);
    }


    private GPAopProxy createProxy(GPAdvisedSupport config){
        Class targetClass = config.getTargetClass();
        if (targetClass.getInterfaces().length > 0 ){
            return new GPJdkDynamicAopProxy(config);
        }
        //TODO 没有实现接口使用cglib代理 暂时没写
        return null;
    }
}
