package com.yameng.bean;

import java.io.Serializable;
//方便通过其他类传递person对象给Variable中，而不用把person的每个属性都传递给Variable一遍
public class Person implements Serializable{
	private static final long serialVersionUID = 1L;  
	String user="";
	String name="";
	String telephone="";
	String email="";
	String address="";
	String source_user="";//他的上线
	String push_userid="";//百度推生成的userid
	public String getPush_userid() {
		return push_userid;
	}
	public void setPush_userid(String push_userid) {
		this.push_userid = push_userid;
	}
	@Override
	public String toString() {
		return "Person [address=" + address + "]";
	}
	public String getSource_user() {
		return source_user;
	}
	public void setSource_user(String source_user) {
		this.source_user = source_user;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
}
