package com.tang.bean;

public class UserInfo {
	private String username;
	private String password;
	private int age;
	private String sex;
	private int phone;
	
	public UserInfo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UserInfo(String username, String password, int age, String sex,
			int phone ) {
		super();
		this.username = username;
		this.password = password;
		this.age = age;
		this.sex = sex;
		this.phone = phone;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public int getPhone() {
		return phone;
	}

	public void setPhone(int phone) {
		this.phone = phone;
	}
	

}
