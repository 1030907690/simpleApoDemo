package com.gupaoedu.vip.spring.formework.aop.support;

import com.gupaoedu.vip.spring.formework.aop.GPAopConfig;
import com.gupaoedu.vip.spring.formework.aop.aspect.GPAfterReturningAdvice;
import com.gupaoedu.vip.spring.formework.aop.aspect.GPAfterThrowingAdvice;
import com.gupaoedu.vip.spring.formework.aop.aspect.GPMethodBeforeAdvice;
import lombok.Data;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/***
 * 主要用来解析和封装aop配置
 * */
public class GPAdvisedSupport {

    private Class targetClass;

    private Object target;

    private Pattern pointCutClassPattern;


    private transient Map<Method, List<Object>> methodCache;

    private GPAopConfig config;

    public GPAdvisedSupport(GPAopConfig config) {
        this.config = config;
    }

    public List<Object> getInterceptorsAndDynamicInterceptionAdvice(Method method, Class<?> targetClass) throws Exception {
        List<Object> cached = methodCache.get(method);
        //缓存未命中，则进行下一步处理
        if (cached == null) {
            Method m = targetClass.getMethod(method.getName(), method.getParameterTypes());
            cached = methodCache.get(m);
            //存入缓存
            this.methodCache.put(m, cached);
        }
        return cached;
    }


    public boolean pointCutMatch() {
        return pointCutClassPattern.matcher(this.targetClass.toString()).matches();
    }

    private void parse() {
        //pointCut表达式
        String pointCut = config.getPointCut()
                .replaceAll("\\.", "\\\\.")
                .replaceAll("\\\\.\\*", ".*")
                .replaceAll("\\(", "\\\\(")
                .replaceAll("\\)", "\\\\)");

        String pointCutForClass = pointCut.substring(0, pointCut.lastIndexOf("\\(") - 4);
        pointCutClassPattern = Pattern.compile("class" + pointCutForClass.substring(pointCutForClass.lastIndexOf(" ") + 1));

        methodCache = new HashMap<>();
        Pattern pattern = Pattern.compile(pointCut);


        try {
            Class aspectClass = Class.forName(config.getAspectClass());
            Map<String, Method> aspectMethods = new HashMap<>();
            for (Method method : aspectClass.getMethods()) {
                aspectMethods.put(method.getName(), method);
            }
            //在这里得到的方法都是原生方法

            for (Method m : targetClass.getMethods()) {
                String methodString = m.toString();
                if (methodString.contains("throws")) {
                    methodString = methodString.substring(0, methodString.lastIndexOf("throws")).trim();
                }

                Matcher matcher = pattern.matcher(methodString);
                if (matcher.matches()) {
                    //能满足切面规则的类，添加大aop配置中
                    List<Object> advices = new LinkedList<>();
                    //前置通知
                    if (!(null == config.getAspectBefore() || "".equals(config.getAspectBefore().trim()))) {
                        advices.add(new GPMethodBeforeAdvice(aspectMethods.get(config.getAspectBefore()), aspectClass.newInstance()));
                    }
                    //后置通知
                    if (!(null == config.getAspectAfter() || "".equals(config.getAspectAfter().trim()))) {
                        advices.add(new GPAfterReturningAdvice(aspectMethods.get(config.getAspectAfter()), aspectClass.newInstance()));
                    }
                    //异常通知
                    if (!(null == config.getAspectAfterThrow() || "".equals(config.getAspectAfterThrow().trim()))) {
                        GPAfterThrowingAdvice afterThrowingAdvice = new GPAfterThrowingAdvice(aspectMethods.get(config.getAspectAfterThrow()), aspectClass.newInstance());
                        afterThrowingAdvice.setThrowingName(config.getAspectAfterThrowingName());
                        advices.add(afterThrowingAdvice);
                    }

                    methodCache.put(m, advices);
                    //methodCache.put(targetClass.getInterfaces()[0].getMethod(m.getName(),m.getParameterTypes()), advices);

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void setTargetClass(Class targetClass) {
        this.targetClass = targetClass;
        parse();
    }

    public void setTarget(Object target) {
        this.target = target;
    }

    public void setPointCutClassPattern(Pattern pointCutClassPattern) {
        this.pointCutClassPattern = pointCutClassPattern;
    }

    public void setMethodCache(Map<Method, List<Object>> methodCache) {
        this.methodCache = methodCache;
    }

    public void setConfig(GPAopConfig config) {
        this.config = config;
    }

    public Class getTargetClass() {
        return targetClass;
    }

    public Object getTarget() {
        return target;
    }

    public Pattern getPointCutClassPattern() {
        return pointCutClassPattern;
    }

    public Map<Method, List<Object>> getMethodCache() {
        return methodCache;
    }

    public GPAopConfig getConfig() {
        return config;
    }
}
