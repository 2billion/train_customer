package com.train.train_manager.base;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.honeywell.aidc.AidcManager;
import com.honeywell.aidc.BarcodeReader;
import com.honeywell.aidc.UnsupportedPropertyException;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.train.train_manager.R;
import com.train.train_manager.core.Cache;
import com.train.train_manager.core.DataManager;
import com.train.train_manager.core.Net;

import java.util.HashMap;
import java.util.Map;

public class BaseApplication extends Application {

    public Net net;
    public DataManager dm;
    private static Toast mToast = null;
    public static Activity curAct = null;
    public static BaseApplication app = null;
    public boolean goHome = false;

    public void setAutoLogin(boolean b) {
        Cache.i().setBoolean("autoLogin", b);
    }

    public boolean getAutoLogin() {
        return Cache.i().getBoolean("autoLogin", false);
    }

    public static boolean needClearReader = false;
//    public static BarcodeReader barcodeReader;
//    public AidcManager manager;

//    public BarcodeReader.BarcodeListener reader ;

//    public void initReader() {
//        Log.i("app","================ initReader()");
//
////        if (barcodeReader != null) {
////            return;
////        }
//        Log.i("app","================ initReader()  ok");
//        AidcManager.create(this, new AidcManager.CreatedCallback() {
//
//            @Override
//            public void onCreated(AidcManager aidcManager) {
//                manager = aidcManager;
//                barcodeReader = manager.createBarcodeReader();
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        doInitReader();
//                    }
//                }, 500);
//
//            }
//        });
//    }

//    public void clearReader() {
//        Log.i("app","================ clearReader()");
//        if (needClearReader == true) {
//            if (manager != null) {
//                // close AidcManager to disconnect from the scanner service.
//                // once closed, the object can no longer be used.
//                manager.close();
//            }
//            if (barcodeReader != null) {
//                // unregister barcode event listener
//                barcodeReader.removeBarcodeListener(reader);
//                // unregister trigger state change listener
//                //                barcodeReader.removeTriggerListener(this);
//                barcodeReader.release();
//                barcodeReader.close();
//                barcodeReader = null;
//            }
//
//            Log.i("app","================ clearReader()   ok");
//        }
//    }

//    public void doInitReader() {
//        // get bar code instance from MainActivity
//        //        barcodeReader = getBarcodeObject();
//        Log.i("app","================ doInitReader()");
//        if (barcodeReader != null) {
//
//            // register bar code event listener
//            barcodeReader.addBarcodeListener(reader);
//            BaseApplication.showToast("init ok");
//
//            // set the trigger mode to client control
//            try {
//                barcodeReader.setProperty(BarcodeReader.PROPERTY_TRIGGER_CONTROL_MODE,
//                        BarcodeReader.TRIGGER_CONTROL_MODE_CLIENT_CONTROL);
//            } catch (UnsupportedPropertyException e) {
//                Toast.makeText(this, "Failed to apply properties", Toast.LENGTH_SHORT).show();
//            }
            // register trigger state change listener
            //            barcodeReader.addTriggerListener(this);

//            Map<String, Object> properties = new HashMap<String, Object>();
//            // Set Symbologies On/Off
//            properties.put(BarcodeReader.PROPERTY_CODE_128_ENABLED, true);
//            properties.put(BarcodeReader.PROPERTY_GS1_128_ENABLED, true);
//            properties.put(BarcodeReader.PROPERTY_QR_CODE_ENABLED, true);
//            properties.put(BarcodeReader.PROPERTY_CODE_39_ENABLED, true);
//            properties.put(BarcodeReader.PROPERTY_DATAMATRIX_ENABLED, true);
//            properties.put(BarcodeReader.PROPERTY_UPC_A_ENABLE, true);
//            properties.put(BarcodeReader.PROPERTY_EAN_13_ENABLED, false);
//            properties.put(BarcodeReader.PROPERTY_AZTEC_ENABLED, false);
//            properties.put(BarcodeReader.PROPERTY_CODABAR_ENABLED, false);
//            properties.put(BarcodeReader.PROPERTY_INTERLEAVED_25_ENABLED, false);
//            properties.put(BarcodeReader.PROPERTY_PDF_417_ENABLED, false);
//            // Set Max Code 39 barcode length
//            properties.put(BarcodeReader.PROPERTY_CODE_39_MAXIMUM_LENGTH, 30);
//            // Turn on center decoding
//            properties.put(BarcodeReader.PROPERTY_CENTER_DECODE, true);
//            // Disable bad read response, handle in onFailureEvent
//            properties.put(BarcodeReader.PROPERTY_NOTIFICATION_BAD_READ_ENABLED, false);
//            // Apply the settings
//            barcodeReader.setProperties(properties);

//            Log.i("app","================ doInitReader()    ok");
//        }
//    }


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


    public void restart() {
        //        ActivityManager am = (ActivityManager) getApplicationContext().getSystemService(getApplicationContext().ACTIVITY_SERVICE);
        //        am.restartPackage("com.train.train_customer");
        BaseApplication.app.setAutoLogin(false);
        Intent k = getApplicationContext().getPackageManager()
                .getLaunchIntentForPackage("com.train.train_manager");
        k.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        getApplicationContext().startActivity(k);
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
        initImagePicker();
        // 捕捉异常
        //        AppUncaughtExceptionHandler crashHandler = AppUncaughtExceptionHandler.getInstance();
        //        crashHandler.init(getApplicationContext());

    }

    public void initImagePicker() {
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