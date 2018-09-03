package com.train.train_customer.core;

import android.content.Context;

import com.train.train_customer.act.bean.PartBean;
import com.train.train_customer.act.bean.TsTypeDataBean;
import com.train.train_customer.base.BaseApplication;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class DataManager {

    //    login
    public String token = null;
    public String userName = null;

    //    配件列表
    //    part list
    public List<PartBean> partList = new ArrayList<>();

    //    搜索用
    //    ts type list
    public List<TsTypeDataBean> tsTypeList = new ArrayList();

}
