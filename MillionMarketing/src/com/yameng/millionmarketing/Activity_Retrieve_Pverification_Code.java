package com.yameng.millionmarketing;

import com.yamtz.millionmarketing.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Activity_Retrieve_Pverification_Code extends Activity {

	private Button btn_verification_code_return;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_retrieve_pverification_code);

		btn_verification_code_return = (Button) findViewById(R.id.btn_verification_code_return);
		btn_verification_code_return.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(
						Activity_Retrieve_Pverification_Code.this,
						Activity_Retrieve_Password.class);
				startActivity(intent);
				overridePendingTransition(R.anim.push_up_out,
						R.anim.my_alpha_action);
			}
		});
	}
}
