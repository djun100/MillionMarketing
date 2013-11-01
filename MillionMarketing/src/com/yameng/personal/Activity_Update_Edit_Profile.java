package com.yameng.personal;

import java.io.File;

import android.R.anim;
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
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.view.animation.Animation.AnimationListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;


import com.yameng.variable.Variable;

import com.yameng.millionmarketing.Activity_Subpage_Fund;
import com.yameng.millionmarketing.YM_GPS_MapviewActivity;

import com.yamtz.millionmarketing.R;

public class Activity_Update_Edit_Profile extends Activity {

	private Button bt_edit_profile_return;
	ImageView iv_portrait;
	
	private Button btn_shortcut_info, buttonDelete, btn_shortcut_position,
	btn_shortcut_myachieve, btn_shortcut_myaccount,
	btn_shortcut_myfriends, buttonSleep;
private Animation animationTranslate, animationRotate, animationScale;
private static int width, height;
private RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(0,
	0);
private static Boolean isClick = false;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.update_edit_profile);
		iv_portrait = (ImageView) findViewById(R.id.iv_portrait);
		bt_edit_profile_return = (Button) findViewById(R.id.bt_edit_profile_return);
		bt_edit_profile_return.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Activity_Update_Edit_Profile.this,
						Activity_PersonalCenter.class);
				startActivity(intent);
				overridePendingTransition(R.anim.push_up_out,
						R.anim.my_alpha_action);
			}
		});
		
		initialButton();
	}

	public void onClick_setPortrait(View view) {
		ShowPickDialog();
	}

	private void ShowPickDialog() {

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
						 * 这个地方小马有个疑问，希望高手解答下：就是这个数据URI与类型为什么要分两种形式来写呀？有什么区别？
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
						 * 官方文档太长了就不看了，其实是错的，这个地方小马也错了，必须改正
						 */
						Intent intent = new Intent(
								MediaStore.ACTION_IMAGE_CAPTURE);
						// 下面这句指定调用相机拍照后的照片要存放的路径
						intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri
								.fromFile(new File(Environment
										.getExternalStorageDirectory(),
								// "yameng/xiaomas.jpg")));
										"/xiaomas.jpg")));
						startActivityForResult(intent, 2);
					}
				}).show();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		// 如果是直接从相册获取
		case 1:
			startPhotoZoom(data.getData());
			break;
		// 如果是调用相机拍照时
		case 2:
			File temp = new File(Environment.getExternalStorageDirectory()
			// + "yameng/xiaomas.jpg");
					+ "/xiaomas.jpg");
			// 输出文件路径
			System.out.println(temp.getPath());
			startPhotoZoom(Uri.fromFile(temp));
			break;
		// 取得裁剪后的图片
		case 3:
			/**
			 * 非空判断大家一定要验证，如果不验证的话， 在剪裁之后如果发现不满意，要重新裁剪，丢弃
			 * 当前功能时，会报NullException，小马只 在这个地方加下，大家可以根据不同情况在合适的 地方做判断处理类似情况
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
		 * 直接在里面Ctrl+F搜：CROP ，之前小马没仔细看过，其实安卓系统早已经有自带图片裁剪功能, 是直接调本地库的，小马不懂C C++
		 * 这个不做详细了解去了，有轮子就用轮子，不再研究轮子是怎么 制做的了...吼吼
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
			Drawable drawable = new BitmapDrawable(photo);

			/**
			 * 下面注释的方法是将裁剪之后的图片以Base64Coder的字符方式上 传到服务器，QQ头像上传采用的方法跟这个类似
			 */

			/*
			 * ByteArrayOutputStream stream = new ByteArrayOutputStream();
			 * photo.compress(Bitmap.CompressFormat.JPEG, 60, stream); byte[] b
			 * = stream.toByteArray(); // 将图片流以字符串形式存储下来
			 * 
			 * tp = new String(Base64Coder.encodeLines(b));
			 * 这个地方大家可以写下给服务器上传图片的实现，直接把tp直接上传就可以了， 服务器处理的方法是服务器那边的事了，吼吼
			 * 
			 * 如果下载到的服务器的数据还是以Base64Coder的形式的话，可以用以下方式转换 为我们可以用的图片类型就OK啦...吼吼
			 * Bitmap dBitmap = BitmapFactory.decodeFile(tp); Drawable drawable
			 * = new BitmapDrawable(dBitmap);
			 */
			iv_portrait.setBackgroundDrawable(drawable);
			Variable.portrait_update=true;
		}
	}
	
	// ////////////////////开始

		private void initialButton() {
			Display display = getWindowManager().getDefaultDisplay();
			height = display.getHeight();
			width = display.getWidth();
			Log.v("width  & height is:",
					String.valueOf(width) + ", " + String.valueOf(height));

			params.height = 40;
			params.width = 40;
			// 设置边距 (int left, int top, int right, int bottom)
			params.setMargins(10, 0, 0, 0);

			buttonSleep = (Button) findViewById(R.id.button_composer_sleep);
			buttonSleep.setLayoutParams(params);

			btn_shortcut_myfriends = (Button) findViewById(R.id.btn_shortcut_myfriends);
			btn_shortcut_myfriends.setLayoutParams(params);

			btn_shortcut_myaccount = (Button) findViewById(R.id.btn_shortcut_myaccount);
			btn_shortcut_myaccount.setLayoutParams(params);

			btn_shortcut_myachieve = (Button) findViewById(R.id.btn_shortcut_myachieve);
			btn_shortcut_myachieve.setLayoutParams(params);

			btn_shortcut_position = (Button) findViewById(R.id.btn_shortcut_position);
			btn_shortcut_position.setLayoutParams(params);

			btn_shortcut_info = (Button) findViewById(R.id.btn_shortcut_info);
			btn_shortcut_info.setLayoutParams(params);

			buttonDelete = (Button) findViewById(R.id.button_friends_delete);

			buttonDelete.setOnClickListener(new OnClickListener() {

				/**
				 * 移动的动画效果 TranslateAnimation(float fromXDelta, float toXDelta,
				 * float fromYDelta, float toYDelta) float
				 * fromXDelta:这个参数表示动画开始的点离当前View X坐标上的差值； float toXDelta,
				 * 这个参数表示动画结束的点离当前View X坐标上的差值； float fromYDelta,
				 * 这个参数表示动画开始的点离当前View Y坐标上的差值； float toYDelta)这个参数表示动画开始的点离当前View
				 * Y坐标上的差值；
				 */

				@Override
				public void onClick(View v) {
					if (isClick == false) {
						isClick = true;

						// camera在最下面
						btn_shortcut_info.startAnimation(animTranslate(0.0f,
								180.0f, 0, 180, btn_shortcut_info, 60));
						btn_shortcut_position.startAnimation(animTranslate(30.0f,
								150.0f, 45, 160, btn_shortcut_position, 80));
						btn_shortcut_myachieve.startAnimation(animTranslate(70.0f,
								120.0f, 90, 135, btn_shortcut_myachieve, 100));
						btn_shortcut_myaccount.startAnimation(animTranslate(80.0f,
								90.0f, 130, 100, btn_shortcut_myaccount, 120));
						btn_shortcut_myfriends.startAnimation(animTranslate(90.0f,
								50.0f, 155, 60, btn_shortcut_myfriends, 140));

					} else {
						isClick = false;

						btn_shortcut_info.startAnimation(animTranslate(10.0f,
								-160.0f, 10, 0, btn_shortcut_info, 180));
						btn_shortcut_position.startAnimation(animTranslate(-50.0f,
								-150.0f, 10, 0, btn_shortcut_position, 160));
						btn_shortcut_myachieve.startAnimation(animTranslate(
								-100.0f, -120.0f, 10, 0, btn_shortcut_myachieve,
								140));
						btn_shortcut_myaccount
								.startAnimation(animTranslate(-140.0f, -90.0f, 10,
										0, btn_shortcut_myaccount, 120));
						btn_shortcut_myfriends
								.startAnimation(animTranslate(-160.0f, -50.0f, 10,
										0, btn_shortcut_myfriends, 80));
					}
				}
			});
			btn_shortcut_info.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					btn_shortcut_info.startAnimation(setAnimScale(2.5f, 2.5f));
					btn_shortcut_position.startAnimation(setAnimScale(0.0f, 0.0f));
					btn_shortcut_myachieve.startAnimation(setAnimScale(0.0f, 0.0f));
					btn_shortcut_myaccount.startAnimation(setAnimScale(0.0f, 0.0f));
					btn_shortcut_myfriends.startAnimation(setAnimScale(0.0f, 0.0f));
					buttonSleep.startAnimation(setAnimScale(0.0f, 0.0f));
				}
			});
			btn_shortcut_position.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					btn_shortcut_position.startAnimation(setAnimScale(2.5f, 2.5f));
					btn_shortcut_info.startAnimation(setAnimScale(0.0f, 0.0f));
					btn_shortcut_myachieve.startAnimation(setAnimScale(0.0f, 0.0f));
					btn_shortcut_myaccount.startAnimation(setAnimScale(0.0f, 0.0f));
					btn_shortcut_myfriends.startAnimation(setAnimScale(0.0f, 0.0f));
					buttonSleep.startAnimation(setAnimScale(0.0f, 0.0f));
					Intent callGPSSettingIntent = new Intent(
							android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
					startActivity(new Intent(Activity_Update_Edit_Profile.this,
							YM_GPS_MapviewActivity.class));
					overridePendingTransition(R.anim.my_scale_action,
							R.anim.my_alpha_action);
				}
			});
			btn_shortcut_myachieve.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					btn_shortcut_myachieve.startAnimation(setAnimScale(2.5f, 2.5f));
					btn_shortcut_position.startAnimation(setAnimScale(0.0f, 0.0f));
					btn_shortcut_info.startAnimation(setAnimScale(0.0f, 0.0f));
					btn_shortcut_myaccount.startAnimation(setAnimScale(0.0f, 0.0f));
					btn_shortcut_myfriends.startAnimation(setAnimScale(0.0f, 0.0f));
					buttonSleep.startAnimation(setAnimScale(0.0f, 0.0f));
				}
			});
			btn_shortcut_myaccount.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					btn_shortcut_myaccount.startAnimation(setAnimScale(2.5f, 2.5f));
					btn_shortcut_myachieve.startAnimation(setAnimScale(0.0f, 0.0f));
					btn_shortcut_position.startAnimation(setAnimScale(0.0f, 0.0f));
					btn_shortcut_info.startAnimation(setAnimScale(0.0f, 0.0f));
					btn_shortcut_myfriends.startAnimation(setAnimScale(0.0f, 0.0f));
					buttonSleep.startAnimation(setAnimScale(0.0f, 0.0f));

					Intent intent = new Intent(Activity_Update_Edit_Profile.this,
							Activity_My_Account.class);
					startActivity(intent);
				}
			});
			btn_shortcut_myfriends.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					btn_shortcut_myfriends.startAnimation(setAnimScale(2.5f, 2.5f));
					btn_shortcut_myachieve.startAnimation(setAnimScale(0.0f, 0.0f));
					btn_shortcut_position.startAnimation(setAnimScale(0.0f, 0.0f));
					btn_shortcut_info.startAnimation(setAnimScale(0.0f, 0.0f));
					btn_shortcut_myaccount.startAnimation(setAnimScale(0.0f, 0.0f));
					buttonSleep.startAnimation(setAnimScale(0.0f, 0.0f));
					Intent callGPSSettingIntent = new Intent(
							android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
					startActivity(new Intent(Activity_Update_Edit_Profile.this,
							Activity_Address_book.class));
					overridePendingTransition(R.anim.my_scale_action,
							R.anim.my_alpha_action);
				}
			});
			buttonSleep.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					buttonSleep.startAnimation(setAnimScale(2.5f, 2.5f));
					btn_shortcut_myachieve.startAnimation(setAnimScale(0.0f, 0.0f));
					btn_shortcut_position.startAnimation(setAnimScale(0.0f, 0.0f));
					btn_shortcut_info.startAnimation(setAnimScale(0.0f, 0.0f));
					btn_shortcut_myaccount.startAnimation(setAnimScale(0.0f, 0.0f));
					btn_shortcut_myfriends.startAnimation(setAnimScale(0.0f, 0.0f));
				}
			});

		}

		protected Animation setAnimScale(float toX, float toY) {
			animationScale = new ScaleAnimation(1f, toX, 1f, toY,
					Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
					0.45f);
			animationScale.setInterpolator(Activity_Update_Edit_Profile.this,
					anim.accelerate_decelerate_interpolator);
			animationScale.setDuration(500);
			animationScale.setFillAfter(false);
			return animationScale;

		}

		protected Animation animRotate(float toDegrees, float pivotXValue,
				float pivotYValue) {
			animationRotate = new RotateAnimation(0, toDegrees,
					Animation.RELATIVE_TO_SELF, pivotXValue,
					Animation.RELATIVE_TO_SELF, pivotYValue);
			animationRotate.setAnimationListener(new AnimationListener() {

				@Override
				public void onAnimationStart(Animation animation) {

				}

				@Override
				public void onAnimationRepeat(Animation animation) {
				}

				@Override
				public void onAnimationEnd(Animation animation) {
					animationRotate.setFillAfter(true);
				}
			});
			return animationRotate;
		}

		/**
		 * 移动的动画效果 TranslateAnimation(float fromXDelta, float toXDelta, float
		 * fromYDelta, float toYDelta) float fromXDelta:动画开始的点离当前View X坐标上的差值； float
		 * toXDelta, 动画结束的点离当前View X坐标上的差值； float fromYDelta, 动画开始的点离当前View Y坐标上的差值；
		 * float toYDelta)动画开始的点离当前View Y坐标上的差值；
		 */
		protected Animation animTranslate(float toX, float toY, final int lastX,
				final int lastY, final Button button, long durationMillis) {
			animationTranslate = new TranslateAnimation(0, toX, 0, toY);
			animationTranslate.setAnimationListener(new AnimationListener() {

				@Override
				public void onAnimationStart(Animation animation) {
				}

				@Override
				public void onAnimationRepeat(Animation animation) {
				}

				@Override
				public void onAnimationEnd(Animation animation) {
					params = new RelativeLayout.LayoutParams(0, 0);
					params.height = 30;
					params.width = 30;
					params.setMargins(lastX, lastY, 0, 0);
					button.setLayoutParams(params);
					button.clearAnimation();
				}
			});
			animationTranslate.setDuration(durationMillis);
			return animationTranslate;
		}

		@Override
		protected void onStop() {
			super.onStop();
			finish();
		}
}
