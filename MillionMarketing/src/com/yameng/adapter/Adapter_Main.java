package com.yameng.adapter;

import com.yamtz.millionmarketing.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class Adapter_Main extends BaseAdapter {
	private Context myContext;
	private int id;
	private final int[] pageTwo = { R.drawable.main_gridview_item_1,
			R.drawable.main_gridview_item_2, R.drawable.main_gridview_item_3,
			R.drawable.main_gridview_item_4, R.drawable.main_gridview_item_5,
			R.drawable.main_gridview_item_6, R.drawable.main_gridview_item_7,
			R.drawable.main_gridview_item_8, };
	private final int[] pageOne = { R.drawable.main_gridview_item_ask,
			R.drawable.item2, R.drawable.main_gridview_item_green,
			R.drawable.item2, R.drawable.main_gridview_item_green,
			R.drawable.item2, R.drawable.main_gridview_item_green,
			R.drawable.item2,

	};

	public Adapter_Main(Context c, int id) {
		this.myContext = c;
		this.id = id;
	}

	public int getCount() {
		int count;
		if (1 == id) {
			count = pageOne.length;
		} else {
			count = pageTwo.length;
		}
		return count;
	}

	public Object getItem(int position) {
		return position;
	}

	public long getItemId(int position) {
		return position;
	}
	//项目view布局
	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = LayoutInflater.from(myContext).inflate(
				R.layout.main_gridview_item, null);

		ImageView imageView = (ImageView) convertView.findViewById(R.id.iv);
		if (1 == id) {
			if (!(position == 1)) {
				imageView.setBackgroundResource(pageOne[position]);
			}
		} else {
			imageView.setBackgroundResource(pageTwo[position]);
		}
		return convertView;
	}

}