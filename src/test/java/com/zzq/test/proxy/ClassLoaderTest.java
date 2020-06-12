package com.zzq.test.proxy;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;

/**
 * 自定义的类加载器
 */

public class ClassLoaderTest extends ClassLoader {
	
	private File dir;
	
	public ClassLoaderTest(String path) {
		dir = new File(path);
	}
	
	@Override
	protected Class<?> findClass(String name) throws ClassNotFoundException {

		if(dir != null){
			File classFile = new File(dir,name+".class");
			if(classFile.exists()){
				try {
					FileInputStream fis = new FileInputStream(classFile);
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					byte [] buffer = new byte[1024];
					
					int len;
					
					while ((len = fis.read(buffer)) != -1 ) {
						baos.write(buffer,0,len);
					}
					//把字节流的内容加载到内存
					return defineClass("com.zzq.test.proxy." +name,baos.toByteArray()
							,0,baos.size());
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
					
		}
		return super.findClass(name);
	}
	 
}