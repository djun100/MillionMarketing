package com.yameng.utils;


import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

public class SharePreferenceUtil {
	public static final String MESSAGE_SOUND_KEY = "message_sound";
	static SharePreferenceUtil spUtil;
	private SharedPreferences sp;
	private SharedPreferences.Editor editor;
	String user;
	String password;

	public String getPassword() {
		return sp.getString("password","");
	}
	public void setPassword(String password) {
		editor.putString("password", password);
		editor.commit();
	}
	public String getUser() {
		return sp.getString("user","");
	}
	public void setUser(String user) {
		editor.putString("user", user);
		editor.commit();
	}
	public SharePreferenceUtil(Context context, String file) {
		sp = context.getSharedPreferences(file, Context.MODE_PRIVATE);
		editor = sp.edit();
	}
	public void setSavePwdState(boolean state) {
		// TODO Auto-generated method stub
		editor.putBoolean("savepwdstate", state);
		editor.commit();
	}
	public  boolean getSavePwdState() {
		return sp.getBoolean("savepwdstate", false);
	}
	//eg:
	// appid 
	public void setAppId(String appid) {
		// TODO Auto-generated method stub
		editor.putString("appid", appid);
		editor.commit();
	}

	public String getAppId() {
		return sp.getString("appid", "");
	}
	// user_id
	public void setUserId(String userId) {
		editor.putString("userId", userId);
		editor.commit();
	}

	public String getUserId() {
		return sp.getString("userId", "");
	}

	// channel_id
	public void setChannelId(String ChannelId) {
		editor.putString("ChannelId", ChannelId);
		editor.commit();
	}

	public String getChannelId() {
		return sp.getString("ChannelId", "");
	}

	// nick
	public void setNick(String nick) {
		editor.putString("nick", nick);
		editor.commit();
	}

	public String getNick() {
		return sp.getString("nick", "");
	}

	// 头像图标
	public int getHeadIcon() {
		return sp.getInt("headIcon", 0);
	}

	public void setHeadIcon(int icon) {
		editor.putInt("headIcon", icon);
		editor.commit();
	}
	// 新消息是否有声音
	public boolean getMsgSound() {
		return sp.getBoolean(MESSAGE_SOUND_KEY, true);
	}

	public void setMsgSound(boolean isChecked) {
		editor.putBoolean(MESSAGE_SOUND_KEY, isChecked);
		editor.commit();
	}
}
