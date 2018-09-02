package com.train.train_customer.act.bean;

//    {"data":{"userName":"gzdcd","token":"5cb1cd6deb5e4b248c8c0b954e14d7df"},"code":200,"msg":"成功"}

import org.json.JSONException;
import org.json.JSONObject;

public class BaseBean {
    public int code = 0;
    public String msg = null;
    public JSONObject data = null;

    public BaseBean(String str) {
        JSONObject json = null;
        try {
            json = new JSONObject(str);
            this.code = json.optInt("code");
            this.msg = json.optString("msg");
            if (this.code == 200) {
                this.data = json.optJSONObject("data");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public boolean isOK() {
        return this.code == 200;
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