package com.train.train_customer.core;

import android.content.Context;
import android.util.Log;

import com.train.train_customer.base.BaseApplication;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Net {
    public static final String HOST = "http://115.28.136.235:8080/bstapi/";

    private OkHttpClient client;
    private Context ctx;

    public Net(Context ctx) {
        client = new OkHttpClient.Builder().readTimeout(30, TimeUnit.SECONDS).build();
        this.ctx = ctx;
    }

    public BaseApplication app() {
        return (BaseApplication) this.ctx.getApplicationContext();
    }

    public void login(Callback callBack, String name, String pwd) {
        String url = HOST + "/login/checkCustomerPwd?userName=" + name + "&password=" + pwd;
        Request request = new Request.Builder().url(url).get().build();
        Call call = client.newCall(request);
        call.enqueue(callBack);
    }

    public void checkOut(Callback callBack) {
        String url = HOST + "/login/checkout";
        Request request = new Request.Builder().url(url).get().build();
        Call call = client.newCall(request);
        call.enqueue(callBack);
    }

    //    partName":"","tsType":"","partNo":"","buPartNo":"","page":1,"pageSize":20
    public void getPartList(Callback callBack, int page, int pageSize, String partName, String tsType, String partNo, String buPartNo) {
        String url = HOST + "/part/findPartNumList?page=" + page + "&pageSize=" + pageSize + "&partName=" + partName + "&tsType=" + tsType + "&partNo=" + partNo + "&buPartNo=" + buPartNo;
        Request request = new Request.Builder().url(url).get()
                .addHeader("token", BaseApplication.app.dm.token).build();
        Call call = client.newCall(request);
        call.enqueue(callBack);
    }
}
