package com.yameng.millionmarketing;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

import com.yameng.personal.Activity_Chat;
import com.yameng.utils.ExitApplication;
import com.yamtz.millionmarketing.R;

public class Activity_FriendsCenter extends Activity {
Intent intent;
ImageButton ib_chat;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_friendscenter);
		ExitApplication.getInstance().addActivity(this);  
		init();
		intent=getIntent();
		//Person person=(Person)intent.getSerializableExtra("person");
		//String name=person.getName();
		
	}

	private void init() {
		// TODO Auto-generated method stub
		ib_chat=(ImageButton)findViewById(R.id.ib_chat);
		ib_chat.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				intent =new Intent(Activity_FriendsCenter.this,Activity_Chat.class);
				
				startActivity(intent);
			}
		});
	}
	public void onClick_Main(View v){
		startActivity(new Intent(this,MainActivity.class));
	}
	public void onClick_Left(View v){
		startActivity(new Intent(this,MainActivity.class));
	}
}
