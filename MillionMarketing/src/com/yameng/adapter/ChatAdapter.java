package com.yameng.adapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.k.util.LocalPicToDrawable;
import com.yameng.bean.MessageInfo;
import com.yameng.bean.MessageItem;
import com.yameng.utils.ExpressionUtil;
import com.yameng.utils.MyApplication;
import com.yameng.utils.SharePreferenceUtil;
import com.yamtz.millionmarketing.R;

public class ChatAdapter extends BaseAdapter {

	private LayoutInflater mInflater;
//	private final ArrayList<MessageInfo> msgs;
	private Context context;
	private List<MessageItem> mMsgList;
	private SharePreferenceUtil mSpUtil;
	public ChatAdapter(Context context, List<MessageItem> mMsgList) {
//		public ChatAdapter(Context context, ArrayList<MessageInfo> arrayList) {
		this.context = context;
		mInflater = LayoutInflater.from(this.context);
		this.mMsgList = mMsgList;
		mSpUtil = MyApplication.getInstance().getSpUtil();
	}

	public void addMessage(MessageItem msg) {
		mMsgList.add(msg);
		notifyDataSetChanged();
	}

	public void addMessages(List<MessageItem> msgList) {
		mMsgList.addAll(msgList);
		notifyDataSetChanged();
	}

	public void clear() {
		mMsgList.clear();
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return mMsgList.size();
	}

	@Override
	public Object getItem(int position) {
		return mMsgList.get(position);
	}
/*	public ChatAdapter(Context context, ArrayList<MessageInfo> arrayList) {
//		public ChatAdapter(Context context, ArrayList<MessageInfo> arrayList) {
		this.context = context;
		mInflater = LayoutInflater.from(this.context);
		msgs = arrayList;
		mSpUtil = MyApplication.getInstance().getSpUtil();
	}
	
	public void addMessage(MessageInfo msg) {
		msgs.add(msg);
		notifyDataSetChanged();
	}
	
	public void addMessages(List<MessageInfo> msgList) {
		msgs.addAll(msgList);
		notifyDataSetChanged();
	}
	
	public void clear() {
		msgs.clear();
		notifyDataSetChanged();
	}
	
	@Override
	public int getCount() {
		return msgs.size();
	}
	
	@Override
	public Object getItem(int position) {
		return msgs.get(position);
	}
*/
	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		MessageItem msgInfo = (MessageItem) getItem(position);
		if (convertView == null) {
			//单条item
			convertView = mInflater.inflate(R.layout.chat_item, null);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.setData(msgInfo);
		return convertView;
	}

	 class ViewHolder {
		RelativeLayout rl_chatitem;
		ImageView image;
		TextView text;
		View ll_wraptv;
		public ViewHolder(View convertView) {
			rl_chatitem = (RelativeLayout) convertView
					.findViewById(R.id.listitem);
			image = (ImageView) convertView.findViewById(R.id.headicon);
			text = (TextView) convertView.findViewById(R.id.message);
			ll_wraptv=(LinearLayout)convertView.findViewById(R.id.ll_wraptv);
		}

		public void setData(MessageItem msg) {
			RelativeLayout.LayoutParams rl_chat_left = ((RelativeLayout.LayoutParams) rl_chatitem
					.getLayoutParams());
/*			RelativeLayout.LayoutParams rl_tv_msg_left = ((RelativeLayout.LayoutParams) text
					.getLayoutParams());*/
			RelativeLayout.LayoutParams rl_tv_msg_left = ((RelativeLayout.LayoutParams) ll_wraptv
					.getLayoutParams());
			RelativeLayout.LayoutParams rl_iv_headicon_left = ((RelativeLayout.LayoutParams) image
					.getLayoutParams());
			System.out.println("ChatAdapter————msg.isComMeg()："+msg.isComMeg());
			if (!msg.isComMeg()) {
				//右边自己的
				ll_wraptv.setBackgroundDrawable(context.getResources().getDrawable(
						R.drawable.chatto_bg_normal));
				//用代码修改xml的布局
				rl_chat_left.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, -1);
				rl_chat_left.addRule(RelativeLayout.ALIGN_PARENT_LEFT, 0);
				rl_iv_headicon_left.addRule(RelativeLayout.ALIGN_PARENT_RIGHT,
						-1);
				rl_iv_headicon_left
						.addRule(RelativeLayout.ALIGN_PARENT_LEFT, 0);
				rl_tv_msg_left.addRule(RelativeLayout.LEFT_OF, R.id.headicon);
				rl_tv_msg_left.addRule(RelativeLayout.RIGHT_OF, 0);
				try {
					image.setImageDrawable(LocalPicToDrawable.getImageDrawable(MyApplication.path_portrait));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					image.setImageResource(R.drawable.boy);
				}
//				image.setImageResource(R.drawable.xiaohei);
			} else {
				ll_wraptv.setBackgroundDrawable(context.getResources().getDrawable(
						R.drawable.chatfrom_bg_normal));

				rl_chat_left.addRule(RelativeLayout.ALIGN_PARENT_LEFT, -1);
				rl_chat_left.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, 0);
				rl_iv_headicon_left.addRule(RelativeLayout.ALIGN_PARENT_LEFT,
						-1);
				rl_iv_headicon_left.addRule(RelativeLayout.ALIGN_PARENT_RIGHT,
						0);
				rl_tv_msg_left.addRule(RelativeLayout.RIGHT_OF, R.id.headicon);
				rl_tv_msg_left.addRule(RelativeLayout.LEFT_OF, 0);
				image.setImageResource(R.drawable.boy);
			}

//			image.setImageResource(R.drawable.xiaohei);
//			image.setImageResource(R.drawable.boy);
			String str = msg.getMessage();
			String zhengze = "f0[0-9]{2}|f10[0-7]";
			try {
				SpannableString spannableString = ExpressionUtil
						.getExpressionString(context, str, zhengze);
				text.setText(spannableString);
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			}
		}
	}

}
