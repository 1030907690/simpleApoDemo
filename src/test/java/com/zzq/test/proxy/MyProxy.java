package com.zzq.test.proxy;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;


import javax.tools.JavaCompiler.CompilationTask;


public class MyProxy {

	private static final String path = MyProxy.class.getResource("/").getPath().substring(1).replace("/target/test-classes/","") +"/src/test/java/com/zzq/test/proxy/ProxyTest.java";

    //private static final String path = "/Volumes/新加卷/workspace/git/CeShi2/src/test/java/com/zzq/test/proxy/ProxyTest.java";


    private static final String rt = "\r\n";



    /**
     * 返回代理实例的方法
     **/
    public static Object newProxyInstance(ClassLoader loader, Class<?> claz, MyInvocationHandler h) {
        requiredValidate(loader, claz, h);
        //拼接代理类
        String proxyClssStr = getProxyTest(claz);

        //输出到文件
        //G:/win_workspace/git/CeShi2/src/test/java/com/zzq/test/proxy
        outFile(proxyClssStr);

        //编译文件
        try {
            complierJava();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //加載到jvm内存
        return loaderClassToJvm(h);

    }

    /***
     * 必须的验证
     * */
    private static void requiredValidate(ClassLoader loader, Class<?> claz, MyInvocationHandler h) {
        if (null == loader || null == claz || null == h) {
			throw new RuntimeException("参数异常");
        }
    }


    private static Object loaderClassToJvm(MyInvocationHandler h) {
        ClassLoaderTest loaderTest = new ClassLoaderTest(path.replace("/ProxyTest.java", ""));
        try {
            Class<?> clz = loaderTest.findClass("ProxyTest");

            Constructor<?> construct = clz.getConstructor(MyInvocationHandler.class);
            //把h参数传入代理类的构造方法  返回代理类的实例
            return construct.newInstance(h);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    private static void complierJava() throws IOException {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        StandardJavaFileManager standardJavaFileManager = compiler.getStandardFileManager(null, null, null);
        Iterable<? extends JavaFileManager> javaFileObjects = (Iterable<? extends JavaFileManager>) standardJavaFileManager.getJavaFileObjects(path);
        CompilationTask task = compiler.getTask(null, standardJavaFileManager, null, null, null, (Iterable<? extends JavaFileObject>) javaFileObjects);
        task.call();
        try {
            standardJavaFileManager.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
		 
   		
		
	/*	JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<JavaFileObject>();
		StandardJavaFileManager fileManager = compiler.getStandardFileManager(
		        diagnostics, null, null);
		Location oLocation = StandardLocation.CLASS_OUTPUT;
		fileManager.setLocation(oLocation,
		        Arrays.asList(new File[] { new File(path.replace("/ProxyTest.java", "")) }));
		Iterable<? extends JavaFileObject> compilationUnits = fileManager
		        .getJavaFileObjects(path);
		JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager,
		        diagnostics, null, null, compilationUnits);
		boolean result = task.call();
		fileManager.close();
*/
    }


    private static void outFile(String proxyClssStr) {
        File file = new File(path);
        try {
            FileWriter fw = new FileWriter(file);
            fw.write(proxyClssStr);
            fw.flush();
            fw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /**
     * 字符串拼接代理类
     */
    private static String getProxyTest(Class<?> claz) {
        Method[] methods = claz.getMethods();
        StringBuffer sb = new StringBuffer();
        sb.append("package com.zzq.test.proxy;" + rt);
        sb.append("import java.lang.reflect.Method; " + rt);
        sb.append("public class ProxyTest implements " + claz.getName() + "{");
        sb.append(rt + "MyInvocationHandler h;" + rt);
        sb.append("public ProxyTest(MyInvocationHandler h){" + rt);
        sb.append("this.h = h;" + rt);
        sb.append("}" + rt);
        sb.append(getMethodString(methods, claz));
        sb.append(rt + "}");
        return sb.toString();
    }


    /**
     * 循环拼接处接口的方法(这里是UserService接口)
     */
    private static Object getMethodString(Method[] methods, Class<?> claz) {
        String proxyMe = "";
        for (Method method : methods) {
            proxyMe += "public void " + method.getName();
            proxyMe += "() throws Throwable {" + rt + " Method md =";
            proxyMe += claz.getName() + ".class.getMethod(\"" + method.getName() + "\",new Class[]{});" + rt;
            proxyMe += " this.h.invoke(this,md,null);" + rt + "}" + rt;
        }
        return proxyMe;
    }
	
	
	/*
	public static void createProxyClassFile(){
		
		byte [] generateProxyClass = ProxyGenerator.generateProxyClass(
"ProxyTest", new Class<?>[]{UserService.class});
		try {
			FileOutputStream os = new FileOutputStream("ProxyTest.class");
			os.write(generateProxyClass);
			os.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
*/

}
