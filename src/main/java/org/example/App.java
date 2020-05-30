package org.example;

import com.gupaoedu.vip.spring.formework.ioc.ApplicationContext;
import com.gupaoedu.vip.spring.formework.ioc.DefaultApplicationContextImpl;
import com.gupaoedu.vip.spring.demo.service.ITestService;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        ApplicationContext context = new DefaultApplicationContextImpl();
        ITestService testService = (ITestService) context.getBean("testService");

        System.out.println(testService);

        testService.test();

    }
}
