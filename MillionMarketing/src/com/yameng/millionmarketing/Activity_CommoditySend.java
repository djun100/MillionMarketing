package com.yameng.millionmarketing;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.k.util.CustomProgressDialog;
import com.k.util.FormFile;
import com.k.util.HttpRequestUtil;
import com.k.util.Util_HttpPost;
import com.yameng.fragment.Fragment_SendCommodity;
import com.yameng.fragment.Fragment_SendSkill;
import com.yameng.personal.Activity_PersonalCenter;
import com.yameng.variable.Variable;
import com.yamtz.millionmarketing.R;

public class Activity_CommoditySend extends FragmentActivity {
	Context context=this;
	private static final int SCALE = 5;// 照片缩小比例
	File file;
	ArrayList<FormFile> formFiles;
	FormFile formFile;
	String commodityId;
	Map<String, String> map;
	Map<String, String> filemap;//上传文件附带参数
	String picpath;
	TextView tv_pictureslist;
	ImageView imageView;
	EditText et_title, et_sum, et_price, et_profit, et_describe;
	TextView tv_title_left;
	String title, sum, price, profit, describe;
	StringBuffer sb = new StringBuffer();

	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sendcommodity);
		initCommodityFragment();
		sb.append("待上传商品图片：");
/*		 不实例化，如果没选择图片会空指针错误，
		且不能放到onResume里，调用选择图片后onResume会重新执行重新将file置为空*/
		file = new File("");
		 formFiles=new ArrayList<FormFile>();

	}

	private void initView() {
		// TODO Auto-generated method stub
		imageView = (ImageView) findViewById(R.id.iv_test);
		tv_pictureslist = (TextView) findViewById(R.id.tv_pictureslist);
		et_title = (EditText) findViewById(R.id.et_title);
		et_sum = (EditText) findViewById(R.id.et_sum);
		et_price = (EditText) findViewById(R.id.et_price);
		et_profit = (EditText) findViewById(R.id.et_profit);
		et_describe = (EditText) findViewById(R.id.et_describe);
		tv_title_left = (TextView) findViewById(R.id.tv_title_left);
		
	}

	private void initCommodityFragment() {
		// TODO Auto-generated method stub
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		Fragment_SendCommodity commodity = new Fragment_SendCommodity();
		ft.replace(R.id.container, commodity);
		ft.commit();
	}

	private void initSkillFragment() {
		// TODO Auto-generated method stub
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		Fragment_SendSkill skill = new Fragment_SendSkill();
		ft.replace(R.id.container, skill);
		ft.commit();
	}

	public void onClick_skill(View view) {
		initSkillFragment();
	}

	public void onClick_commodity(View view) {
		initCommodityFragment();
	}

	public void onClick_selectPicture(View v) {
		System.out.println("调用函数响应");
		Intent intent = new Intent();
		/* 开启Pictures画面Type设定为image */
		intent.setType("image/*");
		/* 使用Intent.ACTION_GET_CONTENT这个Action */
		intent.setAction(Intent.ACTION_GET_CONTENT);
		/* 取得相片后返回本画面 */
		startActivityForResult(intent, 1);
	}

	/**
	 * 按钮响应————提交商品
	 * 
	 * @param v
	 */
	public void onClick_commoditySubmit(View v) {
		title = et_title.getText().toString().trim();
		sum = et_sum.getText().toString().trim();
		price = et_price.getText().toString().trim();
		profit = et_profit.getText().toString().trim();
		describe = et_describe.getText().toString().trim();

		new SubmitCommodityAsyncTask(this).execute();

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			Uri uri = data.getData();
			Log.e("uri", uri.toString());

			ContentResolver cr = this.getContentResolver();
			/*
			 * Bitmap bm = MediaStore.Images.Media.getBitmap(cr,
			 * uri);//显得到bitmap图片 //为防止原始图片过大导致内存溢出，这里先缩小原图显示，然后释放原始Bitmap占用的内存
			 * Bitmap smallBitmap = ImageTools.zoomBitmap(bm, bm.getWidth() /
			 * SCALE, bm.getHeight() / SCALE); //释放原始图片占用的内存，防止out of memory异常发生
			 * bm.recycle();
			 */
			// 第二部分
			String[] proj = { MediaStore.Images.Media.DATA };

			// 好像是android多媒体数据库的封装接口，具体的看Android文档

			Cursor cursor = managedQuery(uri, proj, null, null, null);

			// 按我个人理解 这个是获得用户选择的图片的索引值

			int column_index = cursor
					.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

			// 将光标移至开头 ，这个很重要，不小心很容易引起越界

			cursor.moveToFirst();

			// 最后根据索引值获取图片路径

			String path = cursor.getString(column_index);
			System.out.println("path:" + path);
			file = new File(path);

			formFile = new FormFile(file.getName(), file, "document", "*/*");
			formFiles.add(formFile);
			System.out.println("文件名：" + file.getName());
			sb.append("\n" + file.getName());
			System.out.println(sb.toString());
			tv_pictureslist.setText(sb.toString());
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		initView();
	}

	class SubmitCommodityAsyncTask extends AsyncTask<String, Integer, String> {
		boolean isSuccess;
		CustomProgressDialog progressDialog = null;

		public SubmitCommodityAsyncTask(Context context) {
			// 构造函数中显示对话框
			progressDialog = CustomProgressDialog
					.createDialog(Activity_CommoditySend.this);
			progressDialog.setMessage("正在加载中...");
			progressDialog.show();

		}

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			// 上传商品内容并获得数据库中自增后的该商品id
			commodityId = new Util_HttpPost(Variable.accessaddress
					+ "sendcommodity", getParamsMap()).post();
			System.out.println("Activity_SendCommodity————商品id:" + commodityId);
			try {
				System.out.println("检查文件状态(名称)："+file.getName());
				if (file.exists()) {
					
					  isSuccess = HttpRequestUtil .uploadFiles(Variable.address_commodity, getFileParamsMap(),
					  formFiles);
					 
					// null, formFile);

				}

			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		private Map<String, String> getParamsMap() {
			// TODO Auto-generated method stub
			map = new HashMap<String, String>();
			map.put("user", Variable.person.getUser());
			map.put("title", title);
			map.put("sum", sum);
			map.put("price", price);
			map.put("profit", profit);
			map.put("describe", describe);
			map.put("action", "sendcommodity");
			return map;
		}
		
		private Map<String, String> getFileParamsMap() {
			// TODO Auto-generated method stub
			map = new HashMap<String, String>();
			map.put("user", Variable.person.getUser());
			map.put("commodityid", commodityId);
			return map;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			progressDialog.dismiss();
			if (isSuccess) {
				/*
				 * Toast.makeText(MainActivity.this, "文件上传成功",
				 * Toast.LENGTH_SHORT).show();
				 */
				System.out.println("文件上传成功");
			} else {
				/*
				 * Toast.makeText(MainActivity.this, "文件上传失败",
				 * Toast.LENGTH_SHORT).show();
				 */
				System.out.println("文件上传失败");
			}
			startActivity(new Intent(context,Activity_Commodities.class));
			finish();
		}

	}
	public void onClick_Main(View v) {
		startActivity(new Intent(context,MainActivity.class));
		finish();
	}
	public void onClick_Left(View v) {
		startActivity(new Intent(context,Activity_PersonalCenter.class));
		finish();

	}
	public void stopAndGo(Class c){
		startActivity(new Intent(context,c));
		finish();
	}
}
