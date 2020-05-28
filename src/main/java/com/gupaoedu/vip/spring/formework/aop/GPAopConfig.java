package com.gupaoedu.vip.spring.formework.aop;

import lombok.Data;

/***
 * aop配置封装
 * */
@Data
public class GPAopConfig {
    //以下配置与properties文件中保持一致

    //切面表达式
    private String pointCut;
    // 切面类
    private String aspectClass;
    //切面前置通知
    private String aspectBefore;
    //切面后置通知
    private String aspectAfter;
    //切面异常通知
    private String aspectAfterThrow;
    //切面异常类型
    private String aspectAfterThrowingName;

}
