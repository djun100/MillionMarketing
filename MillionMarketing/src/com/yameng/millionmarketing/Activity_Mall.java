package com.yameng.millionmarketing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.k.util.CustomProgressDialog;
import com.k.util.Util_HttpPost;
import com.yameng.bean.Commodity;
import com.yameng.millionmarketing.Activity_Commodities.GetCommoditiesTask;
import com.yameng.personal.Activity_PersonalCenter;
import com.yameng.variable.Variable;
import com.yamtz.millionmarketing.R;

public class Activity_Mall extends Activity{
	Activity context =this;
	ListView lv_commodities;
	TextView tv_pagetitle;
	CustomProgressDialog progressDialog = null;
	ArrayList<Commodity> commodities=new ArrayList<Commodity>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mall);
		initView();
		//获取商品可见的好友的商品list
		new GetCommoditiesTask(this).execute();
	}

	private void initView() {
		// TODO Auto-generated method stub
		View include=findViewById(R.id.in_title);
		lv_commodities=(ListView)findViewById(R.id.lv_commodities);
		tv_pagetitle=(TextView)include.findViewById(R.id.tv_pagetitle);
		
		tv_pagetitle.setText("商品列表");
		lv_commodities.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Variable.commodity=commodities.get(arg2);
				context.startActivity(new Intent(context, Activity_CommodityShow.class));
			}
		});
	}
	private ArrayList<Map<String,Object>> data() {
		// TODO Auto-generated method stub
		ArrayList<Map<String,Object>> data= new ArrayList<Map<String,Object>>();
		Map<String, Object> m;
		for(Commodity commodity:commodities){
			m = new HashMap<String, Object>();
			
			m.put("id", commodity.getId());
			m.put("title", commodity.getTitle());
			m.put("price", "价格：¥"+commodity.getPrice()+"元");
			m.put("seller", "卖家："+commodity.getSeller());
			m.put("picture", commodity.getPictures());
			m.put("sales", "已售："+commodity.getSales()+"件");
			if(commodity.getPictures()==null||commodity.getPictures().isEmpty()){
				m.put("ib_commoditypic", R.drawable.commodity_default_sports);
			}else{
				m.put("ib_commoditypic", R.drawable.commodity_default_sports);
			}
			data.add(m);			
		}
		return data;
	}
	private void inflate_lv_commodities() {
		// TODO Auto-generated method stub
		String[] from={"ib_commoditypic","title","price","seller","sales"};
		int[] to={R.id.iv_commoditypic,R.id.tv_title,R.id.tv_price,R.id.tv_seller,R.id.tv_sales};
		SimpleAdapter adapter=new SimpleAdapter(context, data(), R.layout.adapter_commodities, from, to);
		lv_commodities.setAdapter(adapter);
	}
	class GetCommoditiesTask extends AsyncTask<String, Integer, String>{
		// 显示对话框
		ProgressDialog pdialog;
		String commodities_raw;
		public GetCommoditiesTask(Context context) {
			progressDialog = CustomProgressDialog
					.createDialog(Activity_Mall.this);
			progressDialog.setMessage("正在加载中...");
			progressDialog.show();

		}
		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			 commodities_raw = new Util_HttpPost(Variable.accessaddress
					+ "utilpost", getParamsMap()).post();

			return null;
		}

		private ArrayList<Commodity> getCommodities(String result) {
			// TODO Auto-generated method stub
			if(result==null||result.isEmpty()){
				return null;
				
			}
			// parse json data
			try {

				/* 将result进行JSONArray解析 */
				JSONArray jArray = new JSONArray(result);
				//[{"telephone":1234}]的长度为1
				System.out.println("输出jArray.length()：" + jArray.length());
//				commodities=new ArrayList<Commodity>();
				for (int i = 0; i < jArray.length(); i++) {
					JSONObject json_data = jArray.getJSONObject(i);
					Commodity commodity=new Commodity();
					try {
						commodity.setId(json_data.getString("id"));
					} catch (Exception e) {
						// TODO Auto-generated catch block
						commodity.setId("");
					}
					try {
						commodity.setSeller(json_data.getString("user"));
					} catch (Exception e) {
						// TODO Auto-generated catch block
						commodity.setSeller("");
					}
					try {
						commodity.setTitle(json_data.getString("title"));
					} catch (Exception e) {
						// TODO Auto-generated catch block
						commodity.setTitle("");
					}
					try {
						commodity.setStock(json_data.getString("stock"));
					} catch (Exception e) {
						// TODO Auto-generated catch block
						commodity.setStock("");
					}
					try {
						commodity.setSales(json_data.getString("sales"));
					} catch (Exception e) {
						// TODO Auto-generated catch block
						commodity.setSales("");
					}
					try {
						commodity.setProfit(json_data.getString("profit"));
					} catch (Exception e) {
						// TODO Auto-generated catch block
						commodity.setProfit("");
					}
					try {
						commodity.setPrice(json_data.getString("price"));
					} catch (Exception e) {
						// TODO Auto-generated catch block
						commodity.setPrice("");
					}
					try {
						commodity.setDescription(json_data.getString("discription"));
					} catch (Exception e) {
						// TODO Auto-generated catch block
						commodity.setDescription("");
					}
					try {
						commodity.setBegin(json_data.getString("begin"));
					} catch (Exception e) {
						// TODO Auto-generated catch block
						commodity.setBegin("");
					}
					try {
						commodity.setEnd(json_data.getString("end"));
					} catch (Exception e) {
						// TODO Auto-generated catch block
						commodity.setEnd("");
					}
					try {
						commodity.setPictures(json_data.getString("pictures"));
					} catch (Exception e) {
						// TODO Auto-generated catch block
						commodity.setPictures("");
					}
					commodities.add(commodity);
					System.out.println("Activity_Mall————added commodity:"+commodity.toString());

				}
			}
			catch (JSONException e) {
				System.out.println("Error parsing json");
			}
			return commodities;
		}

		private Map<String, String> getParamsMap() {
			// TODO Auto-generated method stub
			HashMap<String, String> map = new HashMap<String, String>();
			//根据用户名在商品列表中取到自己的商品
			map.put("user", Variable.person.getUser());
			map.put("action", "visiblecommodities");
			return map;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			System.out.println("Activity_Mall————commodities_raw:"+commodities_raw);
			getCommodities(commodities_raw);
			inflate_lv_commodities();
			progressDialog.dismiss();
		}
		
	}
	public void onClick_Main(View v){
		startActivity(new Intent(context,MainActivity.class));
		finish();
	}
	public void onClick_Left(View v){
		startActivity(new Intent(context,Activity_PersonalCenter.class));
		finish();
	}
}
