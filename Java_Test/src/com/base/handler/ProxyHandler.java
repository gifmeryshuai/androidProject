package com.base.handler;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.logging.Logger;

/**
 * 代理类，实现InvocationHandler接口，实现invoke方法
 * 由invok方法监控委托类方法的执行 
 */
public class ProxyHandler implements InvocationHandler {

	//目标对象，即是委托类
	private Object targetObject;
	
	public Object newProxyInstance(Object object) {
		
		try {
			
			this.targetObject = object;
			/**
			 * 第一个参数是委托类的类装载器
			 * 第二个参数是，该委托类的实现的接口方法；也就是被拦截的方法；
			 * 第三个参数是当前代理类；意思是当前被拦截的方法在被拦截时需要执行的是哪个InvocationHandler的invoke
			 * 最后返回一个对象，这个对象实际上就是接口类对象；
			 */
			return Proxy.newProxyInstance(object.getClass().getClassLoader(), object.getClass().getInterfaces(), this);
			
		} catch (Exception e) {
			
			System.out.println(""+e.getMessage());
			
		}
		
		return null;
	}
	/**
	 * InvocationHandler接口的方法，proxy表示代理，method表示原对象被调用的方法，args表示方法的参数
	 * */  
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		// TODO Auto-generated method stub
		Object ret=null;  
		try {
			if(method == null) 
				return null;
			
			System.out.println("方法:"+method.getName());
			/**
			 * 打印该方法的参数
			 */
			if(args != null && args.length>0) {
				
				for(int i = 0 ; i < args.length ;i++) {
					System.out.println("参数:"+args[i]);
				}
			}
			//监控执行前的操作
			System.out.println("执行前");
			ret =  method.invoke(this.targetObject, args);
			//监控执行后的操作
			System.out.println("执行后");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			//监控失败后的操作
			System.out.println(method.getName()+"-执行失败");
		}
		
		return ret;
	}

}
