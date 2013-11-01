package com.yameng.millionmarketing;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;

import com.baidu.android.pushservice.PushConstants;
import com.baidu.android.pushservice.PushManager;
import com.yameng.utils.MyApplication;
import com.yamtz.millionmarketing.R;

public class Activity_Welcome extends Activity{
	private ImageView welcome;
	String TAG="Activity_Welcome";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		//检测是否首次运行
		SharedPreferences sp = getSharedPreferences("sp", MODE_PRIVATE);
		System.out.println("sp.getBoolean="+sp.getBoolean("FIRSTTIME", false));
		if (!sp.getBoolean("FIRSTTIME", false)) {	
//			if (!sp.getBoolean("FIRSTTIME", false)) {	
			// 
			Editor editor = sp.edit();
			//			editor.putBoolean("FIRSTTIME	", true);不能多空格，否则取不到
			editor.putBoolean("FIRSTTIME", true);
			editor.commit();
			//图片没准备好不进行跳转指引页面
/*			Intent intent = new Intent(Activity_Welcome.this, Activity_FirstRunGuide.class);
			startActivity(intent);
			finish();*/
		}

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_welcome);
		//setContentView(R.layout.tabwidget);
		welcome = (ImageView)findViewById(R.id.welcomImage);
		AlphaAnimation alphaAnimation = new AlphaAnimation(0.1f, 1.0f);
		alphaAnimation.setDuration(2000);
		welcome.startAnimation(alphaAnimation);
		
		alphaAnimation.setAnimationListener(new AnimationListener() {
			
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			public void onAnimationEnd(Animation animation) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Activity_Welcome.this,MainActivity.class);
				startActivity(intent);
				finish();
			}
		});

	}
	
}
