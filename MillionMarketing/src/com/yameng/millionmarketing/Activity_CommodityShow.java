package com.yameng.millionmarketing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.k.util.CustomProgressDialog;
import com.k.util.HttpPic;
import com.k.util.Util_HttpPost;
import com.yameng.variable.Variable;
import com.yamtz.millionmarketing.R;

public class Activity_CommodityShow extends Activity{
	Activity activity=this;
	boolean self;//是否是自己
	TextView pagetitle;
	TextView tv_title,tv_price,tv_stock,tv_description,tv_comments;
	ViewPager advPager;
	Button btn_delorbuy;
	RelativeLayout rl_pics;
	String[] pics;//地址里图片数
	ViewGroup group;//图片指示points
	private ImageView imageView;
	private ImageView[] imageViews;
	AdvAdapter adapter;
	List<Bitmap> bitmaps=new ArrayList<Bitmap>();//网络获取的商品图片集合
	Bitmap bitmap;//从网络获取的图片
	List<View> vp_pics;//图片组
	Message message=new Message();
	private AtomicInteger what = new AtomicInteger(0);
	private boolean isContinue = false;//非自动滚动
	private final Handler viewHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			advPager.setCurrentItem(msg.what);
			super.handleMessage(msg);
			switch(msg.what){
			case 0:
				//如果有图片，将rl_pics显示出来
				if(Variable.commodity.getPictures().length()==0){
					return;
				}else{
					rl_pics.setVisibility(View.VISIBLE);
				}
				for(Bitmap bitmap:bitmaps){
					ImageView iv=new ImageView(activity);
					iv.setImageBitmap(bitmap);
					vp_pics.add(iv);
					System.out.println("vp_pics当前"+vp_pics.size());
				}
				imageViews = new ImageView[vp_pics.size()];
				addPoint();
				adapter.notifyDataSetChanged();
				break;
			}
		}

	};
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_commodityshow);
		initView();
//		autoRoll();
		new Task().execute();
	}
	
	/**
	 * pics自动滚动
	 */
	public void autoRoll() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {
					if (isContinue) {
						viewHandler.sendEmptyMessage(what.get());
						whatOption();
					}
				}
			}

		}).start();
	}
	
	private void initView() {
		// TODO Auto-generated method stub
		 rl_pics=(RelativeLayout)findViewById(R.id.rl_pics);
		View include=findViewById(R.id.in_title);
		pagetitle = (TextView) findViewById(R.id.tv_pagetitle);
		tv_title = (TextView) findViewById(R.id.tv_title);
		tv_price = (TextView) findViewById(R.id.tv_price);
		tv_stock = (TextView) findViewById(R.id.tv_stock);
		tv_description = (TextView) findViewById(R.id.tv_description);
//		tv_comments = (TextView) findViewById(R.id.tv_comments);
		 group = (ViewGroup) findViewById(R.id.viewGroup);
		 btn_delorbuy=(Button)findViewById(R.id.btn_delorbuy);
		  self=Variable.person.getUser().equals(Variable.commodity.getSeller());
		 btn_delorbuy.setText(self?"删除":"购买");
		 btn_delorbuy.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(self==true){
					//根据商品id删除该商品
					deleteCommodityById(Variable.commodity.getId());

				}else{
					//进入购买商品流程
				}

			}
		});
		pagetitle.setText("资源展示");
		tv_title.setText(Variable.commodity.getTitle());
		tv_price.setText("¥"+Variable.commodity.getPrice()+"元");
		tv_stock.setText("库存："+Variable.commodity.getStock());
		tv_description.setText("商品描述："+Variable.commodity.getDescription());
		 vp_pics = new ArrayList<View>();

		advPager = (ViewPager) findViewById(R.id.vp_pic);
		 adapter=new AdvAdapter(vp_pics);
		advPager.setAdapter(adapter);
		advPager.setOnPageChangeListener(new GuidePageChangeListener());
		advPager.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
				case MotionEvent.ACTION_MOVE:
					isContinue = false;
					break;
				case MotionEvent.ACTION_UP:
					isContinue = true;
					break;
				default:
					isContinue = true;
					break;
				}
				return false;
			}
		});
	}

	protected void deleteCommodityById(String id) {
		// TODO Auto-generated method stub
		new DelCommTask(this).execute();

	}

	public void addPoint() {
		for (int i = 0; i < vp_pics.size(); i++) {
			imageView = new ImageView(this);
			imageView.setLayoutParams(new LayoutParams(20, 20));
			imageView.setPadding(5, 5, 5, 5);
			imageViews[i] = imageView;
			if (i == 0) {
				imageViews[i]
						.setBackgroundResource(R.drawable.banner_dian_focus);
			} else {
				imageViews[i]
						.setBackgroundResource(R.drawable.banner_dian_blur);
			}
			group.addView(imageViews[i]);
		}
	}
	private final class AdvAdapter extends PagerAdapter {
		private List<View> views = null;

		public AdvAdapter(List<View> views) {
			this.views = views;
		}

		@Override
		public void destroyItem(View arg0, int arg1, Object arg2) {
			((ViewPager) arg0).removeView(views.get(arg1));
		}

		@Override
		public void finishUpdate(View arg0) {

		}

		@Override
		public int getCount() {
			return views.size();
		}

		@Override
		public Object instantiateItem(View arg0, int arg1) {
			((ViewPager) arg0).addView(views.get(arg1), 0);
			return views.get(arg1);
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public void restoreState(Parcelable arg0, ClassLoader arg1) {

		}

		@Override
		public Parcelable saveState() {
			return null;
		}

		@Override
		public void startUpdate(View arg0) {

		}
	}
	private final class GuidePageChangeListener implements OnPageChangeListener {

		@Override
		public void onPageScrollStateChanged(int arg0) {

		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {

		}

		@Override
		public void onPageSelected(int arg0) {
			what.getAndSet(arg0);
			for (int i = 0; i < imageViews.length; i++) {
				imageViews[arg0]
						.setBackgroundResource(R.drawable.banner_dian_focus);
				if (arg0 != i) {
					imageViews[i]
							.setBackgroundResource(R.drawable.banner_dian_blur);
				}
			}

		}

	}
	private void whatOption() {
		what.incrementAndGet();
		if (what.get() > imageViews.length - 1) {
			what.getAndAdd(-4);
		}
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {

		}
	}
	class Task extends AsyncTask<String, Integer, String>{

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			System.out.println("Variable.commodity.getPictures():"+Variable.commodity.getPictures());
			pics=Variable.commodity.getPictures().split(",");
			System.out.println("pics大小："+pics.length);
			List<String> list=Arrays.asList(pics);
			for(String temp:list){
				 bitmap= HttpPic.getHttpBitmap(Variable.accessaddress+"small/"+temp);
				 bitmaps.add(bitmap);
				 System.out.println("bitmaps大小："+bitmaps.size());
			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			message.what=0;
			viewHandler.sendMessage(message);

		}
		
	}
	public void onClick_Main(View v){
		startActivity(new Intent(activity,MainActivity.class));
		finish();
	}
	public void onClick_Left(View v){
		startActivity(new Intent(activity,Activity_Commodities.class));
		finish();
	}
	public class DelCommTask extends AsyncTask<String, Integer, String>{
		HashMap<String, String> map;
		CustomProgressDialog dialog;
		public DelCommTask(Context context){
			dialog = CustomProgressDialog
					.createDialog(activity);
			dialog.setMessage("正在加载中...");
			dialog.show();
		}
		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			String result= new Util_HttpPost(Variable.accessaddress+"utilpost",getParamsMap()).post();
			return null;
		}
		
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			dialog.dismiss();
			//直接返回商品列表页
			startActivity(new Intent(activity,Activity_Commodities.class));
			activity.finish();
			super.onPostExecute(result);
		}

		private Map<String, String> getParamsMap() {
			// TODO Auto-generated method stub
			map = new HashMap<String, String>();
			map.put("commodityid", Variable.commodity.getId());
			map.put("action", "deletecommoditybyid");
			return map;
		}
		
	}
}