package com.train.train_customer.core;

import java.util.HashMap;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * SharePreferences本地数据工具
 */
public class SharedPreferencesUtil {

	private static String sharedPreferencesInfo = "";
	private static Context myContext;
	private static SharedPreferences sharedPreferences;
	private static Editor spEditor;
	private static SharedPreferencesUtil self = new SharedPreferencesUtil();

	/**
	 * 初始化，一般在Application
	 */
	public static void init(Context context) {
		myContext = context;
		String pkName = context.getPackageName();
		sharedPreferencesInfo = "sharedpreferences." + pkName;
	}

	/**
	 * 获取单例
	 */
	public static SharedPreferencesUtil getInstance() {
		if (sharedPreferences == null && myContext != null) {
			sharedPreferences = myContext.getSharedPreferences(
					sharedPreferencesInfo, Context.MODE_PRIVATE);
			spEditor = sharedPreferences.edit();
		}
		return self;
	}

	/**
	 * 是否包含key
	 */
	public boolean isContainKey(String key) {
		return sharedPreferences.contains(key);
	}

	/**
	 * 获取String
	 */
	public String getString(String key) {
		return sharedPreferences.getString(key, "");
	}

	/**
	 * 获取String，必须传个默认值
	 */
	public String getString(String key, String defaultValue) {
		return sharedPreferences.getString(key, defaultValue);
	}

	@SuppressWarnings("unchecked")
	public HashMap<String, String> getAll() {
		return (HashMap<String, String>) sharedPreferences.getAll();
	}

	/**
	 * 保存String
	 */
	public boolean setString(String key, String value) {
		if (sharedPreferences.contains(key)) {
			spEditor.remove(key);
		}
		spEditor.putString(key, value);
		return spEditor.commit();
	}

	/**
	 * 删除某项
	 */
	public boolean clearItem(String key) {
		spEditor.remove(key);
		return spEditor.commit();
	}

	/**
	 * 设置bool值
	 */
	public boolean setBoolean(String key, boolean value) {
		spEditor.putBoolean(key, value);
		return spEditor.commit();
	}

	/**
	 * 获取bool值
	 */
	public boolean getBoolean(String key, boolean defaultValue) {
		return sharedPreferences.getBoolean(key, defaultValue);
	}

	/********************************************
	 * 设置是否是无图模式
	 */
	public void setNoImgMode(boolean noImg) {
		spEditor.putBoolean("app_no_img_mode", noImg);
		spEditor.commit();
	}

	/********************************************
	 * 无图模式
	 */
	public boolean getNoImgMode() {
		return sharedPreferences.getBoolean("app_no_img_mode", false);
	}

	/********************************************
	 * 保存缓存 WK_CACHE_
	 */
	public void saveCache(String key, String jsonStr) {
		setString("WK_CACHE" + key, jsonStr);
	}

	/********************************************
	 * 读取本地缓存
	 */
	public String getCache(String key) {
		return sharedPreferences.getString("WK_CACHE" + key, "");
	}
}
