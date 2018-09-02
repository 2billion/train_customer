package com.train.train_customer.act.bean;

//    {"data":{"userName":"gzdcd","token":"5cb1cd6deb5e4b248c8c0b954e14d7df"},"code":200,"msg":"成功"}

public class LoginBean extends BaseBean {

    public String userName = null;
    public String token = null;

    public LoginBean(String str) {
        super(str);
        if (this.data != null) {
            this.userName = this.data.optString("userName");
            this.token = this.data.optString("token");
        }
    }

}

