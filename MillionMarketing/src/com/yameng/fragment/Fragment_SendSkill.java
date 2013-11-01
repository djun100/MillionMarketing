package com.yameng.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.yamtz.millionmarketing.R;

public class Fragment_SendSkill extends Fragment {

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_sendcommodity_second, container, false);
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
