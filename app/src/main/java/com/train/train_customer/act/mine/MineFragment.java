package com.train.train_customer.act.mine;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;
import com.train.train_customer.R;
import com.train.train_customer.act.bean.BaseBean;
import com.train.train_customer.act.bean.UserDataBean;
import com.train.train_customer.base.BaseApplication;
import com.train.train_customer.base.BaseFragment;
import com.train.train_customer.core.CircleTransform;
import com.train.train_customer.core.Net;
import com.train.train_customer.core.NetCallback;

import java.io.IOException;
import java.lang.reflect.Type;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;

/**
 * Created by solo on 2018/1/8.
 */

public class MineFragment extends BaseFragment {
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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.mine_main, null);
        ButterKnife.bind(this, view);
        init();
        return view;
    }

    private void init() {

        infoView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), MineInfoActivity.class));
            }
        });
        changePswView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), MinePwdActivity.class));
            }
        });

        BaseApplication.app.net.findCustomerInfo(new NetCallback() {
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
        getActivity().runOnUiThread(new Runnable() {
            public void run() {
                try {
                    loginName.setText(BaseApplication.app.dm.userBean.userName);
                    nickname.setText(BaseApplication.app.dm.userBean.customerName);
                    loadAvatar();
                }catch(Exception e){}
            }
        });
    }

    private void loadAvatar() {
        String url = Net.HOST + BaseApplication.app.dm.userBean.customerImg;
        avatar.setPadding(0, 0, 0, 0);
        Picasso.get()
                .load(url)
                .resize(200, 200)
                .transform(new CircleTransform(getActivity()))
                .centerCrop()
                .into(avatar);
    }

    @Override
    public void onResume() {
        super.onResume();
        reload_list();
        if (BaseApplication.app.dm.userBean == null || BaseApplication.app.dm.userBean.customerImg == null) {
            return;
        }
        loadAvatar();

    }

    @Override
    public void reload_list() {
        if (BaseApplication.app.reload_mine) {
            BaseApplication.app.reload_mine = false;
            initUI();
        }

    }
}
