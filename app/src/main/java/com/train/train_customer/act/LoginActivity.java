package com.train.train_customer.act;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.train.train_customer.R;
import com.train.train_customer.act.bean.LoginBean;
import com.train.train_customer.base.BaseActivity;
import com.train.train_customer.base.BaseApplication;
import com.train.train_customer.core.NetCallback;

import java.io.IOException;
import java.lang.reflect.Type;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.name)
    EditText et_name;
    @BindView(R.id.password)
    EditText et_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        ButterKnife.bind(this);
        init();
    }

    private void init() {

        et_name.setText("gzdcd");
        et_password.setText("123456");
        et_name.setSelection(et_name.length());

        findViewById(R.id.btn_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
        autoLogin();
    }

    private void autoLogin() {
        if (BaseApplication.app.getAutoLogin()) {
            login();
        }
    }

    private void gotoMainAct() {
        Intent i = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(i);
        finish();
    }

    private void login() {
        String name = et_name.getText().toString();
        String password = et_password.getText().toString();
        BaseApplication.app.net.login(new NetCallback() {
            @Override
            public void failure(Call call, IOException e) {
            }

            @Override
            public void response(Call call, String responseStr) throws IOException {
                Type cvbType = new TypeToken<LoginBean>() {
                }.getType();
                LoginBean bean = new Gson().fromJson(responseStr, cvbType);
                if (bean.isOK()) {
                    BaseApplication.app.showToast("登录成功");
                    BaseApplication.app.setAutoLogin(true);
                    BaseApplication.app.dm.token = bean.data.token;
                    BaseApplication.app.dm.userName = bean.data.userName;
                    finish();
                    gotoMainAct();
                } else {
                    BaseApplication.app.showToast(bean.msg);
                }
            }
        }, name, password);
    }

}
