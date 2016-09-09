package com.base.handler;

public interface UserMethod {

	public void addUser(int id, String name);
	public void deleteUser(int id);
	public String findUser(int id);
	public void updateUser(int id, String name);
}
