package com.yameng.personal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.SimpleAdapter;
import android.widget.TabHost;
import android.widget.TabWidget;

import com.k.util.CustomProgressDialog;
import com.yameng.bean.Person;
import com.yameng.millionmarketing.Activity_FriendsCenter;
import com.yameng.millionmarketing.Activity_Login;
import com.yameng.millionmarketing.MainActivity;
import com.yameng.utils.PersonGroup;
import com.yameng.utils.Util_Interaction;
import com.yameng.variable.Variable;
import com.yamtz.millionmarketing.R;
import com.yamtz.millionmarketing.R.drawable;
import com.yameng.utils.MyApplication;


public class Activity_FriendsList extends FragmentActivity {
	Activity context =this;
	GridView gv_friends;
	GridView gv_friendsl2;
	GridView gv_friendsl3;
	View in_title;
	Intent intent;
	TabHost tabhost;
	CustomProgressDialog progressDialog = null;
	ImageButton ib_main,ib_left;// 返回到个人中心
	public static ArrayList<Person> persons;// 设为静态变量以供fragment调用
	List<Map<String, Object>> list2;//L2好友的填充数据
	List<Map<String, Object>> list ;
	List<Map<String, Object>> list3;

	String param = "";// 查询下线的地址传递参数
	String param3 = "";// 查询L3下线的地址传递参数
	String param_source_user = "";// 查询上线的地址传递参数
	// 默认pesonsL2中的source_user没有与persons的user相匹配，
	// 即L2中没有L1的下线，该person为未发展状态
	boolean flag = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_friendlist);
		/*
		 * //加载fragment
		 * 
		 * FragmentTransaction ft =
		 * getSupportFragmentManager().beginTransaction();
		 * FriendsList_FragmentL1 l1 = new FriendsList_FragmentL1();
		 * ft.add(R.id.fragment, l1); ft.commit();
		 */
		// 初始化
		init();
		Task_MyFriendsList task_myFriendsList = new Task_MyFriendsList(this);
		task_myFriendsList.execute();

	}

	private void init() {
		// TODO Auto-generated method stub
		init_tabhost();
		init_gv_friends();
		init_gv_friendsl2();
		init_gv_friendsl3();
		init_title();

	}

	public void init_tabhost() {
		tabhost = (TabHost) findViewById(R.id.tabhost);
		tabhost.setup();
		TabWidget tabWidget = tabhost.getTabWidget();

		tabhost.addTab(tabhost
				.newTabSpec("tab1")
				.setIndicator(
						"",
						getResources().getDrawable(
								R.drawable.selector_friendslist_ib_l1))
				.setContent(R.id.gv_friends));
		tabhost.getTabWidget()
				.getChildAt(0)
				.setBackgroundResource(R.drawable.selector_friendslist_ib_l1_bg);
		// tabhost.getTabWidget().getChildAt(0).;

		tabhost.addTab(tabhost
				.newTabSpec("tab2")
				.setIndicator(
						"",
						getResources().getDrawable(
								R.drawable.selector_friendslist_ib_l2))
				.setContent(R.id.gv_friendsl2));
		tabhost.getTabWidget()
				.getChildAt(1)
				.setBackgroundResource(R.drawable.selector_friendslist_ib_l2_bg);

		tabhost.addTab(tabhost
				.newTabSpec("tab3")
				.setIndicator(
						"",
						getResources().getDrawable(
								R.drawable.selector_friendslist_ib_l3))
				.setContent(R.id.gv_friendsl3));
		tabhost.getTabWidget()
				.getChildAt(2)
				.setBackgroundResource(R.drawable.selector_friendslist_ib_l3_bg);

		final int tabs = tabWidget.getChildCount();
	}

	public void init_title() {
		in_title = (View) findViewById(R.id.in_title);
		// 返回
		ib_left = (ImageButton) in_title.findViewById(R.id.ib_left);
		ib_left.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Activity_FriendsList.this,
						Activity_PersonalCenter.class);
				startActivity(intent);
			}
		});
		ib_main = (ImageButton) in_title.findViewById(R.id.ib_main);
		ib_main.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(context,MainActivity.class));
			}
		});
	}

	private void init_gv_friendsl3() {
		// TODO Auto-generated method stub
		gv_friendsl3 = (GridView) findViewById(R.id.gv_friendsl3);
	}

	private void init_gv_friendsl2() {
		// TODO Auto-generated method stub
		gv_friendsl2 = (GridView) findViewById(R.id.gv_friendsl2);
	}

	public void init_gv_friends() {
		gv_friends = (GridView) findViewById(R.id.gv_friends);
		gv_friends.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				// 设置聊天对象
				System.out.println("点击位置click position:" + position);
				// 如果点击的是最后一个，我的上线
				if (position == MyApplication.personL1s.size()) {
					Variable.personChat =MyApplication.personL1.get(0);
					intent = new Intent(Activity_FriendsList.this,
							Activity_FriendsCenter.class);
					intent.putExtra("person",MyApplication.personL1.get(0));
					startActivity(intent);
					// 点在前面的都是我的下线
				} else {
					Variable.personChat = MyApplication.personL1s.get(position);
					intent = new Intent(Activity_FriendsList.this,
							Activity_FriendsCenter.class);
					intent.putExtra("person", MyApplication.personL1s.indexOf(position));
					startActivity(intent);
				}

			}
		});
	}

	class Task_MyFriendsList extends AsyncTask<String, Integer, String> {
		Util_Interaction util_Interaction;
		public Task_MyFriendsList(Context context) {
			progressDialog = CustomProgressDialog
					.createDialog(Activity_FriendsList.this);
			progressDialog.setMessage("正在加载中...");
			progressDialog.show();

		}
		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			// 错将数据库字段source_user写为resource_user，又调试半天，这种东西超过5个字符的最好复制
			util_Interaction = new Util_Interaction();
			// 我的上线
			MyApplication.personL1 =PersonGroup.getSourceUser(Variable.person.getUser());
			// 我的下线
			MyApplication.personL1s = PersonGroup.getBranches(Variable.person.getUser());
			// 我的下线的下线
			MyApplication.personsL2s =PersonGroup.getPersonsL2s();

			return null;
		}



		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			progressDialog.dismiss();
			inflate_gv_friends();
			inflate_gv_friendsl2();
			inflate_gv_friendsl3();
			super.onPostExecute(result);
		}

		private void inflate_gv_friendsl3() {
			// TODO Auto-generated method stub
			//如果没有数据即没有好友直接返回
			list3=dataL3();
			if(list3==null)
				return;
			String[] from = { "iv_portrait", "tv_name", "iv_expandstate" };
			int[] to = { R.id.iv_portrait, R.id.tv_name, R.id.iv_expandstate };
			SimpleAdapter adapter3 = new SimpleAdapter(
					Activity_FriendsList.this, list3,
					R.layout.adapter_friendslist_gridview, from, to);
			gv_friendsl2.setAdapter(adapter3);
		}

		private ArrayList<Map<String, Object>> dataL3() {
			// TODO Auto-generated method stub
			 list3 = new ArrayList<Map<String, Object>>(
					1);
			// 我的下线的下线的下线personsL3s
			if (MyApplication.personsL3s != null) {
				for (Person person : MyApplication.personsL3s) {
					Map<String, Object> map = new HashMap<String, Object>();
					// 头像
					map.put("iv_portrait", drawable.boy);
					// 姓名
					map.put("tv_name", person.getName());
					// 发展状态,将状态图片层设为全透明背景
					map.put("iv_expandstate", R.drawable.friendslist_null);
					list3.add(map);
				}
			}
			// personL2的下线(我的上线的同级)personsL3
			if (MyApplication.personsL3 != null) {
				for (Person person : MyApplication.personsL3) {
					Map<String, Object> map = new HashMap<String, Object>();
					// 头像
					map.put("iv_portrait", drawable.boy);
					// 姓名
					map.put("tv_name", person.getName());
					// 发展状态,将状态图片层设为全透明背景
					map.put("iv_expandstate", R.drawable.friendslist_null);
					list3.add(map);
				}
			}
			// personL2的上线personL3
			if (MyApplication.personL3 != null) {
				for (Person person : MyApplication.personL3) {
					Map<String, Object> map = new HashMap<String, Object>();
					// 头像
					map.put("iv_portrait", drawable.boy);
					// 姓名
					map.put("tv_name", person.getName());
					// 发展状态,将状态图片层设为全透明背景
					map.put("iv_expandstate", R.drawable.friendslist_null);
					list3.add(map);
				}
			}

			return null;
		}

		private void inflate_gv_friendsl2() {
			// TODO Auto-generated method stub
			//如果没有数据即没有好友直接返回
			list2=dataL2();
			if(list2==null)
				return;
			String[] from = { "iv_portrait", "tv_name", "iv_expandstate" };
			int[] to = { R.id.iv_portrait, R.id.tv_name, R.id.iv_expandstate };
			SimpleAdapter adapter2 = new SimpleAdapter(
					Activity_FriendsList.this, list2,
					R.layout.adapter_friendslist_gridview, from, to);
			gv_friendsl2.setAdapter(adapter2);

		}

		/**
		 * @return L2包含成员：我的上线的上线personL2、我的下线的下线personsL2s
		 */
		private List<Map<String, Object>> dataL2() {
			// TODO Auto-generated method stub
			 list2 = new ArrayList<Map<String, Object>>(
					1);
			// 我的下线的下线personsL2s
			if (MyApplication.personsL2s != null) {
				for (Person person : MyApplication.personsL2s) {
					Map<String, Object> map = new HashMap<String, Object>();
					// 头像
					map.put("iv_portrait", drawable.boy);
					// 姓名
					map.put("tv_name", person.getName());
					// 发展状态,将状态图片层设为全透明背景
					map.put("iv_expandstate", R.drawable.friendslist_null);
					list2.add(map);
				}
			}

			// 列表最后一位是我的上线的上线personL2
			if (MyApplication.personL2 != null) {
				for (Person person :MyApplication. personL2) {
					Map<String, Object> map = new HashMap<String, Object>();
					// 头像
					map.put("iv_portrait", drawable.boy);
					// 姓名
					map.put("tv_name", person.getName());
					list2.add(map);
				}
			}
			return list2;
		}

		public void inflate_gv_friends() {
			String[] from = { "iv_portrait", "tv_name", "iv_expandstate" };
			int[] to = { R.id.iv_portrait, R.id.tv_name, R.id.iv_expandstate };
			//如果没有数据即没有好友直接返回
			list=data();
			if(list==null)
				return;
			SimpleAdapter adapter = new SimpleAdapter(
					Activity_FriendsList.this, list,
					R.layout.adapter_friendslist_gridview, from, to);
			gv_friends.setAdapter(adapter);
		}

		List<Map<String, Object>> data() {
			list = new ArrayList<Map<String, Object>>(
					1);
			if (MyApplication.personL1s == null)
				return null;
			// 从Person类的ArrayList数组中遍历出Person对象，逐个操作
			a: for (Person person : MyApplication.personL1s) {
				Map<String, Object> map = new HashMap<String, Object>();
				// 头像
				map.put("iv_portrait", drawable.boy);
				// 姓名
				map.put("tv_name", person.getName());
				if (MyApplication.personsL2s!=null) {
					// 发展状态
					b: for (Person tempperson : MyApplication.personsL2s) {
						// 如果L2中的tempperson的source_user里有person的user，那该person就是已发展状态
						if (tempperson.getSource_user()
								.equals(person.getUser())) {
							flag = true;
							map.put("iv_expandstate",
									R.drawable.friendslist_expanded);
							break b;
						}
					}
				}
				if (flag == false) {
					// 未发展状态
					map.put("iv_expandstate", R.drawable.friendslist_expanding);
				}
				// 将下一位person的标志位置为默认状态false
				flag = false;

				list.add(map);
			}
			// 添加上线person
			Map<String, Object> map = new HashMap<String, Object>();
			// 头像
			map.put("iv_portrait", drawable.boy);
			// 姓名
			map.put("tv_name", MyApplication.personL1.get(0).getName());
			// 发展状态,将状态图片层设为全透明背景
			map.put("iv_expandstate", R.drawable.friendslist_null);
			list.add(map);
			return list;
		}
	}
	public void onClick_Main(View v){
		startActivity(new Intent(this,MainActivity.class));
	}
	public void onClick_Left(View v){
		startActivity(new Intent(this,MainActivity.class));
	}
}
