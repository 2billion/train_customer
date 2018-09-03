package com.train.train_customer.core;

import android.content.Context;
import android.util.Log;

import com.train.train_customer.base.BaseApplication;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public abstract class NetCallback implements Callback {

    @Override
    public void onFailure(Call call, IOException e) {
        Log.i("api","=========url:"+call.request().url().toString());
        BaseApplication.app.showToast("请求失败:"+e.getMessage());
        this.failure(call,e) ;
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        String responseStr =  response.body().string();
        Log.i("api","=========url:"+call.request().url().toString());
        Log.i("api","=========back:"+responseStr);
        this.response(call,responseStr);
    }

    public abstract void failure(Call call, IOException e) ;

    public abstract void response(Call call, String responseStr) throws IOException;

}
