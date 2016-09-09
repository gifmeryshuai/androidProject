package com.base.subject;

/**
 * 此类是委托类，它实现了
 * 特定的接口
 * 完成特定的功能
 */
public class RealSubject implements InterfaceSubject {

	@Override
	public void addSub(int id, String name) {
		// TODO Auto-generated method stub

		System.out.println("RealSubject-增加:"+id+"");
	}

	@Override
	public void deleteSub(int id) {
		// TODO Auto-generated method stub

		System.out.println("RealSubject-删除:"+id+"");
	}

	@Override
	public String findSub(int id) {
		// TODO Auto-generated method stub
		System.out.println("RealSubject-查询:"+id+"");
		return "RealSubject-查询:"+id;
	}

	@Override
	public void updateSub(int id, String name) {
		// TODO Auto-generated method stub
		System.out.println("RealSubject-更改:"+id+" - "+name);
	}

}
