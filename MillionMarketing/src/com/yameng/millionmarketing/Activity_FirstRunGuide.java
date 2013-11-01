package com.yameng.millionmarketing;

import java.util.ArrayList;
import java.util.List;

import com.yamtz.millionmarketing.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.view.Window;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;

/**
 * @author manymore13
 */
public class Activity_FirstRunGuide extends Activity {

	private static final int TO_THE_END = 0;
	private static final int LEAVE_FROM_END = 1;

	private int[] ids = { R.drawable.guide_1, R.drawable.guide_3,
			R.drawable.guide_5, R.drawable.guide_6, R.drawable.guide_7 };


	private List<View> guides = new ArrayList<View>();
	private ViewPager pager;
	private ImageView start;
	private ImageView curDot;
	private LinearLayout dotContain;
	private int offset;
	private int curPos = 0;
	Intent intent;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_guide);
		init();
	}

	private ImageView buildImageView(int id) {
		ImageView iv = new ImageView(this);
		iv.setImageResource(id);
		ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
				ViewGroup.LayoutParams.FILL_PARENT,
				ViewGroup.LayoutParams.FILL_PARENT);
		iv.setLayoutParams(params);
		iv.setScaleType(ScaleType.FIT_XY);
		return iv;
	}

	private void init() {
		this.getView();
		initDot();
		ImageView iv = null;
		guides.clear();
		for (int i = 0; i < ids.length; i++) {
			iv = buildImageView(ids[i]);
			guides.add(iv);
		}
		System.out.println("guild_size=" + guides.size());

		curDot.getViewTreeObserver().addOnPreDrawListener(
				new OnPreDrawListener() {
					public boolean onPreDraw() {
						offset = curDot.getWidth();
						return true;
					}
				});

		final GuidePagerAdapter adapter = new GuidePagerAdapter(guides);
		pager.setAdapter(adapter);
		pager.clearAnimation();
		pager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				 intent = new Intent(Activity_FirstRunGuide.this,
						MainActivity.class);

				int pos = position % ids.length;

				moveCursorTo(pos);

				if (pos == ids.length - 1) {

				} else if (curPos == ids.length - 1) {
					handler.sendEmptyMessageDelayed(LEAVE_FROM_END, 100);
				}
				curPos = pos;
				super.onPageSelected(position);
			}
		});

	}

	private void getView() {
		dotContain = (LinearLayout) this.findViewById(R.id.dot_contain);
		pager = (ViewPager) findViewById(R.id.contentPager);
		curDot = (ImageView) findViewById(R.id.cur_dot);
		start = (ImageView) findViewById(R.id.open);
		start.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Intent intent = new Intent(Activity_FirstRunGuide.this,
						Activity_Welcome.class);
				startActivity(intent);
			}
		});
	}

	private boolean initDot() {

		if (ids.length > 0) {
			ImageView dotView;
			for (int i = 0; i < ids.length; i++) {
				dotView = new ImageView(this);
				dotView.setImageResource(R.drawable.guide_dot1_w);
				dotView.setLayoutParams(new LinearLayout.LayoutParams(
						ViewGroup.LayoutParams.WRAP_CONTENT,
						ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f));

				dotContain.addView(dotView);
			}
			return true;
		} else {
			return false;
		}
	}

	private void moveCursorTo(int position) {
		AnimationSet animationSet = new AnimationSet(true);
		TranslateAnimation tAnim = new TranslateAnimation(offset * curPos,
				offset * position, 0, 0);
		animationSet.addAnimation(tAnim);
		animationSet.setDuration(300);
		animationSet.setFillAfter(true);
		curDot.startAnimation(animationSet);
	}

	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			if (msg.what == TO_THE_END)
				start.setVisibility(View.VISIBLE);
			else if (msg.what == LEAVE_FROM_END)
				start.setVisibility(View.GONE);
			startActivity(intent);
			finish();
		}
	};

	class GuidePagerAdapter extends PagerAdapter {

		private List<View> views;

		public GuidePagerAdapter(List<View> views) {
			this.views = views;
		}

		public void destroyItem(View arg0, int arg1, Object arg2) {
			((ViewPager) arg0).removeView(views.get(arg1 % views.size()));
		}

		public void finishUpdate(View arg0) {
		}

		public int getCount() {
			return views.size() * 20;
		}

		public Object instantiateItem(View arg0, int arg1) {
			Log.e("tag", "instantiateItem = " + arg1);
			((ViewPager) arg0).addView(views.get(arg1 % views.size()), 0);
			return views.get(arg1 % views.size());
		}

		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == (arg1);
		}

		public void restoreState(Parcelable arg0, ClassLoader arg1) {

		}

		public Parcelable saveState() {
			return null;
		}

		public void startUpdate(View arg0) {

		}
	}
}