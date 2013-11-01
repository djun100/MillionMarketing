package com.yameng.millionmarketing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.k.util.Util_HttpPost;
import com.yameng.bean.Commodity;
import com.yameng.personal.Activity_PersonalCenter;
import com.yameng.variable.Variable;
import com.yamtz.millionmarketing.R;

public class Activity_Commodities extends Activity{
	//生成的adapter默认上下文参数是context，这里干脆就初始化下，下面自动生成的context就不用改activity.this了
	Context context=this;
	TextView tv_title,tv_seller,tv_price,tv_sales,tv_pagetitle,tv_tip;
	ImageView iv_commoditypic;
	ListView lv_commodities;
	ArrayList<Commodity> commodities=new ArrayList<Commodity>();
	Handler myHandler = new Handler() {
		// 2.重写消息处理函数
		public void handleMessage(Message msg) {
			switch (msg.what) {
			// 判断发送的消息
			case 0:
				// 显示出快发布吧
				tv_tip.setVisibility(View.VISIBLE);
				break;
		
			}
			super.handleMessage(msg);
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_commodites);
		initView();
		new GetCommoditiesTask().execute();

	}

	private void initView() {
		// TODO Auto-generated method stub
		tv_tip = (TextView) findViewById(R.id.tv_tip);
		tv_title = (TextView) findViewById(R.id.tv_title);
		tv_seller = (TextView) findViewById(R.id.tv_seller);
		tv_price = (TextView) findViewById(R.id.tv_price);
		tv_sales = (TextView) findViewById(R.id.tv_sales);
		iv_commoditypic = (ImageView) findViewById(R.id.iv_commoditypic);
		lv_commodities = (ListView) findViewById(R.id.lv_commodities);
		View include=findViewById(R.id.in_title);
		tv_pagetitle=(TextView)include.findViewById(R.id.tv_pagetitle);
		
		tv_pagetitle.setText("我的资源");
		lv_commodities.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Variable.commodity=commodities.get(arg2);
				stopAndGo(context, Activity_CommodityShow.class);
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
		Message message=new Message();
		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			String commodities_raw = new Util_HttpPost(Variable.accessaddress
					+ "commoditieslist", getParamsMap()).post();
			getCommodities(commodities_raw);
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
				commodities=new ArrayList<Commodity>();
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
					System.out.println("added commodity:"+commodity.toString());

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
			map.put("action", "commoditieslist");
			return map;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if(commodities.size()==0){
				message.what=0;
				myHandler.sendMessage(message);				
			}

			inflate_lv_commodities();
			
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
	public void stopAndGo(Context context,Class cls){
		startActivity(new Intent(context,cls));
		finish();
	}
}
