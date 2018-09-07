package com.train.train_customer.base;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.widget.Toast;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.train.train_customer.R;
import com.train.train_customer.core.Cache;
import com.train.train_customer.core.DataManager;
import com.train.train_customer.core.Net;

public class BaseApplication extends Application {

    public Net net;
    public DataManager dm;
    private static Toast mToast = null;
    public static Activity curAct = null;
    public static BaseApplication app = null;
    public static boolean autoLogin = true;

    //static 代码段可以防止内存泄露
    static {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                layout.setPrimaryColorsId(R.color.colorPrimary, android.R.color.white);//全局设置主题颜色
                return new ClassicsHeader(context);//.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
            }
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                //指定为经典Footer，默认是 BallPulseFooter
                return new ClassicsFooter(context).setDrawableSize(20);
            }
        });
    }

    /**
     * Activity基类方法，都过来签到！
     */
    public void initApp(Activity act) {
        curAct = act;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        BaseApplication.app = this;
        if (net == null) {
            net = new Net(this);
        }
        if (dm == null) {
            dm = new DataManager();
        }
        Cache.init(getApplicationContext());
    }

    public void info() {
        Log.i("app", "app info...");
    }

    /**
     * 显示信息
     */
    public static void showToast(final String msg) {
        curAct.runOnUiThread(new Runnable() {
            public void run() {
                try {
                    if (mToast != null) {
                        mToast.setText(msg);
                    } else {
                        mToast = Toast.makeText(curAct, msg, Toast.LENGTH_SHORT);
                    }
                    mToast.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

}