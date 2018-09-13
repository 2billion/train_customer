package com.train.train_manager.act.mine;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.train.train_manager.R;
import com.train.train_manager.act.bean.BaseBean;
import com.train.train_manager.base.BaseActivity;
import com.train.train_manager.base.BaseApplication;
import com.train.train_manager.core.NetCallback;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;

public class MinePwdActivity extends BaseActivity {

    @BindView(R.id.btn_left)
    View btn_left;

    @BindView(R.id.btn_right)
    View btn_right;

    @BindView(R.id.pwd_old)
    EditText pwd_old;

    @BindView(R.id.pwd_new)
    EditText pwd_new;

    @BindView(R.id.pwd_confirm)
    EditText pwd_confirm;

    @BindView(R.id.submit)
    Button submit;

    @BindView(R.id.show_psw)
    CheckBox show_psw;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mine_pwd);
        ButterKnife.bind(this);
        init();
    }

    private void init() {

        btn_left.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
        btn_right.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(MinePwdActivity.this, MineInfoUpdateActivity.class));
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                do_submit();
            }
        });

        show_psw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    pwd_old.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    pwd_new.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    pwd_confirm.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                }else{
                    pwd_old.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    pwd_new.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    pwd_confirm.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            }
        });

    }

    private void do_submit() {
        String pwd_old_str = pwd_old.getText().toString();
        String pwd_new_str = pwd_new.getText().toString();
        String pwd_confirm_str = pwd_confirm.getText().toString();

        if (pwd_old_str == null || pwd_old_str.equals("")) {
            BaseApplication.showToast("请输入旧密码");
            return;
        }
        if (pwd_new_str == null || pwd_new_str.equals("")) {
            BaseApplication.showToast("请输入新密码");
            return;
        }
        if (pwd_confirm_str == null || pwd_confirm_str.equals("")) {
            BaseApplication.showToast("请输入确认密码");
            return;
        }
        if (!pwd_confirm_str.equals(pwd_new_str)) {
            BaseApplication.showToast("新密码于确认密码不一致");
            return;
        }
        if (pwd_old_str.equals(pwd_new_str)) {
            BaseApplication.showToast("新密码与旧密码一致");
            return;
        }

        BaseApplication.app.net.updateCustomerPassword(new NetCallback() {
            public void failure(Call call, IOException e) {
            }

            public void response(Call call, String responseStr) throws IOException {
                BaseBean bean = new BaseBean().onBack(responseStr);
                if (bean.isOK()) {
                    BaseApplication.app.showToast(bean.msg);
                    BaseApplication.app.restart();
                } else {
                    BaseApplication.app.showToast("请求失败:" + bean.msg);
                }
            }
        }, pwd_old_str, pwd_old_str);
    }


}
