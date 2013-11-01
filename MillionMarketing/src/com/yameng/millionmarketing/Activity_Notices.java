package com.yameng.millionmarketing;

import java.util.ArrayList;
import java.util.Formatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.yameng.bean.Person;
import com.yameng.utils.Util_Interaction;
import com.yameng.variable.Variable;
import com.yamtz.millionmarketing.R;

/**
 * @author Administrator
 * 
 */
public class Activity_Notices extends Activity {
	ListView lv_notices;
	Person person;
	ArrayList<Person> persons;
	int temp_position;//item位置的对外引用
	 String temp;//要传递的网址参数
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_notices);
		init();
		Task_GetReceive task_GetReceive = new Task_GetReceive();
		task_GetReceive.execute();
	}

	private void init() {
		// TODO Auto-generated method stub
		lv_notices = (ListView) findViewById(R.id.lv_notices);
		lv_notices.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				temp_position=position;
				showDialog();

			}
		});

	}
	protected void showDialog() {
		// TODO Auto-generated method stub
		  AlertDialog.Builder builder = new Builder(Activity_Notices.this);
//		  builder.setMessage("确认退出吗？");

		  builder.setTitle("是否接受邀请？");

		  builder.setPositiveButton("接受", new OnClickListener() {

		   @Override
		   public void onClick(DialogInterface dialog, int which) {
		    dialog.dismiss();
		    //http://pc:8080/testweb/connecting?user=admin&source_user=lala&action=acceptinvite
		     temp=new Formatter().format("user=%s&source_user=%s&action=acceptinvite",
		    		Variable.user,persons.get(temp_position).getUser()).toString();
		     //异步接受邀请
		     Task_AcceptInvite task=new Task_AcceptInvite();
		     task.execute();
			
		   }
		  });

		  builder.setNegativeButton("拒绝", new OnClickListener() {

		   @Override
		   public void onClick(DialogInterface dialog, int which) {
		    dialog.dismiss();
			persons.remove(temp_position);
			lv_notices.postInvalidate();
			//从数据库删除邀请
		   }
		  });

		  builder.create().show();
	}

	public void onClick_CurrentItem(View view) {
		// view.getParent()
	}

	class Task_GetReceive extends AsyncTask<String, Integer, String> {


		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			String user = Variable.user;
			Util_Interaction util_Interaction = new Util_Interaction();
			persons = util_Interaction.receiveInvite(Variable.user);
//			System.out.println("响应数据persons大小:" + persons.size());//空异常

			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			/*
			 * 给listview填充数据
			 */
			SimpleAdapter adapter = new SimpleAdapter(
					Activity_Notices.this, data(),
					R.layout.adapter_notices, new String[] {
							"user_inviteme" }, new int[] { R.id.tv_personinviteme});

			if (!(persons == null)) {
				lv_notices.setAdapter(adapter);
			}



			super.onPostExecute(result);
		}



		List<Map<String, Object>> data() {
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>(
					1);
			if (persons == null)
				return null;
			Map<String, Object> map;
			for (Person person:persons) {
				map = new HashMap<String, Object>();
				map.put("user_inviteme", person.getUser()+"请求加我为好友");
				list.add(map);
				System.out.println(list.size());
			}
			return list;

		}

	}
	//同意邀请
	class Task_AcceptInvite extends AsyncTask<String, Integer, String>{

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
		    new Util_Interaction().update(Variable.address_connecting+temp);

			//从数据库删除邀请
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			Toast.makeText(Activity_Notices.this, "已经接受邀请",
					Toast.LENGTH_LONG).show();
			persons.remove(temp_position);
			lv_notices.postInvalidate();
			super.onPostExecute(result);
		}
		
		
	}
}
