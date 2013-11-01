package com.yameng.utils;

import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.app.Application;
//activity中加入
//ExitApplication.getInstance().addActivity(this);
//退出时调用
//ExitApplication.getInstance().exit();

public class ExitApplication extends Application {

	public static List<Activity> activityList = new LinkedList<Activity>();

	private static ExitApplication instance;

	private ExitApplication() {
	}

	// 单例模式中获取唯一的ExitApplication 实例
	public static ExitApplication getInstance() {
		if (null == instance) {
			instance = new ExitApplication();
		}
		return instance;

	}

	// 添加Activity 到容器中
	public void addActivity(Activity activity) {
		activityList.add(activity);
	}

	// 遍历所有Activity 并finish

	public void exit() {

		for (Activity activity : activityList) {
			activity.finish();
		}

		System.exit(0);

	}
}