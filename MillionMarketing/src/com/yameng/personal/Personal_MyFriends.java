package com.yameng.personal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.yameng.millionmarketing.Activity_Notices;
import com.yameng.millionmarketing.Activity_SearchFriends;
import com.yameng.personal.Personal_Constant.ConValue;
import com.yamtz.millionmarketing.R;

public class Personal_MyFriends extends Activity {
	ListView lv_activity;
	List<Map<String, Object>> list;
	String messages = "0";
	Activity_Personal activity_Personal;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.personal_myfriends);
		init();
	}

	private void init() {
		// TODO Auto-generated method stub
		lv_activity = (ListView) findViewById(R.id.lv_activity);
		 //错把这个ListView写到了Activity_Personal大界面里，一直错误
		//		String[] content={"content1","content2","content3"};不是这样的
		String[] content = new String[] { "邀请好友", "content2", "content3" };
		list = getListForSimpleAdapter();
		SimpleAdapter lv_activity_adapter = new SimpleAdapter(
				Personal_MyFriends.this, list,
				R.layout.personal_myfriends_adapter, new String[] { "item",
						"sum" }, new int[] { R.id.tv_item, R.id.tv_sum }//内容的显示样式控制
		);

		lv_activity.setAdapter(lv_activity_adapter);
		lv_activity.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				System.out.println("view:" + view + "  position:" + position
						+ "sid:" + id);
				//position=0响应
				if ((position + "").equals("0")) {
					System.out.println("进入position==0");
					Intent intent = new Intent(Personal_MyFriends.this,
							Activity_SearchFriends.class);
					startActivity(intent);

				}
				//position=3响应
				if ((position + "").equals("3")) {
					System.out.println("进入position==3");
					Intent intent = new Intent(Personal_MyFriends.this,
							Activity_Notices.class);
					startActivity(intent);

				}
			}
		});
	}

	private List<Map<String, Object>> getListForSimpleAdapter() {
		// TODO Auto-generated method stub
		List<Map<String,Object>>list=new ArrayList<Map<String,       Object>>(2);  
        Map<String, Object> map = new HashMap<String,Object>();  
        map.put("item","邀请好友");  
        map.put("sum", "");  
        list.add(map);  
 
        map = new HashMap<String,Object>();  
        map.put("item","邀请好友");  
        map.put("sum","");  
        list.add(map);  
 
        map = new HashMap<String,Object>();  
        map.put("item","我的消息：");  
        map.put("sum",messages);  
        list.add(map);  
        map = new HashMap<String,Object>();  
        map.put("item","我收到的邀请：");  
        map.put("sum",messages);  
        list.add(map);  
 
       
    return list;  
	}
}
