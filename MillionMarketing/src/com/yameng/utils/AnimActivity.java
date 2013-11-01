package com.yameng.utils;

import android.R.anim;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.RelativeLayout.LayoutParams;

import com.yamtz.millionmarketing.R;

public class AnimActivity extends Activity {
	// 开始复制
	public Button btn_shortcut_info, buttonDelete, btn_shortcut_position,
			btn_shortcut_myachieve, btn_shortcut_myaccount, bt_friend_return,// 返回
			btn_shortcut_myfriends, buttonSleep;
	public Animation animationTranslate, animationRotate, animationScale;
	public static int width, height;
	// android.widget.RelativeLayout.LayoutParams.LayoutParams(int w, int h)
	public LayoutParams params = new LayoutParams(0, 0);
	public static Boolean isClick = false;

	// //////////////////////////


	// ////////////////////开始
	public void initialButton() {
		Display display = getWindowManager().getDefaultDisplay();
		height = display.getHeight();
		width = display.getWidth();
		Log.v("width  & height is:",
				String.valueOf(width) + ", " + String.valueOf(height));

		params.height = 25;
		params.width = 25;
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
		// buttonDelete.setLayoutParams(params);

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
					// buttonDelete.startAnimation(animRotate(-45.0f, 0.5f,
					// 0.45f));

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
					// buttonSleep.startAnimation(animTranslate(170.0f, 0.0f,
					// 190, 0, buttonSleep, 1600));

				} else {
					isClick = false;
					// buttonDelete.startAnimation(animRotate(90.0f, 0.5f,
					// 0.45f));

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
					// buttonSleep.startAnimation(animTranslate(-170.0f, -0.0f,
					// 10, 0, buttonSleep, 500));

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
				// buttonDelete.startAnimation(setAnimScale(0.0f, 0.0f));
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
				// buttonDelete.startAnimation(setAnimScale(0.0f, 0.0f));
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
				// buttonDelete.startAnimation(setAnimScale(0.0f, 0.0f));
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
				// buttonDelete.startAnimation(setAnimScale(0.0f, 0.0f));
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
				// buttonDelete.startAnimation(setAnimScale(0.0f, 0.0f));
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
				// buttonDelete.startAnimation(setAnimScale(0.0f, 0.0f));
			}
		});

	}

	protected Animation setAnimScale(float toX, float toY) {
		animationScale = new ScaleAnimation(1f, toX, 1f, toY,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.45f);
		animationScale.setInterpolator(this,
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
				params = new LayoutParams(0, 0);
				params.height = 25;
				params.width = 25;
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
	// //////////////////////////////////////////
}