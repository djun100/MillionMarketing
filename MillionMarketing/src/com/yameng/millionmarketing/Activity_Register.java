package com.yameng.millionmarketing;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

import com.k.util.CustomProgressDialog;
import com.yameng.bean.Person;
import com.yameng.personal.Activity_PersonalCenter;
import com.yameng.utils.Util_Interaction;
import com.yameng.variable.Variable;
import com.yamtz.millionmarketing.R;

public class Activity_Register extends Activity {
	EditText et_phone, et_password,et_user,et_identifer,et_email,et_profession,et_address;
	String post, phone, password,user,identifer,email,profession,address;
	String response;// 登录响应
	CustomProgressDialog progressDialog = null;
	Person person;//注册成功后的用户对象
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_register);

		et_phone = (EditText) findViewById(R.id.et_phone);
		et_user = (EditText) findViewById(R.id.et_user);
		et_password = (EditText) findViewById(R.id.et_password);
		et_identifer = (EditText) findViewById(R.id.et_identifer);
		et_email = (EditText) findViewById(R.id.et_email);
		et_profession = (EditText) findViewById(R.id.et_profession);
		et_address = (EditText) findViewById(R.id.et_address);

	}


	// 按钮响应——注册
	public void onClick_login_btn_register(View view) {
		
		user = et_user.getText().toString().trim();
		phone = et_phone.getText().toString().trim();
		password = et_password.getText().toString().trim();
		identifer = et_identifer.getText().toString().trim();
		email = et_email.getText().toString().trim();
		profession = et_profession.getText().toString().trim();
		address = et_address.getText().toString().trim();
		//进行空值判断
		if(user.equals("")){
			toast("用户名为空还想注册？");
			return;
		}
		if(password.equals("")){
			toast("不知道填写密码吗？");
			return;
		}
		if(phone.equals("")){
			toast("请把手机号也填上吧");
			return;
		}
		post = "telephone=" + phone 
				+ "&action=" + "register"
				+ "&password=" + password
				+ "&user=" + user;
		RegisterTask task = new RegisterTask(this);
		task.execute();

	}

	private void toast(String content) {
		Toast.makeText(Activity_Register.this, content, Toast.LENGTH_LONG)
				.show();
	}

	Handler myHandler = new Handler() {
		// 2.重写消息处理函数
		public void handleMessage(Message msg) {
			switch (msg.what) {
			// 判断发送的消息
			case 0:
				// 更新View
				toast("注册失败");
				break;
			case 1:
				toast("注册成功");
				person=new Person();
				person.setUser(user);
				person.setTelephone(phone);
				Variable.telephone = phone;
				Variable.user=user;
				Variable.person=person;
				Intent intent = new Intent(Activity_Register.this,
						Activity_PersonalCenter.class);
				startActivity(intent);
				finish();
				break;
			}
			super.handleMessage(msg);
		}
	};

	class RegisterTask extends AsyncTask<String, Integer, String> {
		Message message = new Message();
		// 显示对话框
		ProgressDialog pdialog;

		public RegisterTask(Context context) {
			progressDialog = CustomProgressDialog
					.createDialog(Activity_Register.this);
			progressDialog.setMessage("正在加载中...");
			progressDialog.show();
		}

		@Override
		protected String doInBackground(String... params) {
			System.out.println("待提交post:" + post);
			response = new Util_Interaction().register_connecting(post);
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
//			System.out.println("response:" + response);//值为空出现错误
			if (!response.equals("1")) {
				message.what = 0;
				myHandler.sendMessage(message);
				return;
			}
			message.what = 1;
			myHandler.sendMessage(message);
			super.onPostExecute(result);
			progressDialog.dismiss();
		}

	}
}
