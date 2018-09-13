package com.train.train_manager.core;

import android.content.Context;
import android.content.SharedPreferences;

import com.train.train_manager.base.BaseApplication;

public class Cache {

    private static Cache self;

    private static String sharedPreferencesInfo = "";
    private static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor spEditor;
    private static boolean isInitOk = false;

    public static void init(Context ctx) {
        if (isInitOk == false) {
            String pkName = ctx.getPackageName();
            sharedPreferencesInfo = "cn.wk.sharedpreferences." + pkName;
            self = new Cache();
            sharedPreferences = ctx.getSharedPreferences(
                    sharedPreferencesInfo, Context.MODE_PRIVATE);
            spEditor = sharedPreferences.edit();
            isInitOk = true;
        }
    }

    /**
     * 获取单例
     */
    public static Cache i() {
        if (self == null) {
            self = new Cache();
            sharedPreferences = BaseApplication.curAct.getSharedPreferences(
                    sharedPreferencesInfo, Context.MODE_PRIVATE);
            spEditor = sharedPreferences.edit();
        }
        return self;
    }

    /**
     * 获取String
     */
    public String getString(String key) {
        return sharedPreferences.getString(key, "");
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
     * 是否包含key
     */
    public boolean isContainKey(String key) {
        return sharedPreferences.contains(key);
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


}
