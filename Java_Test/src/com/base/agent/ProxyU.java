package com.base.agent;

public class ProxyU {

	
//	InvocationHandler
//	Proxy
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
//
		Proxyhanlder proxyhanlder = new Proxyhanlder();
		
		UserMethod method = (UserMethod) proxyhanlder.newProxyInstance(new UserMain());
		
//		method.addUser(111, "aaaaa");
		
		String ma = method.findUser(10);
		
		System.out.println("结果:"+ma);
//		UserMain userMain = new UserMain();
//		UserProxy proxy = new UserProxy(userMain);
//		proxy.addUser(0, "张四");
	}

}
