package com.train.train_customer.act.order;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.train.train_customer.R;
import com.train.train_customer.act.bean.BaseBean;
import com.train.train_customer.act.bean.OrderBean;
import com.train.train_customer.act.bean.OrderChangeBean;
import com.train.train_customer.act.bean.OrderChangeListBean;
import com.train.train_customer.base.BaseActivity;
import com.train.train_customer.base.BaseApplication;
import com.train.train_customer.core.NetCallback;
import com.train.train_customer.view.AmountView;

import java.io.IOException;
import java.lang.reflect.Type;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;

public class OrderInfoActivity extends BaseActivity {

    @BindView(R.id.order_info_part_name)
    TextView part_name;

    @BindView(R.id.order_info_ts_type)
    TextView ts_type;

    @BindView(R.id.order_info_order_no)
    TextView order_no;

    @BindView(R.id.order_info_order_time)
    TextView order_time;

    @BindView(R.id.order_info_o_total_qty)
    TextView o_total_qty;

    @BindView(R.id.order_info_bst_part_no)
    TextView bst_part_no;

    @BindView(R.id.order_info_last_qty)
    TextView last_qty;

    @BindView(R.id.order_info_bu_part_no)
    TextView bu_part_no;

    @BindView(R.id.order_info_pick_qty)
    TextView pick_qty;

    @BindView(R.id.amount_view)
    AmountView amountView;

    @BindView(R.id.reason)
    EditText reason;

    @BindView(R.id.submit)
    Button submit;

    @BindView(R.id.list)
    LinearLayout list;

    @BindView(R.id.btn_left)
    View btn_left;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_info);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        OrderBean bean = BaseApplication.app.dm.orderInfoBean;
        part_name.setText("配件名：" + bean.partName);
        ts_type.setText("适应车型：" + bean.tsType + "(" + bean.contractNo + ")");
        order_no.setText("订单号：" + bean.orderNo);

        order_time.setText("订单时间：" + bean.orderTime);
        o_total_qty.setText("需求总数：" + (int) bean.oTotalQty);

        bst_part_no.setText("BST物资编号：" + bean.bstPartNo);
        last_qty.setText("剩余总数：" + (int) bean.lastQty);

        bu_part_no.setText("路局物资编码：" + bean.buPartNo);
        pick_qty.setText("可领总数：" + (int) bean.pickQty);

        submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                do_submit();
            }
        });
        amountView.setGoods_storage((int) bean.lastQty);
        amountView.setOnAmountChangeListener(new AmountView.OnAmountChangeListener() {
            public void onAmountChange(View view, int amount) {
                BaseApplication.app.dm.orderInfoBean.count = amount;
            }
        });

        btn_left.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });

        getOrderChangehistory();
    }

    private void do_submit() {
        String reason_str = reason.getText().toString();
        if (reason_str.isEmpty()) {
            BaseApplication.showToast("请填写变更原因");
            return;
        }

        String changeReason = reason_str;
        String detailId = BaseApplication.app.dm.orderInfoBean.detailId;
        String changeNum = BaseApplication.app.dm.orderInfoBean.count + "";

        BaseApplication.app.net.saveOrderChangeInfo(new NetCallback() {
            public void failure(Call call, IOException e) {
            }

            public void response(Call call, String responseStr) throws IOException {
                BaseBean bean = new BaseBean().onBack(responseStr);
                if (bean.isOK()) {
                    BaseApplication.app.showToast(bean.msg);
                    finish();
                } else {
                    BaseApplication.app.showToast("请求失败" + bean.msg);
                }
            }
        }, detailId, changeReason, changeNum);
    }

    private void getOrderChangehistory() {
        String detailId = BaseApplication.app.dm.orderInfoBean.detailId;
        BaseApplication.app.net.findOrderChangeByDetailId(new NetCallback() {
            public void failure(Call call, IOException e) {
            }

            public void response(Call call, String responseStr) throws IOException {
                Type cvbType = new TypeToken<OrderChangeListBean>() {
                }.getType();
                OrderChangeListBean bean = new Gson().fromJson(responseStr, cvbType);
                if (bean.isOK()) {
                    BaseApplication.app.showToast(bean.msg);
                    showOrderHistory(bean);
                } else {
                    BaseApplication.app.showToast("请求失败" + bean.msg);
                }
            }
        }, detailId);
    }

    private void showOrderHistory(final OrderChangeListBean bean) {
        this.runOnUiThread(new Runnable() {
            public void run() {
                for (final OrderChangeBean b : bean.data) {
                    String status = "待审核";
                    if (b.status == 1) {
                        status = "审核通过";
                    } else if (b.status == 2) {
                        status = "审核不通过";
                    }
                    TextView textView = (TextView) View.inflate(OrderInfoActivity.this, R.layout.order_change_list_item, null);
                    textView.setText(b.changeTime + "\n" + b.partName + " 数量变更：" + b.newTotalQty + ", 原因：" + b.changeReason + ", 状态：" + status);
                    list.addView(textView);
                }
            }
        });
    }
}
