package com.train.train_customer.act;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.train.train_customer.R;
import com.train.train_customer.act.bean.BaseBean;
import com.train.train_customer.act.bean.LoginBean;
import com.train.train_customer.base.BaseActivity;
import com.train.train_customer.base.BaseApplication;

import java.io.IOException;
import java.lang.reflect.Type;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

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
        et_name.setSelection(et_name.length());
        findViewById(R.id.btn_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

    }

    private void gotoMainAct() {
        Intent i = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(i);
        finish();
    }


    private void login() {
        String name = et_name.getText().toString();
        String password = et_password.getText().toString();
        BaseApplication.app.net.login(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                BaseApplication.app.showToast("请求失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String back = response.body().string();
                Type cvbType = new TypeToken<LoginBean>() {
                }.getType();
                LoginBean bean = new Gson().fromJson(back, cvbType);
                if (bean.isOK()) {
                    BaseApplication.app.showToast("登录成功");
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
