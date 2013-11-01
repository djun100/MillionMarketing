package com.yameng.variable;

import com.yameng.bean.Commodity;
import com.yameng.bean.Person;

public class Variable {
	static boolean logined = false;
	static boolean personal_player = false;
	public static Person person;//当前登录用户
	public static String user="";
	public static String telephone="";
	public static String name="";
	public static String email="";
	public static String address="";
	public static String source_user="";//我的上线
	public static String push_userid="";//百度云推送生成的userid
	
	public static boolean portrait_update=false;
	public static Person personChat;//正在和你聊天的人
	public static String personChatUser;//正在和你聊天的人的user
	public static Commodity commodity;//当前商品
	// 必须为pc的ip，而不是localhost或者127之类的。
//	public static String accessaddress = "http://djun100.big02.tomcats.pw/testweb/";
//	public static String accessaddress = "http://116.226.235.141:8080/testweb/";
//	public static String accessaddress = "http://192.168.1.111:8080/testweb/";
//	public static String accessaddress = "http://24kiss.duapp.com/";
	public static String accessaddress = "http://0.24kiss.duapp.com/";
	public static String accessaddressssssss = "http://0.24kiss.duapp.com/";
	public static String address_connecting = accessaddress + "connecting?";
	public static String address_register = accessaddress + "register?";
//	public static String address_friendslist = accessaddress + "friendslist?source_user=";
	public static String address_friendslist = accessaddress + "friendslist?";
	public static String address_searchfriends = accessaddress + "searchfriends?telephone=";
	public static String address_sendinvite = accessaddress + "sendinvite?";
	public static String address_message_check = accessaddress + "message_check?";
	public static String address_receiveinvite = accessaddress + "receiveinvite?user=";
	public static String address_commodity = accessaddress + "commodity";
	public static long exitTime = 0;
	 class test{
		 void te(){
			 String aa="dfs";
		}
	}
}
