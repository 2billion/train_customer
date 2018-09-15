package com.train.train_manager.act.mine;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.train.train_manager.R;
import com.train.train_manager.act.bean.BaseBean;
import com.train.train_manager.act.bean.UserBean;
import com.train.train_manager.base.BaseActivity;
import com.train.train_manager.base.BaseApplication;
import com.train.train_manager.core.NetCallback;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;

public class MineInfoUpdateActivity extends BaseActivity {

    @BindView(R.id.btn_left)
    View btn_left;

    @BindView(R.id.btn_right)
    View btn_right;

    @BindView(R.id.user_name)
    EditText user_name;

    @BindView(R.id.user_desc)
    EditText user_desc;

    @BindView(R.id.post)
    EditText post;

    @BindView(R.id.submit)
    View submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mine_info_update);
        ButterKnife.bind(this);
        init();
    }

    private void init() {

        btn_left.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });

        UserBean bean = BaseApplication.app.dm.userBean;
        if (bean != null) {
            if (bean.userName != null) {
                user_name.setText(bean.userName);
                user_name.setSelection(user_name.getText().toString().length());
            }
            if (bean.userDesc != null) {
                user_desc.setText(bean.userDesc);
            }
            if (bean.post != null) {
                post.setText(bean.post);
            }
        }
        submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                do_submit();
            }
        });
    }

    private void do_submit() {

        String user_name_str = user_name.getText().toString();
        String user_desc_str = user_desc.getText().toString();
        String post_str = post.getText().toString();

        BaseApplication.app.net.editpersonal(new NetCallback() {
            public void failure(Call call, IOException e) {
            }

            public void response(Call call, String responseStr) throws IOException {
                BaseBean bean = new BaseBean().onBack(responseStr);
                if (bean.isOK()) {
                    BaseApplication.app.showToast("保存成功");
                    finish();
                } else {
                    BaseApplication.app.showToast("请求失败" + bean.msg);
                }
            }
        }, user_name_str, user_desc_str, post_str);
    }


}
