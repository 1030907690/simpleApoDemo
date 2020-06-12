package com.zzq.test.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class JdkProxyInvocationHandler implements InvocationHandler {
	
	private UserService userService;
	
	public JdkProxyInvocationHandler(UserService userService){
		this.userService = userService;
	}
	
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		System.out.println("before");
		method.invoke(userService, args);
		return null;
	}

}
