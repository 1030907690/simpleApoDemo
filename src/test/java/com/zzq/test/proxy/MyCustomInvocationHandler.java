package com.zzq.test.proxy;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MyCustomInvocationHandler implements MyInvocationHandler {

	UserService userService;
	
	public MyCustomInvocationHandler(	UserService userService) {
		this.userService = userService;
	}
	
	public Object invoke(Object proxy,Method method,Object []args){
		try {
			System.out.println("before");
			method.invoke(userService, args);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
}
