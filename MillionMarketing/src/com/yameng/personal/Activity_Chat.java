package com.yameng.personal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.inputmethod.InputMethodManager;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.google.gson.Gson;
import com.k.util.Common;
import com.k.util.SendMsgAsyncTask;
import com.yameng.adapter.ChatAdapter;
import com.yameng.bean.Expressions;
import com.yameng.bean.MessageInfo;
import com.yameng.bean.MessageItem;
import com.yameng.bean.Person;
import com.yameng.bean.RecentItem;
import com.yameng.db.MessageDB;
import com.yameng.db.RecentDB;
import com.yameng.millionmarketing.Activity_Register;
import com.yameng.millionmarketing.MainActivity;
import com.yameng.millionmarketing.MainActivity.MessageReceiver;
import com.yameng.utils.MyApplication;
import com.yameng.utils.SharePreferenceUtil;
import com.yameng.utils.Util_Interaction;
import com.yameng.variable.Variable;
import com.yamtz.millionmarketing.R;

/**
 * 在handler中更新接收到的消息
 * 
 * @author Administrator
 * 
 */
public class Activity_Chat extends Activity implements OnClickListener {

	public static boolean isForeground = false;
	// for receive customer msg from jpush server
	private MessageReceiver mMessageReceiver;
	MyApplication myApplication;
	public static final String TAG = "Activity_Chat";
	public static final String MESSAGE_RECEIVED_ACTION = "Activity_Chat.MESSAGE_RECEIVED_ACTION";
	public static final String KEY_TITLE = "title";
	public static final String KEY_MESSAGE = "message";
	public static final String KEY_EXTRAS = "extras";

	private SharePreferenceUtil mSpUtil;
	String chatUser;// 对方用户名
	private Gson mGson;
	private MessageDB mMsgDB;
	private RecentDB mRecentDB;
	String message_content;// 当前消息框里的内容
	ViewPager viewPager;
	ImageButton choose;
	Button send;
	EditText et_sendmessage;
	ArrayList<GridView> grids;// 表情组
	int[] expressionImages;
	String[] expressionImageNames;
	ListView listview;
	ChatAdapter adapter;
	Intent intent;
	private static int MsgPagerNum;
	String user_accept, user_send, message_datetime, action;
	// ArrayList<MessageInfo> messages;
	ArrayList<MessageItem> messages;
	Person mFromUser;
	// MessageInfo message = new MessageInfo();
	MessageItem message = new MessageItem();
	MessageItem messageSend = new MessageItem();
	private boolean isFaceShow = false;// 表情界面标志位
	private InputMethodManager imm;// 输入法界面
	Handler myHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			// 更新显示对方发来的消息
			case 0:

				break;
			}
			super.handleMessage(msg);
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_chitchat);

		initData();
		initView();

	}

	private void initView() {
		// TODO Auto-generated method stub
		viewPager = (ViewPager) findViewById(R.id.viewpager);
		et_sendmessage = (EditText) findViewById(R.id.et_sendmessage);
		send = (Button) findViewById(R.id.bt_chitchat_send_message);
		choose = (ImageButton) findViewById(R.id.ib_chitchat_expression);
		listview = (ListView) findViewById(R.id.listview);

		// adapter = new ChatAdapter(this);
		/*
		 * adapter.addMessage(new MessageInfo("本人进入聊天页面", true));
		 * adapter.addMessage(new MessageInfo("对方进入聊天页面", false));
		 */
		imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
		expressionImages = Expressions.expressionImgs;
		expressionImageNames = Expressions.expressionImgNames;

		listview.setAdapter(adapter);

		choose.setOnClickListener(this);
		send.setOnClickListener(this);
		et_sendmessage.setOnClickListener(this);

		LayoutInflater inflater = LayoutInflater.from(this);
		grids = new ArrayList<GridView>();
		// 表情
		GridView gView1 = (GridView) inflater.inflate(R.layout.grid1, null);

		List<Map<String, Object>> listItems = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < 24; i++) {
			Map<String, Object> listItem = new HashMap<String, Object>();
			listItem.put("image", expressionImages[i]);
			listItems.add(listItem);
		}
		// 第一组表情item layout
		SimpleAdapter simpleAdapter = new SimpleAdapter(this, listItems,
				R.layout.singleexpression, new String[] { "image" },
				new int[] { R.id.image });
		gView1.setAdapter(simpleAdapter);
		gView1.setNumColumns(8);
		gView1.setHorizontalSpacing(1);
		gView1.setVerticalSpacing(1);
		gView1.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.WRAP_CONTENT));
		gView1.setGravity(Gravity.BOTTOM);

		gView1.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Bitmap bitmap = null;
				bitmap = BitmapFactory.decodeResource(getResources(),
						expressionImages[arg2 % expressionImages.length]);
				ImageSpan imageSpan = new ImageSpan(Activity_Chat.this, bitmap);
				SpannableString spannableString = new SpannableString(
						expressionImageNames[arg2].substring(1,
								expressionImageNames[arg2].length() - 1));
				spannableString.setSpan(imageSpan, 0,
						expressionImageNames[arg2].length() - 2,
						Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
				et_sendmessage.append(spannableString);
			}
		});

		grids.add(gView1);
		grids.add((GridView) inflater.inflate(R.layout.grid2, null));
		grids.add((GridView) inflater.inflate(R.layout.grid3, null));
		PagerAdapter mPagerAdapter = new PagerAdapter() {
			@Override
			public boolean isViewFromObject(View arg0, Object arg1) {
				return arg0 == arg1;
			}

			@Override
			public int getCount() {
				return grids.size();
			}

			@Override
			public void destroyItem(View container, int position, Object object) {
				((ViewPager) container).removeView(grids.get(position));
			}

			@Override
			public Object instantiateItem(View container, int position) {
				((ViewPager) container).addView(grids.get(position));
				return grids.get(position);
			}
		};
		viewPager.setAdapter(mPagerAdapter);
		viewPager.setOnPageChangeListener(new GuidePageChangeListener());
		// 开线程定期检测数据库聊天消息
		/*
		 * ReadMessageTask readTask=new ReadMessageTask(); readTask.execute();
		 */
		registerMessageReceiver(); // used for receive msg
	}

	// 从sqlite获取消息转为ArrayList<MessageInfo>,然后依次adapter.add().
	private void initData() {
		// TODO Auto-generated method stub
		mFromUser = Variable.personChat;
		/*
		 * if (mFromUser == null) {// 如果为空，直接关闭 finish(); }
		 */
		myApplication = MyApplication.getInstance();
		mSpUtil = myApplication.getSpUtil();
		mGson = myApplication.getGson();
		mMsgDB = myApplication.getMessageDB();
		mRecentDB = myApplication.getRecentDB();

		MsgPagerNum = 0;

		/*
		 * Set<String> keySet = MyApplication.getInstance().getFaceMap()
		 * .keySet(); keys = new ArrayList<String>(); keys.addAll(keySet);
		 * adapter = new MessageAdapter(this, initMsgData());
		 */
		adapter = new ChatAdapter(this, initMsgData());
		// adapter = new ChatAdapter(this, initMsgDateTemp());
	}

	/**
	 * 加载消息历史，从数据库中读出
	 */
	private List<MessageItem> initMsgData() {
		String usertemp = (mFromUser == null ? Variable.personChatUser
				: mFromUser.getUser());
		List<MessageItem> list = mMsgDB.getMsg(usertemp, MsgPagerNum);
		List<MessageItem> msgList = new ArrayList<MessageItem>();// 消息对象数组
		if (list.size() > 0) {
			for (MessageItem entity : list) {
				if (entity.getName().equals("")) {
					entity.setName(mFromUser.getName());
				}
				/*
				 * if (entity.getHeadImg() < 0) {
				 * entity.setHeadImg(mFromUser.getHeadIcon()); }
				 */
				msgList.add(entity);
			}
		}
		return msgList;

	}

	private ArrayList<MessageInfo> initMsgDateTemp() {
		ArrayList<MessageInfo> messageInfos = new ArrayList<MessageInfo>();
		if (initMsgData().size() > 0) {
			for (MessageItem entity : initMsgData()) {
				MessageInfo messageInfo = new MessageInfo();
				messageInfo.setMessage_content(entity.getMessage());
				messageInfo.setIstoOrForm(!entity.isComMeg());
				messageInfos.add(messageInfo);

			}
		}
		return messageInfos;
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
		unregisterReceiver(mMessageReceiver);// 注销广播接收器
		super.onDestroy();
	}

	class GuidePageChangeListener implements OnPageChangeListener {

		@Override
		public void onPageScrollStateChanged(int arg0) {

			Log.d("onPageScrollStateChanged",
					"onPageScrollStateChanged() invoked!" + arg0);

		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			Log.d("onPageScrolled", "onPageScrolled() invoked!" + arg0 + "arg1"
					+ arg1 + "arg2" + arg2);
		}

		@Override
		public void onPageSelected(int arg0) {
			Log.d("onPageSelected", "onPageSelected() invoked!" + arg0);
		}
	}

	@Override
	protected void onStop() {
		super.onStop();
		finish();
	}

	/*
	 * class SendMessageTask extends AsyncTask<String, Integer, String> {
	 * Message message = new Message();
	 * 
	 * @Override protected String doInBackground(String... params) { // TODO
	 * Auto-generated method stub
	 * user_accept=Variable.personChat.getUser();//对方作为接收方
	 * user_send=Variable.user;//自己作为发送方 new
	 * Util_Interaction().update(Variable.address_connecting + "user_accept=" +
	 * user_accept + "&user_send=" + user_send + "&message_content=" +
	 * message_content_write + "&action=insertchat" // + "&message_datetime=" +
	 * message_datetime ); return null; }
	 * 
	 * @Override protected void onPostExecute(String result) { // TODO
	 * Auto-generated method stub super.onPostExecute(result); }
	 * 
	 * }
	 */
	class SendMessageTask extends AsyncTask<String, Integer, String> {
		Message msg = new Message();

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			if (Variable.personChat != null) {
				user_accept = Variable.personChat.getUser();// 对方作为接收方
			} else {
				user_accept = Variable.personChatUser;
			}

			// JPushSend.send(user_accept, message_content);

			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			// 将message_content置为空
			message_content = null;
			msg.what = 0;
			myHandler.sendMessage(msg);
			super.onPostExecute(result);
		}

	}

	/*
	 * //polling方式轮询数据库更新消息 class ReadMessageTask extends AsyncTask<String,
	 * Integer, String> { Message message = new Message();
	 * 
	 * @Override protected String doInBackground(String... params) { // TODO
	 * Auto-generated method stub while(true){ try { Thread.sleep(5*1000); }
	 * catch (InterruptedException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); } // 从数据库读取聊天内容
	 * user_accept=Variable.user;//自己作为内容接收方
	 * user_send=Variable.personChat.getUser();//对方作为内容发送方 message_datetime=new
	 * Common().getDate(); action="readchat"; messages= new
	 * Util_Interaction().query(Variable.address_connecting + "user_accept=" +
	 * user_accept + "&user_send=" + user_send + "&action=" + action);
	 * //将读取的消息对象插入到当前界面 //必须先判断是否为空 if(messages!=null&&messages.size()!=0){
	 * Message msg=new Message(); msg.what=0; myHandler.sendMessage(msg); } } }
	 * 
	 * }
	 */

	public void onClick_Left(View v) {
		intent = new Intent(Activity_Chat.this, Activity_PersonalCenter.class);
		startActivity(intent);
		finish();
	}

	public void onClick_Main(View v) {
		intent = new Intent(Activity_Chat.this, MainActivity.class);
		startActivity(intent);
		finish();
	}

	public class MessageReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			if (MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {
				String message_content = intent.getStringExtra(KEY_MESSAGE);// 获取附带的消息
				String extras = intent.getStringExtra(KEY_EXTRAS);// 获取附带的json属性字符串
				StringBuilder showMsg = new StringBuilder();
				showMsg.append(KEY_MESSAGE + " : " + message_content + "\n");

				// 组建message对象
				message.setMessage(message_content);
				// 更新到界面
				adapter.addMessage(message);
				// setCostomMsg(showMsg.toString());
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
		switch (v.getId()) {
		case R.id.ib_chitchat_expression:
			if (!isFaceShow) {
				imm.hideSoftInputFromWindow(
						et_sendmessage.getWindowToken(), 0);
				try {
					Thread.sleep(80);// 解决此时会黑一下屏幕的问题
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				viewPager.setVisibility(View.VISIBLE);
				isFaceShow = true;
			} else {
				viewPager.setVisibility(View.GONE);
				isFaceShow = false;
			}
			break;
		case R.id.bt_chitchat_send_message:
			// 获取当前聊天框里的内容
			message_content = et_sendmessage.getText().toString();

			// 将输入框内容置空
			et_sendmessage.setText("");
			if (message_content.length() != 0) {
				// 向聊天表插入聊天内容
				// adapter.addMessage(new MessageInfo(message_content,
				// true));
				viewPager.setVisibility(ViewPager.GONE);
				// 发送消息
				/*
				 * SendMessageTask task = new SendMessageTask();
				 * task.execute();
				 */
				com.yameng.bean.Message msgItem = new com.yameng.bean.Message(
						System.currentTimeMillis(), message_content, "");
				new SendMsgAsyncTask(mGson.toJson(msgItem), mFromUser
						.getPush_userid()).send();
				// 保存数据库
				chatUser = (Variable.personChat == null ? Variable.personChatUser
						: Variable.personChat.getUser());
				messageSend = new MessageItem(
						MessageItem.MESSAGE_TYPE_TEXT, chatUser, System
								.currentTimeMillis(), message_content, 0,
						false, 0);
				// 进行显示
				adapter.addMessage(messageSend);
				// 显示最后一条
				listview.setSelection(adapter.getCount() - 1);
				RecentItem recentItem = new RecentItem(chatUser, 0,
						chatUser, message_content, 0, System
								.currentTimeMillis());
				MyApplication
						.getInstance()
						.getMessageDB()
						.saveMsg(Variable.personChat.getUser(), messageSend);

				MyApplication.getInstance().getRecentDB()
						.saveRecent(recentItem);
			} else {
				Toast.makeText(Activity_Chat.this, "不能发送空的消息",
						Toast.LENGTH_LONG).show();
			}
			//将焦点置为最下一条item
			listview.setSelection(adapter.getCount() - 1);
			break;
		case R.id.et_sendmessage:
			imm.showSoftInput(et_sendmessage, 0);
			viewPager.setVisibility(View.GONE);
			isFaceShow = false;
			break;
		}
	}
}