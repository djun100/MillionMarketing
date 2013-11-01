package com.yameng.bean;

import java.io.Serializable;

public class MessageInfo implements Serializable {

	private static final long serialVersionUID = 1L;
	String msg;
	boolean istoOrForm;//true自己发的
	String user_accept;
	String user_send;
	String message_content;
	String message_datetime;
	
	

	public String getUser_accept() {
		return user_accept;
	}

	public void setUser_accept(String user_accept) {
		this.user_accept = user_accept;
	}

	public String getUser_send() {
		return user_send;
	}

	public void setUser_send(String user_send) {
		this.user_send = user_send;
	}

	public String getMessage_content() {
		return message_content;
	}

	public void setMessage_content(String message_content) {
		this.message_content = message_content;
	}

	public String getMessage_datetime() {
		return message_datetime;
	}

	public void setMessage_datetime(String message_datetime) {
		this.message_datetime = message_datetime;
	}

	public boolean isIstoOrForm() {
		return istoOrForm;
	}

	public void setIstoOrForm(boolean istoOrForm) {
		this.istoOrForm = istoOrForm;
	}

	public MessageInfo() {
	}


	public MessageInfo(String msg, boolean istoOrForm) {
		super();
		this.msg = msg;
		this.istoOrForm = istoOrForm;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}
