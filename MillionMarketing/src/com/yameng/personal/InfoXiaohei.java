package com.yameng.personal;

import com.yamtz.millionmarketing.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.Window;
import android.widget.EditText;

public class InfoXiaohei extends Activity {
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.info_xiaohei);              
    }

   public void btn_back(View v) {   
      	this.finish();
      } 
   public void btn_back_send(View v) {     
     	this.finish();
     } 
   public void head_xiaohei(View v) {     
	   Intent intent = new Intent();
		intent.setClass(InfoXiaohei.this,InfoXiaoheiHead.class);
		startActivity(intent);
    } 
    
}
