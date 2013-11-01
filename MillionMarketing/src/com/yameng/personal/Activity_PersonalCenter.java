package com.yameng.personal;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yameng.millionmarketing.Activity_Commodities;
import com.yameng.millionmarketing.Activity_SearchFriends;
import com.yameng.millionmarketing.MainActivity;
import com.yameng.utils.ExitApplication;
import com.yameng.variable.Variable;
import com.yamtz.millionmarketing.R;

/**
 * 个人中心
 * 
 * @author 24kSocrates</br>
 * 
 */
public class Activity_PersonalCenter extends Activity {
	Context context=this;
	private ImageButton ib_left, ib_personalcenter;
	private ViewPager myViewPager;
			Intent intent;
	private MyPagerAdapter myAdapter;

	private LayoutInflater mInflater;

	private List<View> mListViews;

	private View layout1 = null;
	TextView tv_nickname;
	private View layout2 = null;
	View in_title;//include——view
	// 左侧界面组件
	ImageButton ib_friends, ib_activities, ib_messages, ib_settings,ib_invitefriends;
	//右侧界面组件
	ImageButton ib_commodities;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_personal_center);
		ExitApplication.getInstance().addActivity(this); 
		// 初始化界面组件
		init();
		myAdapter = new MyPagerAdapter();
		myViewPager.setAdapter(myAdapter);


		// 初始化当前显示的view

		myViewPager.setCurrentItem(0);
		// 初始化第二个view的信息

		myViewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {

				Log.d("k", "onPageSelected - " + arg0);

				// activity从1到2滑动，2被加载后掉用此方法

				View v = mListViews.get(arg0);

			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

				Log.d("k", "onPageScrolled - " + arg0);

				// 从1到2滑动，在1滑动前调用

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

				Log.d("k", "onPageScrollStateChanged - " + arg0);

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

	}
	//按钮响应————标题left
	public void onClick_Left(View v){
		intent =new Intent(Activity_PersonalCenter.this,MainActivity.class);
		startActivity(intent);
	}
	//按钮响应————标题main
	public void onClick_Main(View v){
		intent =new Intent(Activity_PersonalCenter.this,MainActivity.class);
		startActivity(intent);
	}
	private void init() {
		// TODO Auto-generated method stub
		View title=(View)findViewById(R.id.in_title);
		TextView pageTitle=(TextView)title.findViewById(R.id.tv_pagetitle);
		pageTitle.setText("个人中心");
		mListViews = new ArrayList<View>();
		myViewPager = (ViewPager) findViewById(R.id.viewpagerLayout);
		mInflater = getLayoutInflater();

		layout1 = mInflater.inflate(R.layout.personalcenter_1, null);

		layout2 = mInflater.inflate(R.layout.personalcenter_2, null);

		mListViews.add(layout1);

		mListViews.add(layout2);
		 in_title=(View)findViewById(R.id.in_title);
		 tv_nickname=(TextView)layout1.findViewById(R.id.tv_nickname);
		ib_friends = (ImageButton)layout1.findViewById(R.id.ib_friends);
		ib_activities = (ImageButton) layout1.findViewById(R.id.ib_activities);
		ib_messages = (ImageButton) layout1.findViewById(R.id.ib_messages);
		ib_settings = (ImageButton) layout1.findViewById(R.id.ib_settings);
		ib_invitefriends=(ImageButton)layout2.findViewById(R.id.ib_invitefriends);
		ib_commodities=(ImageButton)layout2.findViewById(R.id.ib_commodities);
		tv_nickname.setText(Variable.person.getName()+"\nCircle");
		

		ib_friends.setOnClickListener(new ClickListener());
		ib_activities.setOnClickListener(new ClickListener());
		ib_messages.setOnClickListener(new ClickListener());
		ib_settings.setOnClickListener(new ClickListener());
		ib_invitefriends.setOnClickListener(new ClickListener());
		ib_commodities.setOnClickListener(new ClickListener());
	}

	public class ClickListener implements OnClickListener {
		Intent intent;

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.ib_friends:
				//跳转到好友列表页
				intent = new Intent(Activity_PersonalCenter.this,
						Activity_FriendsList.class);
				startActivity(intent);
				break;
			case R.id.ib_activities:
				break;
			case R.id.ib_messages:
				break;
			case R.id.ib_settings:
				//跳转到设置个人资料页
				intent = new Intent(Activity_PersonalCenter.this,
						Activity_Personal_Info.class);
				startActivity(intent);
				break;
			case R.id.ib_invitefriends:
				//跳转到搜索好友页
				intent = new Intent(Activity_PersonalCenter.this,
						Activity_SearchFriends.class);
				startActivity(intent);
				break;
			case R.id.ib_commodities:
				intent =new Intent(context,Activity_Commodities.class);
				startActivity(intent);
				break;
			}
		}

	}

	private class MyPagerAdapter extends PagerAdapter {

		@Override
		public void destroyItem(View arg0, int arg1, Object arg2) {
			Log.d("k", "destroyItem");
			((ViewPager) arg0).removeView(mListViews.get(arg1));
		}

		@Override
		public void finishUpdate(View arg0) {
			Log.d("k", "finishUpdate");
		}

		@Override
		public int getCount() {
			Log.d("k", "getCount");
			// return mListViews.size();
			return 2;
		}

		@Override
		public Object instantiateItem(View arg0, int arg1) {
			Log.d("k", "instantiateItem");
			((ViewPager) arg0).addView(mListViews.get(arg1), 0);
			return mListViews.get(arg1);
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			Log.d("k", "isViewFromObject");
			return arg0 == (arg1);
		}

		@Override
		public void restoreState(Parcelable arg0, ClassLoader arg1) {
			Log.d("k", "restoreState");
		}

		@Override
		public Parcelable saveState() {
			Log.d("k", "saveState");
			return null;
		}

		@Override
		public void startUpdate(View arg0) {
			Log.d("k", "startUpdate");
		}

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if(keyCode == KeyEvent.KEYCODE_BACK){
			startActivity(new Intent(this,MainActivity.class));
		}
		return super.onKeyDown(keyCode, event);
	}

}
