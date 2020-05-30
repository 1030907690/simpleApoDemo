package com.gupaoedu.vip.spring.formework.beans.config;

/***
 * 用来存储配置文件中的信息
 * 相当于保存在内存中的配置
 * */
public class GPBeanDefinition {

    private String beanClassName;//原生bean的全类名

    private boolean lazyInit = false; // 标记是否延时加载

    private String factoryBeanName;//保存beanName，在IOC容器中存储的key

    public void setLazyInit(boolean lazyInit) {
        this.lazyInit = lazyInit;
    }

    public void setFactoryBeanName(String factoryBeanName) {
        this.factoryBeanName = factoryBeanName;
    }

    public String getBeanClassName() {
        return beanClassName;
    }

    public void setBeanClassName(String beanClassName) {
        this.beanClassName = beanClassName;
    }

    public boolean isLazyInit() {
        return lazyInit;
    }

    public String getFactoryBeanName() {
        return factoryBeanName;
    }
}
