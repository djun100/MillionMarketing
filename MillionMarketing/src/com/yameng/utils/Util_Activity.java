package com.yameng.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;



public class Util_Activity extends Activity{
	public void stopAndGo(Context context,Class cls){
		startActivity(new Intent(context,cls));
		finish();
	}
}
