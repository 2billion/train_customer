package com.train.train_customer.act.mine;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.train.train_customer.R;
import com.train.train_customer.act.bean.UserBean;
import com.train.train_customer.base.BaseActivity;
import com.train.train_customer.base.BaseApplication;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MineInfoActivity extends BaseActivity {

    @BindView(R.id.btn_left)
    View btn_left;

    @BindView(R.id.btn_right)
    View btn_right;

    @BindView(R.id.user_name)
    TextView user_name;

    @BindView(R.id.customer_name)
    TextView customer_name;

    @BindView(R.id.customer_des)
    TextView customer_des;

    @BindView(R.id.customer_tel)
    TextView customer_tel;

    @BindView(R.id.customer_mail)
    TextView customer_mail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mine_info);
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
                BaseApplication.showToast("right");
            }
        });
        UserBean bean = BaseApplication.app.dm.userBean;
        if (bean != null) {
            if (bean.userName != null) {
                user_name.setText(bean.userName);
            }
            if (bean.customerName != null) {
                customer_name.setText(bean.customerName);
            }
            if (bean.customerDes != null) {
                customer_des.setText(bean.customerDes);
            }
            if (bean.customerTel != null) {
                customer_tel.setText(bean.customerTel);
            }
            if (bean.customerMail != null) {
                customer_mail.setText(bean.customerMail);
            }
        }
    }

    //    private void do_submit() {
    //        String reason_str = reason.getText().toString();
    //        if (reason_str.isEmpty()) {
    //            BaseApplication.showToast("请填写变更原因");
    //            return;
    //        }
    //
    //        String changeReason = reason_str;
    //        String detailId = BaseApplication.app.dm.orderInfoBean.detailId;
    //        String changeNum = BaseApplication.app.dm.orderInfoBean.count + "";
    //
    //        BaseApplication.app.net.saveOrderChangeInfo(new NetCallback() {
    //            public void failure(Call call, IOException e) {
    //            }
    //
    //            public void response(Call call, String responseStr) throws IOException {
    //                BaseBean bean = new BaseBean().onBack(responseStr);
    //                if (bean.isOK()) {
    //                    BaseApplication.app.showToast(bean.msg);
    //                    finish();
    //                } else {
    //                    BaseApplication.app.showToast("请求失败" + beanm.msg);
    //                }
    //            }
    //        }, detailId, changeReason, changeNum);
    //    }


}
