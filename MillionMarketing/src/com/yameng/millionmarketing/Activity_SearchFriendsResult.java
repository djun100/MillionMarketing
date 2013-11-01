package com.yameng.millionmarketing;

import java.util.ArrayList;
import java.util.Formatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.yameng.bean.Person;
import com.yameng.utils.Util_Interaction;
import com.yameng.variable.Variable;
import com.yamtz.millionmarketing.R;
import com.yamtz.millionmarketing.R.drawable;

/**
 * @author Administrator
 * 
 */
public class Activity_SearchFriendsResult extends Activity {
	TextView tv_user, tv_name;
	ListView lv_result;
	ProgressDialog dialog;
	String name, telephone;
	String[] result;
	Person person;
	Message message;
	Intent intent;
	String response;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_searchfriendsresult);
		intent = getIntent();
		person = (Person) intent.getSerializableExtra("person");
		init();
	}

	private void init() {
		// TODO Auto-generated method stub
		tv_user = (TextView) findViewById(R.id.tv_user);
		tv_user.setText(person.getUser());
		tv_name = (TextView) findViewById(R.id.tv_name);
		tv_name.setText(person.getName());

	}

	public void onClick_Add(View v) {
		AddTask addTask=new AddTask();
		addTask.execute();
	}
	 class AddTask extends AsyncTask<String, Integer, String> {  
	        Message message = new Message();  
	  
	        @Override  
	        protected String doInBackground(String... params) {  
	            // TODO Auto-generated method stub  
	    		// 邀请好友
	    		try {
	    			String sql = new Formatter().format("user=%s&user_inviteme=%s",
	    					person.getUser(), Variable.user).toString();
	    			// 当前用户是否第一次给这位用户发送邀请
	    			System.out.println("检测当前用户是否第一次给这位用户发送邀请");
	    			 response = new Util_Interaction().sendInvite(sql);
	    			System.out.println("response=" + response);

	    		} catch (Exception e) {
	    			// TODO Auto-generated catch block
	    			e.printStackTrace();
	    			Toast.makeText(Activity_SearchFriendsResult.this, "邀请失败",
	    					Toast.LENGTH_LONG).show();

	    		}
	            return null;  
	        }  
	  
	        @Override  
	        protected void onPostExecute(String result) {  
	            // TODO Auto-generated method stub  
    			if (response.equals("请不要重复邀请")) {
    				System.out.println("结果不等于NotFind，用户已经发送过邀请");
    				Toast.makeText(Activity_SearchFriendsResult.this,
    						"请不要重复发送邀请通知", Toast.LENGTH_LONG).show();
    			} else {
    				Toast.makeText(Activity_SearchFriendsResult.this, "已发送邀请通知",
    						Toast.LENGTH_LONG).show();
    			}
/*	            System.out.println("response:" + response);  
	            if () {  
	                message.what = 0;  
	                myHandler.sendMessage(message);  
	                return;  
	            }  
	            message.what = 1;  
	            myHandler.sendMessage(message);  
	            super.onPostExecute(result);  */
	        }
	 }
}
