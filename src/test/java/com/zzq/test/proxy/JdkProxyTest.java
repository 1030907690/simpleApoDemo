package com.zzq.test.proxy;

import sun.misc.ProxyGenerator;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Proxy;

@SuppressWarnings("restriction")
public class JdkProxyTest {

	private static String DEFAULT_CLASS_NAME = "$Proxy0";

	public static void main(String[] args) {
		// 保存生成的代理类的字节码文件
		System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");
		UserService userService = new UserServiceImpl();
		 //第一个参数是指定代理类的类加载器（我们传入当前测试类的类加载器）
        //第二个参数是代理类需要实现的接口（我们传入被代理类实现的接口，这样生成的代理类和被代理类就实现了相同的接口）
        //第三个参数是invocation handler，用来处理方法的调用。这里传入我们自己实现的handler
		for (int i = 0; i < 2; i++) {


		UserService service = (UserService) Proxy.newProxyInstance(userService.getClass().getClassLoader(),
				new Class[] { UserService.class }, new JdkProxyInvocationHandler(userService));
		try {
			service.test();
		} catch (Throwable e) {
			e.printStackTrace();
		}}
		//createProxyClassFile(UserService.class);
	}

	private static void createProxyClassFile(Class<?> c) {
		byte[] data = ProxyGenerator.generateProxyClass(DEFAULT_CLASS_NAME, new Class[] { c });
		FileOutputStream out = null;

		try {
			String filePath = JdkProxyTest.class.getResource("/").getPath().substring(1) + DEFAULT_CLASS_NAME
					+ ".class";
			System.out.println(filePath);
			out = new FileOutputStream(filePath);
			out.write(data);
			out.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (out != null)
					out.close();
			} catch (IOException e) {
				// ignore
			}
		}
	}
}
