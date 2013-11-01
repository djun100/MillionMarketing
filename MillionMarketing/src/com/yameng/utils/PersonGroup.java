package com.yameng.utils;

import java.util.ArrayList;

import android.view.View;

import com.yameng.bean.Person;
import com.yameng.variable.Variable;

public class PersonGroup {

	Person persons;

	/**
	 * @return 我的上线personL1（商品可见） 我的上线的上线personL2（商品可见）
	 *         personL2的上线personL3（商品可见） personL2的下线(我的上线的同级)personsL3（商品可见）
	 */
	public static ArrayList<Person> getCommodityPerson() {
		ArrayList<Person> persons = null;
		return persons;
	}

	/**
	 * @return 我的下线
	 */
	public static ArrayList<Person> getBranches(String user) {
		ArrayList<Person> persons = new Util_Interaction()
				.connecting_FriendsList(user);
		return persons;
	}

	/**
	 * @return 我的上线 PersonL1
	 */
	public static ArrayList<Person> getSourceUser(String user) {
		user = Variable.person.getSource_user();
		String param_source_user = Variable.address_connecting
				+ "action=source_user&user=" + user;
		System.out.println("请求上线：" + param_source_user);
		ArrayList<Person> persons = new Util_Interaction()
				.select(param_source_user);
		return persons;
	}

	/**
	 * @return 我的上线的上线
	 */
	public ArrayList<Person> getPersonL2() {
		ArrayList<Person> persons = PersonGroup
				.getSourceUser(MyApplication.personL1.get(0).getUser());
		return persons;
	}

	// 我的下线的下线
	public static ArrayList<Person> getPersonsL2s() {
		String param = "";// 查询下线的地址传递参数
		for (Person person : MyApplication.personL1s) {
			param += person.getUser() + ",";
		}
		if (!param.equals("")) {
			param = param.substring(0, param.length() - 1);
			param = Variable.address_connecting + "action=multiselect&users="
					+ param;
			System.out.println("查询我的下线的下线的地址参数param:" + param);
			MyApplication.personsL2s = new Util_Interaction().select(param);
		}
		return MyApplication.personsL2s;
	}

	// 我的上线的上线的上线
	public static ArrayList<Person> getPersonL3() {
		return MyApplication.personL3;
	}

	// 我的下线的下线的下线
	public static ArrayList<Person> getPersonsL3s() {
		// 我的下线的下线的下线
		String param = "";// 查询下线的地址传递参数
		for (Person person : MyApplication.personsL2s) {
			param += person.getUser() + ",";
		}
		param = param.substring(0, param.length() - 1);
		param = Variable.address_connecting + "action=multiselect&users="
				+ param;
		MyApplication.personsL3s = new Util_Interaction().select(param);
		return MyApplication.personsL3s;

	}

	// 我的上线的同级
	public static ArrayList<Person> getPersonsL3() {
		return MyApplication.personsL3;
	}

	// 我的所有好友
	public static ArrayList<Person> getAllFriends() {
		ArrayList<Person> persons = null;
//		persons.addAll(collection)
		return persons;
	}

	// L1级好友
	public void onClick_ib_friendslist_l1(View v) {

	}

	// L2级好友
	public void onClick_ib_friendslist_l2(View v) {

	}

	// L3级好友
	public void onClick_ib_friendslist_l3(View v) {

	}
}
