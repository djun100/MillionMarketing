package com.yameng.millionmarketing;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;

import com.yamtz.millionmarketing.R;

public class Activity_Retrieve_Password extends Activity {

	private ImageButton ib_user_login;
	private Button bt_password_return, btn_region;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_retrieve_password);

		// 区域
		btn_region = (Button) findViewById(R.id.btn_region);
		onClick_login_btn_region();

		ib_user_login = (ImageButton) findViewById(R.id.ib_user_login);
		ib_user_login.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Intent intent = new Intent(Activity_Retrieve_Password.this,
						Activity_Retrieve_Pverification_Code.class);
				startActivity(intent);
			}
		});

		// 返回
		bt_password_return = (Button) findViewById(R.id.bt_password_return);
		bt_password_return.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Activity_Retrieve_Password.this,
						Activity_Login.class);
				startActivity(intent);
				overridePendingTransition(R.anim.push_up_out,
						R.anim.my_alpha_action);
			}
		});
	}

	// 按钮响应——区域
	public void onClick_login_btn_region() {
		btn_region.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent callGPSSettingIntent = new Intent(
						android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
				startActivity(new Intent(Activity_Retrieve_Password.this,
						YM_GPS_MapviewActivity.class));
				overridePendingTransition(R.anim.my_scale_action,
						R.anim.my_alpha_action);
			}
		});
	}
}
