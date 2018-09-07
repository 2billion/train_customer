package com.train.train_customer.act.bean;

//    {"data":{"userName":"gzdcd","token":"5cb1cd6deb5e4b248c8c0b954e14d7df"},"code":200,"msg":"成功"}

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.train.train_customer.base.BaseApplication;

public class BaseBean {

    //    public String jsoStr = null;
    //    public JSONObject jsonObj = null;

    public int code = 0;
    public String msg = null;

    //    public BaseBean(String str) {
    //        this.jsoStr = str;
    //    }

    public boolean isOK() {
        BaseApplication.autoLogin = this.code == 200;
        return this.code == 200;
    }

    public BaseBean onBack(String responseStr) {
        return new Gson().fromJson(responseStr, new TypeToken<BaseBean>() {
        }.getType());
    }
}

//消息编码	消息信息
//200	成功
//300	系统错误
//301	参数错误
//302	无权限
//401	用户名或密码错误
//402	TOKEN为空
//403	TOKEN失效