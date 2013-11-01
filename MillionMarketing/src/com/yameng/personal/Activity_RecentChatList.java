package com.yameng.personal;

import java.util.LinkedList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.google.gson.Gson;
import com.way.swipelistview.BaseSwipeListViewListener;
import com.way.swipelistview.SwipeListView;
import com.yameng.adapter.RecentAdapter;
import com.yameng.bean.RecentItem;
import com.yameng.bean.User;
import com.yameng.db.MessageDB;
import com.yameng.db.RecentDB;
import com.yameng.db.UserDB;
import com.yameng.utils.MyApplication;
import com.yameng.utils.SharePreferenceUtil;
import com.yamtz.millionmarketing.R;

/**
 * 在handler中更新接收到的消息
 * 
 * @author Administrator
 * 
 */
public class Activity_RecentChatList extends Activity {
	String TAG="Activity_RecentChatList";
	private LinkedList<RecentItem> mRecentDatas;
	private RecentAdapter mAdapter;
	private MyApplication mApplication;
	private SwipeListView mRecentListView;
	private UserDB mUserDB;
	private MessageDB mMsgDB;
	private RecentDB mRecentDB;
	private SharePreferenceUtil mSpUtil;
	private Gson mGson;
	private TextView mEmpty;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recentchatlist);

		initData();
		initView();
	}

	private void initView() {
		// TODO Auto-generated method stub
		mRecentListView = (SwipeListView) findViewById(R.id.recent_listview);
		mEmpty = (TextView) findViewById(R.id.empty);
		mRecentListView.setEmptyView(mEmpty);
		mRecentListView
				.setSwipeListViewListener(new BaseSwipeListViewListener() {
					@Override
					public void onOpened(int position, boolean toRight) {
					}

					@Override
					public void onClosed(int position, boolean fromRight) {
					}

					@Override
					public void onListChanged() {
					}

					@Override
					public void onMove(int position, float x) {
					}

					@Override
					public void onStartOpen(int position, int action,
							boolean right) {
						// L.d("swipe", String.format(
						// "onStartOpen %d - action %d", position, action));
					}

					@Override
					public void onStartClose(int position, boolean right) {
						// L.d("swipe",
						// String.format("onStartClose %d", position));
					}

					@Override
					public void onClickFrontView(int position) {
						// L.d("swipe",
						// String.format("onClickFrontView %d", position));
						// T.showShort(mApplication, "item onClickFrontView ="
						// + mAdapter.getItem(position));
						RecentItem item = (RecentItem) mAdapter
								.getItem(position);
						User u = new User(item.getUserId(), "", item.getName(),
								item.getHeadImg(), 0);
						mMsgDB.clearNewCount(item.getUserId());
						Intent toChatIntent = new Intent(Activity_RecentChatList.this,
								Activity_Chat.class);
						toChatIntent.putExtra("user", u);
						startActivity(toChatIntent);
					}

					@Override
					public void onClickBackView(int position) {
						mRecentListView.closeOpenedItems();// 关闭打开的项
					}

					@Override
					public void onDismiss(int[] reverseSortedPositions) {
						for (int position : reverseSortedPositions) {
							mAdapter.remove(position);
						}
						// mAdapter.notifyDataSetChanged();
					}
				});
		setListener();
	}

	private void setListener() {
		// TODO Auto-generated method stub
		
	}

	private void initData() {
		// TODO Auto-generated method stub
		mApplication = MyApplication.getInstance();
		mSpUtil = mApplication.getSpUtil();
		mGson = mApplication.getGson();
		mUserDB = mApplication.getUserDB();
		mMsgDB = mApplication.getMessageDB();
		mRecentDB = mApplication.getRecentDB();
	}
	private void initRecentData() {
		// TODO Auto-generated method stub
		mRecentDatas = mRecentDB.getRecentList();
		mAdapter = new RecentAdapter(this, mRecentDatas, mRecentListView);
		mRecentListView.setAdapter(mAdapter);

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		initRecentData();
	}
}