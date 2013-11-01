package com.yameng.personal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.yamtz.millionmarketing.R;

public class Personal_MyAccount extends Activity {
	ListView personal_lv_myaccount;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.personal_myaccount);
		personal_lv_myaccount = (ListView) findViewById(R.id.personal_lv_myaccount);
		SimpleAdapter adapter = new SimpleAdapter(this, getData(),
				R.layout.personal_myaccount_listview_adapter, new String[] {
						"title", "info" }, new int[] { R.id.title, R.id.info });
		personal_lv_myaccount.setAdapter(adapter);

	}

	private List<Map<String, Object>> getData() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("title", "余        额：");
		map.put("info", "0.00");
		list.add(map);

		map = new HashMap<String, Object>();
		map.put("title", "积        分：");
		map.put("info", "0");
		list.add(map);

		map = new HashMap<String, Object>();
		map.put("title", "                  充       值                        ");
		map.put("info", "");
		list.add(map);

		return list;
	}

}