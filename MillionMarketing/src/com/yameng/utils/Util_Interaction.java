package com.yameng.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.yameng.bean.MessageInfo;
import com.yameng.bean.Person;
import com.yameng.variable.Variable;

public class Util_Interaction {
	//以下声明的对象使用前需要实例化
	JSONArray jArray;
	JSONObject json_data;
	String[][] resultArray;
	Person person;
	MessageInfo message;
	String result = "";//页面返回的直接内容
	InputStream is = null;
	ArrayList<Person> list;
	ArrayList<Person> persons;
	ArrayList<MessageInfo> messages;
	//动作执行枚举类
	public static class Action{
		  static final int QUERYMESSAGE=1;
		  static final int MULTISELECT=2;
	}
	
	/**执行增删改操作
	 * @param adress 请求地址
	 * @return
	 */
	public String update(String adress) {
		try {
			System.out.println("请求地址："+adress);
			//从请求网址获取页面内容的输入流
			HttpClient httpclient = new DefaultHttpClient();
			System.out.println(1);
			HttpGet httpGet = new HttpGet(adress);
			System.out.println(2);
			HttpResponse response = httpclient.execute(httpGet);
			System.out.println(3);
			HttpEntity entity = response.getEntity();
			System.out.println(4);
			is = entity.getContent();
		} catch (Exception e) {
			System.out.println("Connecting Error");
		}
		// 将输入流转换为字符串
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "UTF-8"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
//				sb.append(line + "/n");
				sb.append(line );
			}
			is.close();
			result = sb.toString();
			System.out.println("输出result=" + result);
		} catch (Exception e) {
			System.out.println("Error converting to String");
		}

		return result;
	}
	/**执行查人员操作
	 * @param adress
	 * @return
	 */
	public ArrayList<Person> select(String adress){
		result= update(adress);
		if(result.equals("用户名或者密码错误")){
			return null;
			
		}
		// parse json data
		try {

			/* 将result进行JSONArray解析 */
			jArray = new JSONArray(result);
			//[{"telephone":1234}]的长度为1
			System.out.println("输出jArray.length()：" + jArray.length());
			persons=new ArrayList<Person>();
			for (int i = 0; i < jArray.length(); i++) {
				json_data = jArray.getJSONObject(i);
				person=new Person();
				person.setUser(json_data.getString("user"));
				person.setName(json_data.getString("name"));
				person.setTelephone(json_data.getString("telephone"));
				person.setSource_user(json_data.getString("source_user"));
				
				System.out.println("将Person对象添加到list数组中去");
				persons.add(person);
				System.out.println("Success");

			}
		}
		catch (JSONException e) {
			System.out.println("Error parsing json");
		}
		return persons;
	}
	/**执行查消息操作
	 * @param adress
	 * @return
	 */
	public ArrayList<MessageInfo> query(String adress){
		result= update(adress);
		// parse json data
		try {
			
			/* 将result进行JSONArray解析 */
			jArray = new JSONArray(result);
			//[{"telephone":1234}]的长度为1
			System.out.println("输出jArray.length()：" + jArray.length());
			messages=new ArrayList<MessageInfo>();
			for (int i = 0; i < jArray.length(); i++) {
				json_data = jArray.getJSONObject(i);
				message=new MessageInfo();
				message.setUser_accept(json_data.getString("user_accept"));
				message.setUser_send(json_data.getString("user_send"));
				message.setMessage_content(json_data.getString("message_content"));
				message.setMessage_datetime(json_data.getString("message_datetime"));
				System.out.println("将MessageInfo对象添加到Message数列中去");
				messages.add(message);
				System.out.println("Success");
				
			}
		}
		catch (JSONException e) {
			System.out.println("Error parsing json");
		}
		return messages;
	}


	public String register_connecting(String param) {
		String ss = null;
		InputStream is = null;

		try {
			HttpClient httpclient = new DefaultHttpClient();
//			HttpGet httpGet = new HttpGet(Variable.address_register + param);
			HttpGet httpGet = new HttpGet(Variable.address_connecting + param);
			System.out.println("请求地址："+Variable.address_connecting + param);
			HttpResponse response = httpclient.execute(httpGet);
			HttpEntity entity = response.getEntity();

			is = entity.getContent();

		} catch (Exception e) {

			System.out.println("Connecting Error");

		}

		// convert response to string

		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "UTF-8"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
//				sb.append(line + "/n");
				sb.append(line );
			}
			is.close();
			result = sb.toString();
			System.out.println("输出result=" + result);
			return result;
		} catch (Exception e) {
			System.out.println("Error converting to String");
		}

		System.out.println("connecting  ss=" + ss);
		return ss;

	}



	public ArrayList<Person> receiveInvite(String param) {

		String result = "";
		String ss = null;

		InputStream is = null;

		// http post

		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpGet httpGet = new HttpGet(Variable.address_receiveinvite + param);
			System.out.println("请求的地址："+Variable.address_receiveinvite + param);
			HttpResponse response = httpclient.execute(httpGet);
			HttpEntity entity = response.getEntity();

			is = entity.getContent();

		} catch (Exception e) {

			System.out.println("Connecting Error");

		}


		// convert response to string

		try {

			// BufferedReader reader = new BufferedReader(new InputStreamReader(
			// is, "iso-8859-1"), 8);
			// BufferedReader reader = new BufferedReader(new
			// InputStreamReader(is, "GB2312"), 8);
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "UTF-8"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "/n");
			}
			is.close();
			result = sb.toString();
			//如果结果为空说明没有人邀请我，返回空list
			if(result.equals("")){
				return list;
			}

			System.out.println("get = " + result);

		} catch (Exception e) {
			System.out.println("Error converting to String");
		}
		// parse json data
		try {

			/* 将result进行JSONArray解析 */
			jArray = new JSONArray(result);
			//[{"telephone":1234}]的长度为1
			System.out.println("输出jArray.length()：" + jArray.length());
			list=new ArrayList<Person>();
			for (int i = 0; i < jArray.length(); i++) {
				json_data = jArray.getJSONObject(i);
				person=new Person();
				person.setUser(json_data.getString("user"));
				System.out.println("将Person对象添加到list数组中去");
				list.add(person);
				System.out.println("Success");

			}
		}
		catch (JSONException e) {
			System.out.println("Error parsing json");
		}
		System.out.println("ss=" + ss);
		//{"telephone":1234}
		return list;
	}


	/**
	 * 服务器返回数据示例：[{"name":哈哈,"user":我,"telephone":123456},
     * {"name":亚盟小秘书,"user":administrator,"telephone":10000}]
	 * @param param 传递进来的数据格式为：admin
	 * @return ArrayList<Person>
	 */
	public ArrayList<Person> connecting_FriendsList(String param) {

		String result = "";//从服务器端返回的结果字符串
		String ss = null;
		InputStream is = null;
		Person person;
		list=new ArrayList<Person>();
		// http post
		try {
			HttpClient httpclient = new DefaultHttpClient();
//			HttpGet httpGet = new HttpGet(Variable.address_friendslist +"source_user="+ param);
			HttpGet httpGet = new HttpGet(Variable.address_connecting +"source_user="+ param);
//			HttpGet httpGet = new HttpGet(Variable.address_connecting + param);
			System.out.println("connecting_FriendsList请求的地址："+Variable.address_connecting+"source_user=" + param);
			HttpResponse response = httpclient.execute(httpGet);
			HttpEntity entity = response.getEntity();
			is = entity.getContent();
		} catch (Exception e) {
			System.out.println("Connecting Error");
		}
		// convert response to string
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "UTF-8"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "/n");
			}
			is.close();
			result = sb.toString();
			if(result.equals("该用户没有好友")){
				return list;
			}
			// result = sb.toString().substring(3);
			System.out.println("get = " + result);

		} catch (Exception e) {
			System.out.println("Error converting to String");
		}
		// parse json data
		try {

			System.out.println("创建jsonarray对象");
			jArray = new JSONArray(result);
			System.out.println("输出 jArray.length()=" + jArray.length());
			//n行3列二维数组
			resultArray = new String[jArray.length()][3];
			System.out.println("resultArray长度：" + resultArray.length);
			//将JsonArray对象逐个解析到Person实体
			for (int i = 0; i < jArray.length(); i++) {
				//获得第i条json格式数据
				json_data = jArray.getJSONObject(i);
				System.out.println("result " + json_data.toString());
				System.out.println("json_data.get user:"
						+ json_data.get("user"));
				resultArray[i][0] = json_data.get("user").toString();
				resultArray[i][1] = json_data.get("name").toString();
				//Integer对象转换为String不能使用强制类型转换
				resultArray[i][2] =  json_data.get("telephone").toString();
				person=new Person();
				person.setUser(resultArray[i][0]);
				person.setName(resultArray[i][1]);
				person.setTelephone(resultArray[i][2]);
				list.add(person);
				if (i == 0) {
					ss = json_data.toString();
				}
				else {
					ss += json_data.toString();
				}

			}

		}

		catch (JSONException e) {

			System.out.println("Error parsing json");

		}
		System.out.println("connecting杩斿洖ss=" + ss);
		// return ss;
		return list;
	}

	/**
	 * @param param
	 *            地址后缀参数 访问地址："http://localhost:8080/testweb/login?"
	 *            http://localhost:8080/testweb/login?user=admin&password=admin
	 * @return
	 */
	public String login_connecting(String param) {

		String result = "";
		String ss = null;
		InputStream is = null;

		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpGet httpGet = new HttpGet(Variable.address_connecting + param);
			System.out.println("请求的地址："+Variable.address_connecting + param);
			HttpResponse response = httpclient.execute(httpGet);
			HttpEntity entity = response.getEntity();

			is = entity.getContent();

		} catch (Exception e) {

			System.out.println("Connecting Error");

		}

		// convert response to string

		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "UTF-8"), 8);

			StringBuilder sb = new StringBuilder();

			String line = null;

			while ((line = reader.readLine()) != null) {

//				sb.append(line + "/n");
				sb.append(line );

			}

			is.close();

			result = sb.toString();
			//输出:[{"telephone":1234}]
			System.out.println("输出result=" + result);
			if(result.equals("用户名或者密码错误")){
				return result;
			}

		} catch (Exception e) {

			System.out.println("Error converting to String");

		}
		// parse json data

		try {

			/* 将result进行JSONArray解析 */
			jArray = new JSONArray(result);
			//[{"telephone":1234}]的长度为1
			System.out.println("输出jArray.length()：" + jArray.length());
			for (int i = 0; i < jArray.length(); i++) {

				json_data = jArray.getJSONObject(i);

				System.out.println("Success");
				//输出{"telephone":1234}
				System.out.println("result:json_data.toString() :" + json_data.toString());
				if (i == 0) {
					System.out.println("json_data.getuser:" + json_data.get("user"));
					ss = json_data.toString();
				}
				else {
					ss += json_data.toString();
				}
			}
		}
		catch (JSONException e) {
			System.out.println("Error parsing json");
		}
		System.out.println("ss=" + ss);
		//{"telephone":1234}
		return ss;
	}

	/**
	 * @param param 手机号 
	 * eg:123214
	 * @return
	 * 服务器端返回“该用户未注册”，则函数返回null；</br>
	 * 服务器端返回json数据，则函数返回解析好的一个Person对象
	 */
	public Person searchFriends(String param) {
		String result = "";
		String ss = null;
		InputStream is = null;
		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpGet httpGet = new HttpGet(Variable.address_searchfriends + param);
			HttpResponse response = httpclient.execute(httpGet);
			HttpEntity entity = response.getEntity();

			is = entity.getContent();

		} catch (Exception e) {

			System.out.println("Connecting Error");

		}

		// convert response to string

		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "UTF-8"), 8);

			StringBuilder sb = new StringBuilder();

			String line = null;

			while ((line = reader.readLine()) != null) {

//				sb.append(line + "/n");
				sb.append(line );

			}

			is.close();

			result = sb.toString();
			if(result.equals("该用户未注册")){
				return null;
			}
			System.out.println("输出result=" + result);
		} catch (Exception e) {

			System.out.println("Error converting to String");

		}
		// parse json data

		try {

			/* 将result进行JSONArray解析 */
			jArray = new JSONArray(result);
			System.out.println("输出jArray.length()：" + jArray.length());
			for (int i = 0; i < jArray.length(); i++) {

				json_data = jArray.getJSONObject(i);
				//未将Person类初始化会报空指针错误
				person=new Person();
				person.setName(json_data.getString("name"));
				person.setUser(json_data.getString("user"));
				person.setTelephone(json_data.getString("telephone"));
			}
		}
		catch (JSONException e) {
			System.out.println("Error parsing json");
		}
		return person;
	}
	//发送邀请
	public String sendInvite(String param){
		String result = "";
		String ss = null;
		InputStream is = null;

		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpGet httpGet = new HttpGet(Variable.address_sendinvite + param);
			System.out.println("请求的地址："+Variable.address_sendinvite + param);
			HttpResponse response = httpclient.execute(httpGet);
			HttpEntity entity = response.getEntity();
			is = entity.getContent();
		} catch (Exception e) {
			System.out.println("Connecting Error");
		}
		// convert response to string
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "UTF-8"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line );
			}
			is.close();
			result = sb.toString();
		} catch (Exception e) {
			System.out.println("Error converting to String");
		}
		return result;
	}
}