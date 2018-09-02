package com.train.train_customer.act;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.train.train_customer.R;
import com.train.train_customer.act.bean.BaseBean;
import com.train.train_customer.act.bean.LoginBean;
import com.train.train_customer.base.BaseActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

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
        app().net.login(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                app().showToast("请求失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String back = response.body().string();
                LoginBean bean = new LoginBean(back);
                if (bean.isOK()) {
                    app().showToast(bean.userName + "登录成功");
                    finish();
                    gotoMainAct();
                } else {
                    app().showToast("登录失败，请检查账号密码");
                }
            }
        }, name, password);
    }

}
