package com.yameng.personal;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.AdapterView.OnItemClickListener;

import com.yameng.bean.Person;
import com.yameng.millionmarketing.Activity_FriendsCenter;
import com.yameng.variable.Variable;
import com.yamtz.millionmarketing.R;;
public class FriendsList_FragmentL1 extends Fragment {
	GridView gv_friends;
	ArrayList<Person> persons;
	Intent intent;
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_friendslist_l1, container, false);
/*		Button bt_first = (Button) view.findViewById(R.id.bt_first);
		bt_first.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {

				FragmentTransaction ft = getFragmentManager()
						.beginTransaction();
				ft.setCustomAnimations(R.animator.move_in, R.animator.move_out);
				SecondFragment second = new SecondFragment();
				ft.replace(R.id.container, second);
				ft.commit();
			}
		});
		
*/		
		gv_friends = (GridView) view.findViewById(R.id.gv_friends);
		gv_friends.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				//设置聊天对象
//				Variable.personChat=getActivity().persons.get(position);
				 intent=new Intent(getActivity(),Activity_FriendsCenter.class);
				 intent.putExtra("person", persons.indexOf(position));
				 startActivity(intent);
				 
			}
		});
		return view;
	}

}
