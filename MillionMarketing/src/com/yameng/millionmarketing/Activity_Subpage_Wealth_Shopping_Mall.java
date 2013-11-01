package com.yameng.millionmarketing;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import com.yameng.personal.Activity_Address_book;
import com.yameng.personal.Activity_My_Account;
import com.yamtz.millionmarketing.R;

import android.R.anim;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.view.animation.Animation.AnimationListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class Activity_Subpage_Wealth_Shopping_Mall extends Activity {
	private ImageView[] imageViews = null;
	private ImageView imageView = null;
	private ViewPager advPager = null;
	private AtomicInteger what = new AtomicInteger(0);
	private boolean isContinue = true;
	private Button bt_friend_return;

	private Button btn_shortcut_info, buttonDelete, btn_shortcut_position,
			btn_shortcut_myachieve, btn_shortcut_myaccount,
			btn_shortcut_myfriends, buttonSleep;
	private Animation animationTranslate, animationRotate, animationScale;
	private static int width, height;
	private RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
			0, 0);
	private static Boolean isClick = false;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_subpage_wealth_shopping_mall);
		initViewPager();
		initialButton();

		// 返回
		bt_friend_return = (Button) findViewById(R.id.bt_friend_return);
		bt_friend_return.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(
						Activity_Subpage_Wealth_Shopping_Mall.this,
						MainActivity.class);
				startActivity(intent);
				overridePendingTransition(R.anim.push_up_out,
						R.anim.my_alpha_action);
			}
		});
	}

	private void initViewPager() {
		advPager = (ViewPager) findViewById(R.id.adv_pager);
		ViewGroup group = (ViewGroup) findViewById(R.id.viewGroup);

		List<View> advPics = new ArrayList<View>();

		ImageView img1 = new ImageView(this);
		img1.setBackgroundResource(R.drawable.advertising_default_1);
		advPics.add(img1);

		ImageView img2 = new ImageView(this);
		img2.setBackgroundResource(R.drawable.advertising_default_2);
		advPics.add(img2);

		ImageView img3 = new ImageView(this);
		img3.setBackgroundResource(R.drawable.advertising_default_3);
		advPics.add(img3);

		ImageView img4 = new ImageView(this);
		img4.setBackgroundResource(R.drawable.wealth_shopping_mall_one);
		advPics.add(img4);

		imageViews = new ImageView[advPics.size()];

		for (int i = 0; i < advPics.size(); i++) {
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

		advPager.setAdapter(new AdvAdapter(advPics));
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

	private final Handler viewHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			advPager.setCurrentItem(msg.what);
			super.handleMessage(msg);
		}

	};

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
				startActivity(new Intent(
						Activity_Subpage_Wealth_Shopping_Mall.this,
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

				Intent intent = new Intent(
						Activity_Subpage_Wealth_Shopping_Mall.this,
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
				startActivity(new Intent(
						Activity_Subpage_Wealth_Shopping_Mall.this,
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
		animationScale.setInterpolator(
				Activity_Subpage_Wealth_Shopping_Mall.this,
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