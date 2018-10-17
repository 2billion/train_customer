package com.train.train_manager.base;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.honeywell.aidc.BarcodeFailureEvent;
import com.honeywell.aidc.BarcodeReadEvent;
import com.honeywell.aidc.BarcodeReader;
import com.honeywell.aidc.ScannerUnavailableException;

public abstract class BaseActivity extends Activity {

    public Reader reader = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * 隐藏键盘
     */
    public void hideKeyBoard(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public void log(String str) {
        Log.e("app", "" + str);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        BaseApplication.app.initApp(this);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);            //竖屏
        if (BaseApplication.barcodeReader != null) {
            try {
                BaseApplication.barcodeReader.claim();
            } catch (ScannerUnavailableException e) {
                e.printStackTrace();
                BaseApplication.showToast("Scanner unavailable");
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        log("--------------------- reader is " + (this.reader==null? "null" : "present"));
    }
}
