package com.base.handler;

public class UerProxy implements UserMethod{

	@Override
	public void addUser(int id, String name) {
		// TODO Auto-generated method stub
		System.out.println("委托类：添加- "+id+" - "+name);
	}

	@Override
	public void deleteUser(int id) {
		// TODO Auto-generated method stub
		System.out.println("委托类：删除"+id);
	}

	@Override
	public String findUser(int id) {
		// TODO Auto-generated method stub
		System.out.println("委托类：查询"+id);
		return ""+id;
	}

	@Override
	public void updateUser(int id, String name) {
		// TODO Auto-generated method stub
		System.out.println("委托类：删除"+id+" - "+name);
	}

}
