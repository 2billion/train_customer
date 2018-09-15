package com.train.train_manager.act.mine;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;
import com.train.train_manager.R;
import com.train.train_manager.act.bean.BaseBean;
import com.train.train_manager.act.bean.UserDataBean;
import com.train.train_manager.base.BaseActivity;
import com.train.train_manager.base.BaseApplication;
import com.train.train_manager.core.CircleTransform;
import com.train.train_manager.core.Net;
import com.train.train_manager.core.NetCallback;

import java.io.IOException;
import java.lang.reflect.Type;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;

/**
 * Created by solo on 2018/1/8.
 */

public class MineActivity extends BaseActivity {
    @BindView(R.id.login_name)
    TextView loginName;

    @BindView(R.id.nickname)
    TextView nickname;

    @BindView(R.id.info_view)
    View infoView;

    @BindView(R.id.change_psw_view)
    View changePswView;

    @BindView(R.id.submit)
    Button submit;

    @BindView(R.id.avatar)
    ImageView avatar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mine_main);
        ButterKnife.bind(this);
        //初始化
        init();
    }

    private void init() {

        infoView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(MineActivity.this, MineInfoActivity.class));
            }
        });
        changePswView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(MineActivity.this, MinePwdActivity.class));
            }
        });

        BaseApplication.app.net.info(new NetCallback() {
            public void failure(Call call, IOException e) {
            }

            public void response(Call call, String responseStr) throws IOException {
                Log.e("app", "====" + responseStr);
                Type cvbType = new TypeToken<UserDataBean>() {
                }.getType();
                UserDataBean bean = new Gson().fromJson(responseStr, cvbType);
                if (bean.isOK()) {
                    BaseApplication.app.dm.userBean = bean.data;
                    initUI();
                } else {
                    BaseApplication.app.showToast("请求失败" + bean.msg);
                }
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                restart();

            }
        });
    }

    public void restart() {
        BaseApplication.app.net.checkOut(new NetCallback() {
            public void failure(Call call, IOException e) {
            }

            public void response(Call call, String responseStr) throws IOException {
                Type cvbType = new TypeToken<BaseBean>() {
                }.getType();
                BaseBean bean = new Gson().fromJson(responseStr, cvbType);
                if (bean.isOK()) {
                    BaseApplication.app.restart();
                } else {
                    BaseApplication.app.showToast("请求失败" + bean.msg);
                }
            }
        });
    }

    public void initUI() {
        runOnUiThread(new Runnable() {
            public void run() {
                loginName.setText(BaseApplication.app.dm.userBean.userName);
                nickname.setText(BaseApplication.app.dm.userBean.userId);
                loadAvatar();
            }
        });
    }

    private void loadAvatar() {
        if (BaseApplication.app.dm.userBean.userHeader == null){
            return ;
        }
        String url = Net.HOST + BaseApplication.app.dm.userBean.userHeader;
        avatar.setPadding(0, 0, 0, 0);
        Picasso.get()
                .load(url)
                .resize(200, 200)
                .transform(new CircleTransform(MineActivity.this))
                .centerCrop()
                .into(avatar);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (BaseApplication.app.dm.userBean == null || BaseApplication.app.dm.userBean.userHeader == null) {
            return;
        }
        loadAvatar();
    }
}
