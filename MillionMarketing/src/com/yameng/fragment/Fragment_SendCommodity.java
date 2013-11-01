package com.yameng.fragment;

import java.io.FileNotFoundException;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.yamtz.millionmarketing.R;

public class Fragment_SendCommodity extends Fragment {
	View view;
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		 view = inflater.inflate(R.layout.fragment_sendcommodity_first, container, false);
//		Button bt_first = (Button) view.findViewById(R.id.bt_first);
//		bt_first.setText(MainActivity.temp);
/*		bt_first.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Intent intent=new Intent(getActivity(),Activity_Second.class);
				startActivity(intent);
			}
		});
*/		return view;
	}
	
}

