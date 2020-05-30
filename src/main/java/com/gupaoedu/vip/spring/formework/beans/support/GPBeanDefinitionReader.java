package com.gupaoedu.vip.spring.formework.beans.support;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

//对配置文件的查找、解析、读取
public class GPBeanDefinitionReader {

    private final List<String> registyBeanClasses = new ArrayList<>();

    private final Properties config = new Properties();

    private final String scan_package = "scanPackage";



}
