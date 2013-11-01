package com.yameng.personal;

import java.io.File;
import java.io.IOException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.k.util.LocalPicToDrawable;
import com.k.util.UtilBitmap;
import com.yameng.millionmarketing.MainActivity;
import com.yameng.utils.MyApplication;
import com.yameng.variable.Variable;
import com.yamtz.millionmarketing.R;

public class Activity_Personal_Info extends Activity implements OnClickListener{
	TextView  tv_name, tv_telephone,tv_email,tv_address;
	Button btn_user;
	Drawable drawable;
	ImageButton ib_name,ib_state,ib_email,ib_telephone,ib_address,ib_portrait;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_personal_info);
		init();

	}

	private void init() {
		btn_user=(Button)findViewById(R.id.btn_user);
		tv_name=(TextView)findViewById(R.id.tv_name);
		tv_telephone=(TextView)findViewById(R.id.tv_telephone);
		tv_email=(TextView)findViewById(R.id.tv_email);
		tv_address=(TextView)findViewById(R.id.tv_address);
		ib_name=(ImageButton)findViewById(R.id.ib_name);
		ib_state=(ImageButton)findViewById(R.id.ib_state);
		ib_email=(ImageButton)findViewById(R.id.ib_email);
		ib_telephone=(ImageButton)findViewById(R.id.ib_telephone);
		ib_address=(ImageButton)findViewById(R.id.ib_address);
		ib_portrait=(ImageButton)findViewById(R.id.ib_portrait);
		

		File file=new File(MyApplication.path_portrait);
		if(file.exists()){
			try {
				drawable = LocalPicToDrawable.getImageDrawable(MyApplication.path_portrait);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("之前未保存过头像");
			}
			ib_portrait.setBackgroundDrawable(drawable);
		}


		btn_user.setText(Variable.person.getUser());
		tv_name.setText(Variable.person.getName());
		tv_name.getPaint().setFakeBoldText(true);
		tv_telephone.setText(Variable.person.getTelephone());
		tv_email.setText(Variable.person.getEmail());
		tv_address.setText(Variable.person.getAddress());
		
		ib_portrait.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()){
		case R.id.ib_name:
			break;
		case R.id.ib_state:
			break;
		case R.id.ib_email:
			break;
		case R.id.ib_telephone:
			break;
		case R.id.ib_address:
			break;
		case R.id.ib_portrait:
			//点击头像后更换头像
			showPickDialog();
			break;
		}
	}

	private void showPickDialog() {
		// TODO Auto-generated method stub
		new AlertDialog.Builder(this)
		.setTitle("设置头像...")
		.setNegativeButton("相册", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				/**
				 * 刚开始，我自己也不知道ACTION_PICK是干嘛的，后来直接看Intent源码，
				 * 可以发现里面很多东西，Intent是个很强大的东西，大家一定仔细阅读下
				 */
				Intent intent = new Intent(Intent.ACTION_PICK, null);

				/**
				 * 下面这句话，与其它方式写是一样的效果，如果：
				 * intent.setData(MediaStore.Images
				 * .Media.EXTERNAL_CONTENT_URI);
				 * intent.setType(""image/*");设置数据类型
				 * 如果朋友们要限制上传到服务器的图片类型时可以直接写如
				 * ："image/jpeg 、 image/png等的类型"
				 */
				intent.setDataAndType(
						MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
						"image/*");
				startActivityForResult(intent, 1);
			}
		})
		.setPositiveButton("拍照", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				dialog.dismiss();
				/**
				 * 下面这句还是老样子，调用快速拍照功能，至于为什么叫快速拍照，大家可以参考如下官方
				 * 文档，you_sdk_path/docs/guide/topics/media/camera.html
				 * 我刚看的时候因为太长就认真看，其实是错的，这个里面有用的太多了，所以大家不要认为
				 * 官方文档太长了就不看了，其实是错的
				 */
				Intent intent = new Intent(
						MediaStore.ACTION_IMAGE_CAPTURE);
				// 下面这句指定调用相机拍照后的照片存储的路径
				intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri
						.fromFile(new File(Environment
								.getExternalStorageDirectory(),
								"superspace.jpg")));
				startActivityForResult(intent, 2);
			}
		}).show();
	}
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		// 如果是直接从相册获取
		case 1:
			startPhotoZoom(data.getData());
			break;
		// 如果是调用相机拍照时
		case 2:
			File temp = new File(Environment.getExternalStorageDirectory()
					+ "/superspace.jpg");
			startPhotoZoom(Uri.fromFile(temp));
			break;
		// 取得裁剪后的图片
		case 3:
			/**
			 * 非空判断大家一定要验证，如果不验证的话， 在剪裁之后如果发现不满意，要重新裁剪，丢弃
			 * 当前功能时，会报NullException，只 在这个地方加下，大家可以根据不同情况在合适的 地方做判断处理类似情况
			 * 
			 */
			if (data != null) {
				setPicToView(data);
			}
			break;
		default:
			break;

		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	/**
	 * 裁剪图片方法实现
	 * 
	 * @param uri
	 */

	public void startPhotoZoom(Uri uri) {
		/*
		 * 至于下面这个Intent的ACTION是怎么知道的，大家可以看下自己路径下的如下网页
		 * yourself_sdk_path/docs/reference/android/content/Intent.html
		 * 直接在里面Ctrl+F搜：CROP ，之前没仔细看过，其实安卓系统早已经有自带图片裁剪功能, 是直接调本地库的
		 */
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		// 下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
		intent.putExtra("crop", "true");
		// aspectX aspectY 是宽高的比例
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// outputX outputY 是裁剪图片宽高
		intent.putExtra("outputX", 150);
		intent.putExtra("outputY", 150);
		intent.putExtra("return-data", true);
		startActivityForResult(intent, 3);
	}

	/**
	 * 保存裁剪之后的图片数据
	 * 
	 * @param picdata
	 */
	private void setPicToView(Intent picdata) {
		Bundle extras = picdata.getExtras();
		if (extras != null) {
			Bitmap photo = extras.getParcelable("data");
			String sDir=Environment.getExternalStorageDirectory().getPath()+"/yameng";
			File destDir = new File(sDir);
			  if (!destDir.exists()) {
			   destDir.mkdirs();
			  }
			String pathFileName=sDir+"/portrait.png";
			System.out.println("保存图片路径："+pathFileName);
			UtilBitmap.saveBitmap2file(photo, pathFileName);
			Drawable drawable = new BitmapDrawable(photo);
			ib_portrait.setBackgroundDrawable(drawable);
		}
	}

	public void onClick_Main(View v){
		startActivity(new Intent(this,MainActivity.class));
		finish();
	}
	public void onClick_Left(View v){
		startActivity(new Intent(this,MainActivity.class));
		finish();
	}
}
