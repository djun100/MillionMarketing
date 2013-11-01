package com.yameng.personal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.yamtz.millionmarketing.R;

public class Activity_My_Account extends Activity {

	private Button bt_my_account_serch;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_my_account);

		//返回
		bt_my_account_serch = (Button) findViewById(R.id.bt_my_account_serch);
		bt_my_account_serch.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Activity_My_Account.this,
						Activity_PersonalCenter.class);
				startActivity(intent);
				overridePendingTransition(R.anim.push_up_out,
						R.anim.my_alpha_action);
			}
		});
	}

}
