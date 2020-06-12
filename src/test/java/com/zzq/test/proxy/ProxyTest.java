package com.zzq.test.proxy;
import java.lang.reflect.Method; 
public class ProxyTest implements com.zzq.test.proxy.UserService{
MyInvocationHandler h;
public ProxyTest(MyInvocationHandler h){
this.h = h;
}
public void test() throws Throwable {
 Method md =com.zzq.test.proxy.UserService.class.getMethod("test",new Class[]{});
 this.h.invoke(this,md,null);
}

}