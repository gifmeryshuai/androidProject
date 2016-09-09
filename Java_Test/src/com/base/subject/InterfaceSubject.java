package com.base.subject;

/**
 * 这里我定义4个方法
 * 供代理类和委托类实现；
 * 保存功能的一致性
 */
public interface InterfaceSubject {

	//这四个方法要符合委托类使用的原则，能够实现委托类真正的逻辑；
	public void addSub(int id, String name);
	public void deleteSub(int id);
	public String findSub(int id);
	public void updateSub(int id , String name);
}
