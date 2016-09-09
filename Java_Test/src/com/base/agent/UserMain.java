package com.base.agent;

public class UserMain implements UserMethod{

	@Override
	public void addUser(int id, String name) {
		// TODO Auto-generated method stub
		System.out.println("委托方-addUser:"+id+"*"+name);
	}

	@Override
	public void deleteUser(int id) {
		// TODO Auto-generated method stub
		System.out.println("委托方-deleteUser:"+id);
	}

	@Override
	public String findUser(int id) {
		// TODO Auto-generated method stub
		return "委托方-findUser:"+id;
	}

	@Override
	public void updateUser(int id, String name) {
		// TODO Auto-generated method stub
		System.out.println("委托方-updateUser:"+id+"*"+name);
	}
	
	public void test() {
		
		System.out.println("测试");
	}

}
