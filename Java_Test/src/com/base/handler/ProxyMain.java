package com.base.handler;

public class ProxyMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		ProxyHandler handler = new ProxyHandler();
		
		UserMethod method = (UserMethod) handler.newProxyInstance(new UerProxy());
		
		method.addUser(1, "Huang");
	}

}
