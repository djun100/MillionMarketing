package com.yameng.millionmarketing;

import java.util.ArrayList;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.k.util.CustomProgressDialog;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.yameng.bean.Person;
import com.yameng.personal.Activity_PersonalCenter;
import com.yameng.utils.MyApplication;
import com.yameng.utils.SharePreferenceUtil;
import com.yameng.utils.Util_Interaction;
import com.yameng.variable.Variable;
import com.yamtz.millionmarketing.R;

public class Activity_Login extends Activity  {
	Activity context=this;
	private final String TAG = "Activity_Login";
	EditText login_edit_account, login_edit_password;
	private ImageButton savepwd;
	boolean flag = false;
	private Button login;
	String param;
	String getresult;
	CustomProgressDialog progressDialog = null;
	String s_et_user;
	ArrayList<Person> persons;//登录后的自己
	AsyncHttpClient client;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_login);
		
		// 初始化记住我控件
		savepwd = (ImageButton) findViewById(R.id.ib_savepwd);
		onClick_login_btn_savepwd();
		// 初始化用户名控件
		login_edit_account = (EditText) findViewById(R.id.login_edit_account);
		// 初始化密码控件
		login_edit_password = (EditText) findViewById(R.id.login_edit_password);
		
		//为控件做初始填充
		login_edit_account.setText(new SharePreferenceUtil(context, "sp").getUser());
		if(new SharePreferenceUtil(context, "sp").getSavePwdState()==true){
			login_edit_password.setText(new SharePreferenceUtil(context, "sp").getPassword());
			savepwd.setBackgroundResource(R.drawable.login_glide_button_one);
		}else{
			savepwd.setBackgroundResource(R.drawable.login_glide_button_two);
			
		}

	}


	// 按钮响应——记住我
	public void onClick_login_btn_savepwd() {
		savepwd.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (new SharePreferenceUtil(context,"sp").getSavePwdState() != true) {
					//设置为保存密码状态
					new SharePreferenceUtil(context,"sp").setSavePwdState(true);
					//保存密码
					new SharePreferenceUtil(context,"sp").setPassword(login_edit_password.getText().toString());
					//保存账号
					new SharePreferenceUtil(context,"sp").setUser(login_edit_account.getText().toString());
					savepwd.setBackgroundResource(R.drawable.login_glide_button_one);
				} else {
					//设为非保存状态
					new SharePreferenceUtil(context,"sp").setSavePwdState(false);
					savepwd.setBackgroundResource(R.drawable.login_glide_button_two);
					//将保存的账号置为空
					new SharePreferenceUtil(context,"sp").setUser("");
					//将保存的密码置为空
					new SharePreferenceUtil(context,"sp").setPassword("");
				}
			}
		});
	}

	// 按钮响应——注册
	public void onClick_login_btn_register(View v) {

				Intent intent = new Intent(Activity_Login.this,
						Activity_Register.class);
				startActivity(intent);
				finish();
	}

	// 按钮响应——找回密码
	public void onClick_login_btn_Retrieve(View v) {
		Intent intent = new Intent(Activity_Login.this,
				Activity_Retrieve_Password.class);
		startActivity(intent);
	}

	// 按钮响应——登录
	public void onClick_login_btn_login(View view) {
		
		 s_et_user = login_edit_account.getText().toString().trim();
//		Variable.user = account;
		String password = login_edit_password.getText().toString().trim();
		//进行空值判断
		if(s_et_user.equals("")){
			toast("用户名不能为空");
			return;
		}else if(password.equals("")){
			toast("密码不能为空");
			return;
		}
		param = "user=" + s_et_user + "&password=" + password+"&action=login";
		System.out.println(param);
		LoginTask task = new LoginTask(this);
		task.execute();

	}

	class LoginTask extends AsyncTask<String, Integer, String> {
		Message message = new Message();
		// 显示对话框
		ProgressDialog pdialog;

		public LoginTask(Context context) {
			progressDialog = CustomProgressDialog
					.createDialog(Activity_Login.this);
			progressDialog.setMessage("正在加载中...");
			progressDialog.show();

		}

		@Override
		protected String doInBackground(String... params) {
			param=Variable.address_connecting+param;
			persons = new Util_Interaction().select(param);
			
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
				if (persons!= null&&persons.size()!= 0) {
				message.what = 1;
				

				} else {
				message.what = 0;
			}
			myHandler.sendMessage(message);
			// pdialog.dismiss();
			progressDialog.dismiss();
			super.onPostExecute(result);
		}



	}

	private void toast(String content) {
		// TODO Auto-generated method stub
		Toast.makeText(Activity_Login.this, content, Toast.LENGTH_LONG).show();
	}

	Handler myHandler = new Handler() {
		// 2.重写消息处理函数
		public void handleMessage(Message msg) {
			switch (msg.what) {
			// 判断发送的消息
			case 0:
				// 更新View
				toast("用户名或者密码错误");
				break;
			case 1:

				Variable.user=s_et_user;
				Variable.person=persons.get(0);
				setPush_useridToDB();

				Variable.source_user=persons.get(0).getSource_user();
				toast("欢迎回来，" + Variable.user);

				Intent intent = new Intent(Activity_Login.this,
						Activity_PersonalCenter.class);
				Log.d(TAG, "正在跳转到个人中心……");
				startActivity(intent);

				MainActivity.context.finish();
				Log.d(TAG, "登录完成，结束当前页面");
				finish();
				break;
			}
			super.handleMessage(msg);
		}

		private void setPush_useridToDB() {
			// TODO Auto-generated method stub
			client=MyApplication.getInstance().getClient();
			RequestParams params = new RequestParams();
			params.put("push_userid", Variable.push_userid);
			params.put("user", Variable.person.getUser());
			client.get(Variable.address_connecting, params, new AsyncHttpResponseHandler(){
				  @Override
				    public void onSuccess(String response) {
					  Log.d(TAG, response);
				        if(response.equals("1")){
				        	Log.d(TAG,"绑定push_userid成功");
				        }else{
				        	Log.d(TAG,"绑定push_userid失败，将收不到消息或者收到之前绑定的push_userid的消息");
				        }
				  }
			});

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
