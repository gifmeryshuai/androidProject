package com.base.subject;

public class SubMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		RealSubject subject = new RealSubject();
		
		ProxySubject proxySubject = new ProxySubject(subject);
		
//		proxySubject.addSub(1, "Huang");
		String result = proxySubject.findSub(0);
		System.out.println("结果："+result);
	}

}
