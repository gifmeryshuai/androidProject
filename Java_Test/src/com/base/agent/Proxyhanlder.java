package com.base.agent;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class Proxyhanlder implements InvocationHandler {

	
	private Object tergetObject;
	
	public Object newProxyInstance(Object tergetObject){
		
		this.tergetObject = tergetObject;
		//ClassLoader loader, Class<?>[] interfaces, InvocationHandler h
		return Proxy.newProxyInstance(tergetObject.getClass().getClassLoader(), tergetObject.getClass().getInterfaces(), this);
	}
	
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		// TODO Auto-generated method stub
		Object rst = null;
		try {
			
//			if(method == null || args == null) {
//				
//				return null;
//			}
			System.out.println("start--->>>"+method.getName());
			
			for(int i = 0 ; i < args.length ; i++) {
				
				System.out.println("参数"+i+":"+args[i]);
			}
			
			rst = method.invoke(tergetObject, args);
			System.out.println("success-->>" + method.getName());  
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println("error-->>" + method.getName());  
            throw e;  
		}
		return rst;
	}

}
