package com.yameng.personal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;

import com.yameng.millionmarketing.MainActivity;
import com.yamtz.millionmarketing.R;

public class Activity_Address_book extends Activity {

	private Button btn_address_book_serch;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_address_book);

		// 返回
		btn_address_book_serch = (Button) findViewById(R.id.btn_address_book_serch);
		btn_address_book_serch.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Activity_Address_book.this,
						Activity_PersonalCenter.class);
				startActivity(intent);
				overridePendingTransition(R.anim.push_up_out,
						R.anim.my_alpha_action);
			}
		});
	}
}
