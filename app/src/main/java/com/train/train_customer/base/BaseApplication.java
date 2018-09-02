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
import com.train.train_customer.core.Net;
import com.train.train_customer.core.Cache;

public class BaseApplication extends Application {

    public Net net;
    private static Toast mToast = null;
    public static Activity curAct = null;

    public String token = null;
    public String userName = null;

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
        if (net == null) {
            net = new Net(this);
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

    /**
     * 显示信息
     */
    public static void showBtnMsg(String msgStr, String btnStr, DialogInterface.OnClickListener clickListener) {
        new AlertDialog.Builder(curAct).setMessage(msgStr)
                .setPositiveButton(btnStr, clickListener).show();
    }

    /**
     * 显示信息，点击确定后退出
     */
    public static void showMsgFinish(String msg) {
        new AlertDialog.Builder(curAct).setMessage(msg)
                .setNeutralButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        if (curAct != null) {
                            curAct.finish();
                        }
                    }
                }).show();
    }

    /**
     * 显示信息,自定义两个按钮
     *
     * @param msg       提示信息
     * @param clickStr1 按钮文本1
     * @param click1    按钮点击1
     * @param clickStr2 按钮文本2
     * @param click2    按钮点击2
     * @return
     */
    public static Toast showMsg(String msg, String clickStr1,
                                DialogInterface.OnClickListener click1, String clickStr2, DialogInterface.OnClickListener click2) {
        new AlertDialog.Builder(curAct).setMessage(msg)
                .setPositiveButton(clickStr1, click1)
                .setNeutralButton(clickStr2, click2).show();
        return null;
    }

}