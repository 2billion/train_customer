package com.train.train_customer.core;

import android.content.Context;

import com.train.train_customer.act.bean.CartBean;
import com.train.train_customer.base.BaseApplication;

import org.json.JSONArray;

import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;

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

    //    1.1登录（用户名/密码）
    //    请求地址：/login/checkCustomerPwd
    public void login(Callback callBack, String name, String pwd) {
        String url = HOST + "/login/checkCustomerPwd";
        FormBody.Builder params = new FormBody.Builder();
        params.add("userName", name);
        params.add("password", pwd);
        Request request = new Request.Builder().post(params.build()).url(url).build();
        Call call = client.newCall(request);
        call.enqueue(callBack);
    }

    //    1.3登出
    //    请求地址：/login/checkout
    public void checkOut(Callback callBack) {
        String url = HOST + "/login/checkout";
        FormBody.Builder params = new FormBody.Builder();
        Request request = new Request.Builder().url(url).post(params.build()).build();
        Call call = client.newCall(request);
        call.enqueue(callBack);
    }

    //    1.6获取适用车型
    //    请求地址： /tsType/findTsTypeList
    public void findTsTypeList(Callback callBack) {
        String url = HOST + "/tsType/findTsTypeList";
        FormBody.Builder params = new FormBody.Builder();
        Request request = new Request.Builder().url(url).post(params.build())
                .addHeader("token", BaseApplication.app.dm.token).build();
        Call call = client.newCall(request);
        call.enqueue(callBack);
    }

    //     1.7获取配件列表
    //     请求地址： /part/findPartNumList
    //     partName":"","tsType":"","partNo":"","buPartNo":"","page":1,"pageSize":20
    public void getPartList(Callback callBack, int page, int pageSize, String partName, String tsType, String partNo, String buPartNo) {
        String url = HOST + "/part/findPartNumList";
        FormBody.Builder params = new FormBody.Builder();
        params.add("page", page + "");
        params.add("pageSize", pageSize + "");
        params.add("partName", partName);
        params.add("tsType", tsType);
        params.add("partNo", partNo);
        params.add("buPartNo", buPartNo);
        Request request = new Request.Builder().url(url).post(params.build())
                .addHeader("token", BaseApplication.app.dm.token).build();
        Call call = client.newCall(request);
        call.enqueue(callBack);
    }


    //     1.8获取购物车列表
    //     请求地址： /cart/findCartList
    //     "tsType":"","bstPartNo":"","buPartNo":""}
    public void getCartList(Callback callBack, String tsType, String bstPartNo, String buPartNo) {
        String url = HOST + "/cart/findCartList";
        FormBody.Builder params = new FormBody.Builder();
        params.add("tsType", tsType);
        params.add("bstPartNo", bstPartNo);
        params.add("buPartNo", buPartNo);
        Request request = new Request.Builder().url(url).post(params.build())
                .addHeader("token", BaseApplication.app.dm.token).build();
        Call call = client.newCall(request);
        call.enqueue(callBack);
    }


    //     1.9新增购物车
    //     请求地址： /cart/saveCartListInfo
    //     cartJson:    [
    //        {
    //            "bstPartNo": "63810647S",
    //            "buPartNo": "971900020001",
    //            "contractNo": "790",
    //            "tsType": "CRH1A-200",
    //            "qty": "1"
    //        }
    //    ]
    //    JSONObject json = new JSONObject();
    //        try {
    //        json.put("bstPartNo","63810647S");
    //        json.put("buPartNo","971900020001");
    //        json.put("contractNo","790");
    //        json.put("tsType","CRH1A-200");
    //        json.put("qty","1");
    //    } catch (JSONException e) {
    //        e.printStackTrace();
    //    }
    //    JSONArray jsonArray = new JSONArray();
    //        jsonArray.put(json);
    public void saveCartListInfo(Callback callBack, JSONArray jsonArray) {
        String url = HOST + "/cart/saveCartListInfo";
        FormBody.Builder params = new FormBody.Builder();
        //        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        //        okhttp3.RequestBody body = RequestBody.create(JSON, jsonArray.toString());
        params.add("cartJson", jsonArray.toString());
        Request request = new Request.Builder().url(url).post(params.build())
                .addHeader("token", BaseApplication.app.dm.token).build();
        Call call = client.newCall(request);
        call.enqueue(callBack);
    }

    //    {"bstPartNo":"37810095S","buPartNo":"972500010004","contractNo":"790","tsType":"CRH1A-200","qty":"21"}
    public void updateCartInfo(Callback callBack, CartBean bean) {
        String url = HOST + "/cart/updateCartInfo";
        FormBody.Builder params = new FormBody.Builder();
        JSONArray jsonArray = new JSONArray();
        jsonArray.put(bean.jsonForUpdateCard());
        params.add("cartJson", jsonArray.toString());
        Request request = new Request.Builder().url(url).post(params.build())
                .addHeader("token", BaseApplication.app.dm.token).build();
        Call call = client.newCall(request);
        call.enqueue(callBack);
    }

    //    {"bstPartNo":"37810095S","buPartNo":"972500010004","contractNo":"790","tsType":"CRH1A-200","qty":"21"}
    public void deleteCartInfo(Callback callBack, CartBean bean) {
        String url = HOST + "/cart/deleteCartInfo";
        FormBody.Builder params = new FormBody.Builder();
        JSONArray jsonArray = new JSONArray();
        jsonArray.put(bean.jsonForUpdateCard());
        params.add("cartJson", jsonArray.toString());
        Request request = new Request.Builder().url(url).post(params.build())
                .addHeader("token", BaseApplication.app.dm.token).build();
        Call call = client.newCall(request);
        call.enqueue(callBack);
    }


}
