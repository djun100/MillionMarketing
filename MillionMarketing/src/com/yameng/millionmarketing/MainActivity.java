package com.yameng.millionmarketing;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.android.pushservice.PushConstants;
import com.baidu.android.pushservice.PushManager;
import com.k.util.LocalPicToDrawable;
import com.k.util.Util_Weather;
import com.yameng.personal.Activity_Personal;
import com.yameng.personal.Activity_PersonalCenter;
import com.yameng.personal.Activity_RecentChatList;
import com.yameng.utils.ExitApplication;
import com.yameng.utils.MyApplication;
import com.yameng.variable.Variable;
import com.yamtz.millionmarketing.R;

public class MainActivity extends Activity implements OnClickListener{
	public static Activity context;
	Intent intent;
	public static boolean isForeground=false;
	private final String TAG = "MainActivity";
	//for receive customer msg from jpush server
	private MessageReceiver mMessageReceiver;
	public static final String MESSAGE_RECEIVED_ACTION = "MainActivity.MESSAGE_RECEIVED_ACTION";
	public static final String KEY_TITLE = "title";
	public static final String KEY_MESSAGE = "message";
	public static final String KEY_EXTRAS = "extras";
	
	ImageView iv_bg,iv_portrait;
	ImageButton ib_personal;
	ImageButton ib_login, ib_personalcenter, ib_notices, ib_sendcommodity,ib_mall,ib_setting,ib_messages;
	TextView tv_name; // 第一屏左上角的姓名
	boolean result = true;
	boolean flag = false; // 本主页按两次返回键退出应用
	FrameLayout fl_usercenter;// 用户名气泡层
	TextView tv_user;// 用户名气泡层用户名
	String[] results;// 天气结果数组
	TextView tv_weather, tv_temprature, tv_area;// 天气数据
	ImageView iv_weather;// 天气图片
	private ViewPager myViewPager;

	private MyPagerAdapter myAdapter;

	private LayoutInflater mInflater;

	private List<View> mListViews;

	private View layout1 = null;
	private View layout2 = null;
	private View layout3 = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context=this;
		setContentView(R.layout.activity_main);
		ExitApplication.getInstance().addActivity(this);
		// 异步更新天气
		WeatherTask weatherTask = new WeatherTask();
		weatherTask.execute();
		initView();
		Log.d(TAG,"MainActivity————startWork");
		PushManager.startWork(getApplicationContext(),
				PushConstants.LOGIN_TYPE_API_KEY, MyApplication.API_KEY);// 无baidu帐号登录,以apiKey随机获取一个id
		myAdapter = new MyPagerAdapter();
		myViewPager.setAdapter(myAdapter);
		// 初始化当前显示的view

		myViewPager.setCurrentItem(0);
		// 初始化第二个view的信息

		myViewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {

//				Log.d(TAG, "onPageSelected - " + arg0);

				// activity从1到2滑动，2被加载后掉用此方法

				View v = mListViews.get(arg0);

			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

//				Log.d(TAG, "onPageScrolled - " + arg0);

				// 从1到2滑动，在1滑动前调用

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

//				Log.d(TAG, "onPageScrollStateChanged - " + arg0);

				// 状态有三个0空闲，1是增在滑行中，2目标加载完毕

				/**
				 * 
				 * Indicates that the pager is in an idle, settled state. The
				 * current page
				 * 
				 * is fully in view and no animation is in progress.
				 */

				// public static final int SCROLL_STATE_IDLE = 0;

				/**
				 * 
				 * Indicates that the pager is currently being dragged by the
				 * user.
				 */

				// public static final int SCROLL_STATE_DRAGGING = 1;

				/**
				 * 
				 * Indicates that the pager is in the process of settling to a
				 * final position.
				 */

				// public static final int SCROLL_STATE_SETTLING = 2;

			}

		});
		registerMessageReceiver();  // used for receive msg
	}
	@Override
	protected void onResume() {
		isForeground = true;
		super.onResume();
	}


	@Override
	protected void onPause() {
		isForeground = false;
		super.onPause();
	}


	@Override
	protected void onDestroy() {
		unregisterReceiver(mMessageReceiver);//注销广播接收器
		super.onDestroy();
	}
	/**
	 * 初始化界面组件
	 */
	public void initView() {
		mInflater = getLayoutInflater();
		layout1 = mInflater.inflate(R.layout.main1, null);
		layout2 = mInflater.inflate(R.layout.main2, null);
		layout3 = mInflater.inflate(R.layout.main3, null);
		iv_portrait=(ImageView)layout1.findViewById(R.id.iv_portrait);
		ib_setting=(ImageButton)layout3.findViewById(R.id.ib_setting);
		myViewPager = (ViewPager) findViewById(R.id.viewpagerLayout);
		ib_messages=(ImageButton)layout1.findViewById(R.id.ib_messages);
		View view = findViewById(R.id.in_title);
		ib_login = (ImageButton) view.findViewById(R.id.btn_login);
		ib_sendcommodity = (ImageButton) layout3.findViewById(R.id.ib_sendcommodity);
		tv_name = (TextView) layout1.findViewById(R.id.tv_name);
		ib_personalcenter = (ImageButton) layout1.findViewById(R.id.ib_personalcenter);
		ib_notices = (ImageButton) layout1.findViewById(R.id.ib_notice);
		fl_usercenter = (FrameLayout) layout1.findViewById(R.id.fl_usercenter);
		tv_user = (TextView) layout1.findViewById(R.id.tv_user);
		tv_weather = (TextView) layout1.findViewById(R.id.tv_weather);
		tv_temprature = (TextView) layout1.findViewById(R.id.tv_temprature);
		iv_weather = (ImageView) layout1.findViewById(R.id.iv_weather);
		ib_mall=(ImageButton)layout2.findViewById(R.id.ib_mall);
		mListViews = new ArrayList<View>();
		mListViews.add(layout1);
		mListViews.add(layout2);
		mListViews.add(layout3);
		//设置头像
		String path= Environment.getExternalStorageDirectory().getPath()+"/yameng/portrait.png";
		File file=new File(path);
		//如果已经登录了
		if(Variable.person!=null){
			if(file.exists()){
				try {
					iv_portrait.setBackgroundDrawable(LocalPicToDrawable.getImageDrawable(path));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					System.out.println("之前未保存过头像");
				}
			}
		}
		setLoginoutBg();
		if (Variable.person != null) {
			tv_name.setText(Variable.person.getName());
			tv_name.setTextSize(20);
		}
		// 如果是已登录状态就显示用户名气泡层
		if (Variable.person != null) {
			tv_user.setText(Variable.person.getUser());
			fl_usercenter.setVisibility(View.VISIBLE);
		}
		setOnClickListener();
	}

	private void setOnClickListener() {
		// TODO Auto-generated method stub
		ib_setting.setOnClickListener(this);
		ib_messages.setOnClickListener(this);
		ib_notices.setOnClickListener(this);
		ib_mall.setOnClickListener(this);
		ib_personalcenter.setOnClickListener(this);
		ib_sendcommodity.setOnClickListener(this);
		ib_login.setOnClickListener(this);
	}
	/**
	 * 设置登录注销的背景图
	 */
	public void setLoginoutBg() {
		if (Variable.person!=null) {
			ib_login.setBackgroundResource(R.drawable.selector_main_title_logout);
		}else{
			ib_login.setBackgroundResource(R.drawable.selector_main_title_login);			
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK) {
			/*
			 * if ((System.currentTimeMillis() - Variable.exitTime) > 2000) {
			 * Toast.makeText(getApplicationContext(), "再按一次退出百万消费",
			 * Toast.LENGTH_SHORT).show(); Variable.exitTime =
			 * System.currentTimeMillis();
			 * 
			 * } else { System.exit(0); }
			 */
			//如果未登录就结束程序
			if(Variable.person==null){
				Log.d(TAG, "结束推送并退出应用");
				if (PushManager.isPushEnabled(context)) {
					PushManager.stopWork(context);
				}
				ExitApplication.getInstance().exit();
			}
			if (flag == true) {
				
//				ExitApplication.getInstance().exit();
				//模拟Home按键
				setApplicationBackGround();
				
			} else {
				Toast.makeText(getApplicationContext(), "再按一次退出百万消费",
						Toast.LENGTH_SHORT).show();
				flag = true;
			}
			return true;
		} else {
		}
		return super.onKeyDown(keyCode, event);
	}
	private void setApplicationBackGround() {
		// TODO Auto-generated method stub
		Log.d(TAG, "模拟进入后台");
		   Intent intent = new Intent(Intent.ACTION_MAIN);
	        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);// 注意
	        intent.addCategory(Intent.CATEGORY_HOME);
	        this.startActivity(intent);
	}

	private class MyPagerAdapter extends PagerAdapter {

		@Override
		public void destroyItem(View arg0, int arg1, Object arg2) {
//			Log.d(TAG, "destroyItem");
			((ViewPager) arg0).removeView(mListViews.get(arg1));
		}

		@Override
		public void finishUpdate(View arg0) {
//			Log.d(TAG, "finishUpdate");
		}

		@Override
		public int getCount() {
//			Log.d(TAG, "getCount");
			// return mListViews.size();
			return 3;
		}

		@Override
		public Object instantiateItem(View arg0, int arg1) {
//			Log.d(TAG, "instantiateItem");
			((ViewPager) arg0).addView(mListViews.get(arg1), 0);
			return mListViews.get(arg1);
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
//			Log.d(TAG, "isViewFromObject");
			return arg0 == (arg1);
		}

		@Override
		public void restoreState(Parcelable arg0, ClassLoader arg1) {
//			Log.d(TAG, "restoreState");
		}

		@Override
		public Parcelable saveState() {
//			Log.d(TAG, "saveState");
			return null;
		}

		@Override
		public void startUpdate(View arg0) {
//			Log.d(TAG, "startUpdate");
		}

	}

	class WeatherTask extends AsyncTask<String, Integer, String> {

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			Util_Weather util = new Util_Weather();
			results = util.getWeather("上海");

			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			Message msg = new Message();
			if (results[0].contains("晴")) {
				msg.what = 0;

			} else if (results[0].contains("雨")) {
				msg.what = 1;
			} else if (results[0].contains("雪")) {
				msg.what = 2;
			} else if (results[0].contains("风")) {
				msg.what = 3;
			} else if (results[0].contains("云")) {
				msg.what = 4;
			} else {
				msg.what = 5;
			}
			myHandler.sendMessage(msg);
			super.onPostExecute(result);
		}

	}

	Handler myHandler = new Handler() {
		// 2.重写消息处理函数
		public void handleMessage(Message msg) {
			tv_weather.setText(results[0]);
			tv_temprature.setText(results[1]);
			switch (msg.what) {
			// 判断发送的消息
			case 0:
				iv_weather
						.setBackgroundResource(R.drawable.main_weather_sunning);
				break;
			case 1:
				iv_weather.setBackgroundResource(R.drawable.main_weather_rains);
				break;
			case 2:
				iv_weather.setBackgroundResource(R.drawable.main_weather_snow);
				break;
			case 3:
				iv_weather.setBackgroundResource(R.drawable.main_weather_winds);
				break;
			case 4:
				iv_weather
						.setBackgroundResource(R.drawable.main_weather_clouds);
				break;
			case 5:
				iv_weather
						.setBackgroundResource(R.drawable.main_weather_sunning);
				break;
			}
			super.handleMessage(msg);
		}
	};
	public class MessageReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			if (MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {
              String messge = intent.getStringExtra(KEY_MESSAGE);
              String extras = intent.getStringExtra(KEY_EXTRAS);
              StringBuilder showMsg = new StringBuilder();
              showMsg.append(KEY_MESSAGE + " : " + messge + "\n");
//              setCostomMsg(showMsg.toString());
			}
		}
	}
	public void registerMessageReceiver() {
		mMessageReceiver = new MessageReceiver();
		IntentFilter filter = new IntentFilter();
		filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
		filter.addAction(MESSAGE_RECEIVED_ACTION);
		registerReceiver(mMessageReceiver, filter);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		// 如果未登录，跳转到登录页面
		if (Variable.person==null) {
//			if (Variable.user.equals("")) {
			intent = new Intent(MainActivity.this, Activity_Login.class);
			startActivity(intent);
			return;
		}
		// 将flag标志位设为false
		flag = false;
		switch(v.getId()){
		case R.id.ib_setting:
			startActivity(new Intent(context,Activity_Settings.class));
			break;
		case R.id.ib_messages:
			//最新消息页面
			startActivity(new Intent(context,Activity_RecentChatList.class));
			break;
		case R.id.ib_friends:
			break;
		case R.id.ib_activities:
			break;
		case R.id.ib_settings:
			// 跳转到设置页
			intent = new Intent(MainActivity.this, Activity_Settings.class);
			startActivity(intent);
			break;
		case R.id.btn_login:
			
			if (Variable.person==null) {
				// 跳转到登录页
				intent = new Intent(MainActivity.this, Activity_Login.class);
				startActivity(intent);
				finish();
			}else{
				//注销
				Variable.person=null;
				startActivity(new Intent(context,MainActivity.class));
//				JPushInterface.stopPush(getApplicationContext());
				if (PushManager.isPushEnabled(context)) {
					PushManager.stopWork(context);
				}
				Log.d(TAG, "PushManager.stopWork已经停止了推送");
				finish();
			}
			break;
		case R.id.ib_personalcenter:
			// 跳转到个人中心页
			intent = new Intent(MainActivity.this,
					Activity_PersonalCenter.class);
			startActivity(intent);
			break;
		case R.id.ib_notice:
			// 跳转到通知页面
			intent = new Intent(MainActivity.this, Activity_Notices.class);
			startActivity(intent);
			break;
		case R.id.ib_sendcommodity:
			// 跳转到发布商品和技能页面
			intent = new Intent(MainActivity.this,
					Activity_CommoditySend.class);
			startActivity(intent);
			break;
		case R.id.ib_mall:
			// 跳转到可见商品列表页面
			intent = new Intent(MainActivity.this,
					Activity_Mall.class);
			startActivity(intent);
			break;	
		}
	}
}
