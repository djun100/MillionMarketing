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
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.k.util.CustomProgressDialog;
import com.yameng.personal.Activity_Chat;
import com.yameng.personal.Activity_Personal;
import com.yameng.personal.Activity_PersonalCenter;
import com.yameng.utils.Util_Interaction;
import com.yameng.variable.Variable;
import com.yamtz.millionmarketing.R;

public class Activity_Prize extends Activity {


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_prize);
	}

}
