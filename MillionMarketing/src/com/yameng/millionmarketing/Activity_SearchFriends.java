package com.yameng.millionmarketing;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

import com.yameng.bean.Person;
import com.yameng.utils.Util_Interaction;
import com.yamtz.millionmarketing.R;

/**
 * @author Administrator
 * 
 */
public class Activity_SearchFriends extends Activity {
	EditText et_search;
	ProgressDialog dialog;
	String name, telephone;
	String[] result;
	Person person;
	Message message;
Intent intent;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_searchfriends);
		init();
	}

	private void init() {
		// TODO Auto-generated method stub
		et_search=(EditText)findViewById(R.id.et_search);
	}
	/**
	 * 按钮响应——搜索好友
	 * 
	 * @param view
	 */
	public void onClick_SearchFriends(View view) {
		Toast.makeText(this, "正在努力搜索……", Toast.LENGTH_LONG).show();
		try {
			Task_GetPersons task_GetPersons = new Task_GetPersons();
			task_GetPersons.execute();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Toast.makeText(Activity_SearchFriends.this, "未搜索到该用户",
					Toast.LENGTH_LONG).show();

		}

	}

	class Task_GetPersons extends AsyncTask<String, Integer, String> {

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			// 预留对话框
			// dialog=new ProgressDialog(Activity_SearchFriends.this, 0);
			/*
			 * 获取人员数据列表
			 */
			String s_et_search = et_search.getText().toString().trim();
			 person = new Util_Interaction().searchFriends(s_et_search);
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			if(person==null){
				message=new Message();
				message.what=0;
				handler.sendMessage(message);
				return;
			}else{
				intent=new Intent(Activity_SearchFriends.this,Activity_SearchFriendsResult.class);
				intent.putExtra("person", person);
				startActivity(intent);
			}

			super.onPostExecute(result);
		}
	}
	Handler handler=new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			switch(msg.what){
			case 0:
				Toast.makeText(Activity_SearchFriends.this, "该用户未注册", Toast.LENGTH_LONG).show();
			}
			super.handleMessage(msg);
		}

		
	};
	public void onClick_Main(View v){
		startActivity(new Intent(this,MainActivity.class));
		finish();
	}
	public void onClick_Left(View v){
		startActivity(new Intent(this,MainActivity.class));
		finish();
	}
}
