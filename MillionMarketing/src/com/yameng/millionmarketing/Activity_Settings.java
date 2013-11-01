package com.yameng.millionmarketing;

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
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.baidu.android.pushservice.PushManager;
import com.k.util.CustomProgressDialog;
import com.yameng.personal.Activity_Chat;
import com.yameng.personal.Activity_Personal;
import com.yameng.personal.Activity_PersonalCenter;
import com.yameng.utils.ExitApplication;
import com.yameng.utils.Util_Interaction;
import com.yameng.variable.Variable;
import com.yamtz.millionmarketing.R;

public class Activity_Settings extends Activity implements OnClickListener{
	String TAG="Activity_Settings";
	Button btn_exit_app;
	Activity context=this;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		initView();
	}

	private void initView() {
		// TODO Auto-generated method stub
		btn_exit_app = (Button) findViewById(R.id.btn_exit_app);
		setListener();
	}

	private void setListener() {
		// TODO Auto-generated method stub
		btn_exit_app.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.btn_exit_app:
			
			Log.d(TAG, "结束推送并退出应用");
			if (PushManager.isPushEnabled(context)) {
				PushManager.stopWork(context);
			}
			ExitApplication.getInstance().exit();
			break;
		}
	}

}
