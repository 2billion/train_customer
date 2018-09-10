package com.train.train_customer.act.mine;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.train.train_customer.R;
import com.train.train_customer.act.bean.UserBean;
import com.train.train_customer.base.BaseActivity;
import com.train.train_customer.base.BaseApplication;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MineInfoUpdateActivity extends BaseActivity {

    @BindView(R.id.btn_left)
    View btn_left;

    @BindView(R.id.btn_right)
    View btn_right;

    @BindView(R.id.customer_name)
    EditText customer_name;

    @BindView(R.id.customer_sex)
    EditText customerSex;

    @BindView(R.id.customer_tel)
    EditText customer_tel;

    @BindView(R.id.customer_mail)
    EditText customer_mail;

    @BindView(R.id.customer_addr)
    EditText customer_addr;


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
        btn_right.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                do_submit();
            }
        });
        UserBean bean = BaseApplication.app.dm.userBean;
        if (bean != null) {
            if (bean.customerName != null) {
                customer_name.setText(bean.customerName);
                customer_name.setSelection(customer_name.getText().toString().length());
            }
            if (bean.customerSex > -1) {
                customerSex.setText(bean.customerSex == 0 ? "男" : "女");
                customerSex.setSelection(customerSex.getText().toString().length());
            }
            if (bean.customerTel != null) {
                customer_tel.setText(bean.customerTel);
            }
            if (bean.customerMail != null) {
                customer_mail.setText(bean.customerMail);
            }
            if (bean.customerDes != null) {
                customer_addr.setText(bean.customerAddr);
            }
        }
        customer_name.setEnabled(false);
        customerSex.setEnabled(false);
        customer_tel.setEnabled(false);
        customer_mail.setEnabled(false);
        customer_addr.setEnabled(false);

    }

    private void do_submit() {
        //        String

        //        String reason_str = reason.getText().toString();
        //        if (reason_str.isEmpty()) {
        //            BaseApplication.showToast("请填写变更原因");
        //            return;
        //        }

        //        String customerName = user_name.getText().toString();
        //        String customerSex = customer_name.getText().toString();
        //        String customerTel = user_name.getText().toString();
        //        String customerMail = user_name.getText().toString();
        //        String customerAddr = user_name.getText().toString();
        //
        //
        //        BaseApplication.app.net.updateCustomerInfo(new NetCallback() {
        //            public void failure(Call call, IOException e) {
        //            }
        //
        //            public void response(Call call, String responseStr) throws IOException {
        //                BaseBean bean = new BaseBean().onBack(responseStr);
        //                if (bean.isOK()) {
        //                    BaseApplication.app.showToast(bean.msg);
        //                } else {
        //                    BaseApplication.app.showToast("请求失败" + bean.msg);
        //                }
        //            }
        //        }, customerName, customerSex, customerTel, customerMail, customerAddr);
    }


}
