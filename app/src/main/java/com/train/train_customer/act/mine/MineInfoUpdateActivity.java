package com.train.train_customer.act.mine;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;

import com.train.train_customer.R;
import com.train.train_customer.act.bean.BaseBean;
import com.train.train_customer.act.bean.UserBean;
import com.train.train_customer.base.BaseActivity;
import com.train.train_customer.base.BaseApplication;
import com.train.train_customer.core.NetCallback;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;

public class MineInfoUpdateActivity extends BaseActivity {

    @BindView(R.id.btn_left)
    View btn_left;

    @BindView(R.id.btn_right)
    View btn_right;

    @BindView(R.id.customer_name)
    EditText customer_name;

    @BindView(R.id.customer_tel)
    EditText customer_tel;

    @BindView(R.id.customer_mail)
    EditText customer_mail;

    @BindView(R.id.customer_addr)
    EditText customer_addr;

    @BindView(R.id.submit)
    View submit;

    @BindView(R.id.boy)
    RadioButton boy;

    @BindView(R.id.girl)
    RadioButton girl;


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
                customer_name.setText(bean.userName);
                customer_name.setSelection(customer_name.getText().toString().length());
            }
//            if (bean.customerSex > -1) {
//                boy.setSelected(bean.customerSex == 0);
//                girl.setSelected(bean.customerSex != 0);
//            }
//            if (bean.customerTel != null) {
//                customer_tel.setText(bean.customerTel);
//            }
//            if (bean.customerMail != null) {
//                customer_mail.setText(bean.customerMail);
//            }
//            if (bean.customerDes != null) {
//                customer_addr.setText(bean.customerAddr);
//            }
        }
        submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                do_submit();
            }
        });
    }

    private void do_submit() {

        String customerName = customer_name.getText().toString();
        int customerSex = boy.isSelected() ? 1 : 0;
        String customerTel = customer_tel.getText().toString();
        String customerMail = customer_mail.getText().toString();
        String customerAddr = customer_addr.getText().toString();

        //
        BaseApplication.app.net.updateCustomerInfo(new NetCallback() {
            public void failure(Call call, IOException e) {
            }

            public void response(Call call, String responseStr) throws IOException {
                BaseBean bean = new BaseBean().onBack(responseStr);
                if (bean.isOK()) {
                    BaseApplication.app.showToast("修改成功");
                    BaseApplication.app.reload_mine = true;
                    finish();
                } else {
                    BaseApplication.app.showToast("请求失败" + bean.msg);
                }
            }
        }, customerName, "" + customerSex, customerTel, customerMail, customerAddr);
    }


}
