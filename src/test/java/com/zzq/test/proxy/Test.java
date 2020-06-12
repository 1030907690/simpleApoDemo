package com.zzq.test.proxy;
/*
 * 烛光学院 springaop的基石——大神带你详解动态代理技术
 * https://www.ixigua.com/i6539690901613052420/
 * */
public class Test {

	 
	public static void main(String[] args) {
		UserService service = (UserService) MyProxy.newProxyInstance(Test.class.getClassLoader()
				, UserService.class, new MyCustomInvocationHandler(new UserServiceImpl()));
		try {
			//service是一个生成的代理类 
			service.test();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
