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

import java.util.ArrayList;
import java.util.List;

public abstract class BaseActivity extends Activity  {

    public Reader reader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        BaseApplication.app.initReader();
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
//        BaseApplication.app.clearReader();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
//        BaseApplication.app.reader = this;
        BaseApplication.app.initApp(this);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);            //竖屏
    }

//    @Override
//    public void onBarcodeEvent(final BarcodeReadEvent event) {
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                // update UI to reflect the data
//                List<String> list = new ArrayList<String>();
//                list.add("Barcode data: " + event.getBarcodeData());
//                list.add("Character Set: " + event.getCharset());
//                list.add("Code ID: " + event.getCodeId());
//                list.add("AIM ID: " + event.getAimId());
//                list.add("Timestamp: " + event.getTimestamp());
//
//                log("=================onBarcodeEvent=====" + event.getBarcodeData());
//
//                if (reader != null) {
//                    reader.back(event.getBarcodeData());
//                }
//
//            }
//        });
//    }
//    @Override
//    public void onFailureEvent(BarcodeFailureEvent barcodeFailureEvent) {
//        log("=================onFailureEvent=====" + barcodeFailureEvent.toString());
//    }

}
