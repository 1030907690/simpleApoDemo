package com.gupaoedu.vip.spring.formework.ioc;

import com.gupaoedu.vip.spring.formework.aop.GPAopConfig;
import com.gupaoedu.vip.spring.formework.aop.GPAopProxy;
import com.gupaoedu.vip.spring.formework.aop.GPJdkDynamicAopProxy;
import com.gupaoedu.vip.spring.formework.aop.support.GPAdvisedSupport;
import com.gupaoedu.vip.spring.demo.service.impl.TestServiceImpl;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class DefaultApplicationContextImpl implements ApplicationContext {

    private final Map<String, Object> beanDefinition = new HashMap<>();


    public DefaultApplicationContextImpl() {
        try {
            refresh();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void refresh() throws Exception {
        finishBeanFactoryInitialization();
    }

    private void finishBeanFactoryInitialization() throws Exception {
        registerBeanDefinition("testService", TestServiceImpl.class);
    }

    @Override
    public Object getBean(String beanName) {
        return beanDefinition.get(beanName);
    }

    private void registerBeanDefinition(String beanName, Class clz) throws Exception {
        GPAdvisedSupport config = instantionAopConfig();
        config.setTargetClass(clz);
        config.setTarget(clz.newInstance());
        Object instance = createProxy(config).getProxy();
        beanDefinition.put(beanName, instance);
    }

    public GPAdvisedSupport instantionAopConfig() {

        GPAopConfig config = new GPAopConfig();
        // 设置值
        Properties props = new Properties();
        //查找配置文件的属性 并且都合并到props
        ClassPathResource location = new ClassPathResource("application.properties");
        try {
            PropertiesLoaderUtils.fillProperties(props, new EncodedResource(location, (String) null));
        } catch (IOException e) {
            e.printStackTrace();
        }
        config.setPointCut(props.getProperty("pointCut"));
        config.setAspectClass(props.getProperty("aspectClass"));
        config.setAspectBefore(props.getProperty("aspectBefore"));
        config.setAspectAfter(props.getProperty("aspectAfter"));
        config.setAspectAfterThrow(props.getProperty("aspectAfterThrow"));
        config.setAspectAfterThrowingName(props.getProperty("aspectAfterThrowingName"));
        return new GPAdvisedSupport(config);
    }


    private GPAopProxy createProxy(GPAdvisedSupport config) {
        Class targetClass = config.getTargetClass();
        if (targetClass.getInterfaces().length > 0) {
            return new GPJdkDynamicAopProxy(config);
        }
        //TODO 没有实现接口则使用cglib代理 暂时没写
        return null;
    }
}
