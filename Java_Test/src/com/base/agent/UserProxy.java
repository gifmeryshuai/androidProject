package com.base.agent;

public class UserProxy implements UserMethod{

	
	private UserMain userMain;
	
	public UserProxy(UserMain userMain) {
		this.userMain = userMain;
	}

	@Override
	public void addUser(int id, String name) {
		// TODO Auto-generated method stub
		System.out.println("代理-addUser:"+id+"*"+name);
		userMain.addUser(id, name+"--代理方过滤信息之后交给委托方");
	}

	@Override
	public void deleteUser(int id) {
		// TODO Auto-generated method stub
		System.out.println("--代理方过滤信息之后交给委托方+10"+(id+10));
		userMain.deleteUser(id);
	}

	@Override
	public String findUser(int id) {
		// TODO Auto-generated method stub
		System.out.println("--代理方过滤信息之后交给委托方+10"+(id+10));
		return userMain.findUser(id);
	}

	@Override
	public void updateUser(int id, String name) {
		// TODO Auto-generated method stub
		System.out.println("代理-updateUser:"+id+"*"+name);
		userMain.updateUser(id, name+"--代理方过滤信息之后交给委托方");
	}


}
