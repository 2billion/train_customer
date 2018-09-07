package com.train.train_customer.core;

import android.util.Log;

import com.train.train_customer.base.BaseApplication;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public abstract class NetCallback implements Callback {

    @Override
    public void onFailure(Call call, IOException e) {
        Log.i("api", "=========url:" + call.request().url().toString());
        BaseApplication.app.showToast("请求失败:" + e.getMessage());
        BaseApplication.autoLogin = false;
        this.failure(call, e);
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        String responseStr = response.body().string();
        Log.i("api", "=========url:" + call.request().url().toString());
        Log.i("api", "=========back:" + responseStr);
        this.response(call, responseStr);
    }

    public abstract void failure(Call call, IOException e);

    public abstract void response(Call call, String responseStr) throws IOException;

}


// DEMO

//BaseApplication.app.net.getPartList(new NetCallback() {
//@Override
//public void failure(Call call, IOException e) {
//        BaseApplication.app.showToast("请求失败" + e.getMessage());
//        finishLoad();
//        }
//
//@Override
//public void response(Call call, String responseStr) throws IOException {
//        Type cvbType = new TypeToken<PartListBean>() {
//        }.getType();
//        PartListBean bean = new Gson().fromJson(responseStr, cvbType);
//        if (bean.isOK()) {
//        //                   刷新则删除列表
//        if (page == 1) {
//        BaseApplication.app.dm.productList.clear();
//        }
//        BaseApplication.app.dm.productList.addAll(bean.data.data);
//        getActivity().runOnUiThread(new Runnable() {
//public void run() {
//        adapter.notifyDataSetChanged();
//        }
//        });
//        } else {
//        BaseApplication.app.showToast("请求失败" + bean.msg);
//        }
//        finishLoad();
//        }
//        }, page, pageSize, partName, tsType, partNo, buPartNo);
