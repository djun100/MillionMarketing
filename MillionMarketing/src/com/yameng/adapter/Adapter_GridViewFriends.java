package com.yameng.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.yameng.bean.Person;
import com.yamtz.millionmarketing.R;

public class Adapter_GridViewFriends extends SimpleAdapter {
	public Adapter_GridViewFriends(Context context,
			List<? extends Map<String, ?>> data, int resource, String[] from,
			int[] to) {
		super(context, data, resource, from, to);
		// TODO Auto-generated constructor stub
	}

	//上下文

	List<Person> persons=new ArrayList<Person>(0);
	//构造函数，传入上下文

	@Override
	public int getCount() {
		return persons.size();
	}

	@Override
	public Object getItem(int position) {
		return persons.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ImageView iv_portrait = (ImageView) convertView.findViewById(R.id.iv_portrait);
		ImageView iv_expandstate = (ImageView) convertView.findViewById(R.id.iv_expandstate);
		TextView textView = (TextView) convertView.findViewById(R.id.tv_name);
		return convertView;
	}

}