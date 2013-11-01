package com.yameng.personal;

import android.app.TabActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

import com.k.util.Util_Weather;
import com.yameng.millionmarketing.YM_GPS_MapviewActivity;
import com.yamtz.millionmarketing.R;

public class Activity_Personal extends TabActivity {
	RelativeLayout rl;
	private TabHost tab;
	private LayoutInflater layoutInflater;
	ImageButton ib_playoff;
	static boolean playstatic = false;
	TextView tv_region, tv_temperature_weather;
	String region, weather_tempe;
	Button btn_region;
	private Button bt_friend_return;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_personal);

		ib_playoff = (ImageButton) findViewById(R.id.ib_playoff);
		rl = (RelativeLayout) findViewById(R.id.rl_function1);
		rl.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(Activity_Personal.this,
						Activity_Personal_Info.class);
				startActivity(intent);
			}
		});
		tv_region = (TextView) findViewById(R.id.tv_region);
		btn_region = (Button) findViewById(R.id.btn_region);
		btn_region.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Intent callGPSSettingIntent = new Intent(
						android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
				startActivity(new Intent(Activity_Personal.this,
						YM_GPS_MapviewActivity.class));
				overridePendingTransition(R.anim.my_scale_action,
						R.anim.my_alpha_action);
			}
		});
		region = "上海";
		tv_region.setText(region);
		tv_temperature_weather = (TextView) findViewById(R.id.tv_temperature_weather);

		init();
		Task_Weather task = new Task_Weather();
		task.execute();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		return super.onOptionsItemSelected(item);
	}

	private void init() {
		tab = getTabHost();
		layoutInflater = LayoutInflater.from(this);
		int count = Personal_Constant.ConValue.mTabClassArray.length;
		for (int i = 0; i < count; i++) {
			System.out.println("2--------------");
			TabSpec tabSpec = tab
					.newTabSpec(Personal_Constant.ConValue.mTextviewArray[i])
					.setIndicator(getTabItemView(i))
					.setContent(getTabItemIntent(i));
			tab.addTab(tabSpec);
			tab.getTabWidget().getChildAt(i)
					.setBackgroundResource(R.drawable.selector_tab_background);
		}
	}

	private View getTabItemView(int index) {
		View view = layoutInflater.inflate(R.layout.personal_tabwiget_view,
				null);

		TextView textView = (TextView) view.findViewById(R.id.textview);
		textView.setText(Personal_Constant.ConValue.mTextviewArray[index]);

		return view;

	}

	private Intent getTabItemIntent(int index) {
		Intent intent = new Intent(this,
				Personal_Constant.ConValue.mTabClassArray[index]);

		return intent;
	}

	public void onClick_player(View view) {
		if (playstatic == false) {
			ib_playoff.setBackgroundResource(R.drawable.personal_player_on);
			playstatic = true;
		} else {
			ib_playoff.setBackgroundResource(R.drawable.personal_player_off);
			playstatic = false;
		}
	}

	class Task_Weather extends AsyncTask<String, Integer, String> {

		@Override
		protected String doInBackground(String... params) {
			String[] weather = new Util_Weather().getWeather(region);

			weather_tempe = weather[0] + "  " + weather[1];
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			tv_temperature_weather.setText(weather_tempe);
			super.onPostExecute(result);
		}
	}
	
	public void Return(){
		bt_friend_return = (Button) findViewById(R.id.bt_friend_return);
		bt_friend_return.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
			}
		});
	}
}
