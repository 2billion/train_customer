package com.train.train_manager.act.chuku_action;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.train.train_manager.R;
import com.train.train_manager.act.bean.InAAddBackListBean;
import com.train.train_manager.base.BaseActivity;
import com.train.train_manager.base.BaseApplication;
import com.train.train_manager.base.Reader;
import com.train.train_manager.core.NetCallback;

import java.io.IOException;
import java.lang.reflect.Type;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;

public class ChukuActionActivity extends BaseActivity {

    @BindView(R.id.btn_left)
    View btn_left;

    @BindView(R.id.btn_right)
    View btn_right;

    @BindView(R.id.btn_add)
    View btn_add;

    @BindView(R.id.input_1)
    EditText input1;
    @BindView(R.id.input_2)
    EditText input2;
    @BindView(R.id.input_3)
    EditText input3;

    @BindView(R.id.input_del_1)
    View input_del_1;
    @BindView(R.id.input_del_2)
    View input_del_2;
    @BindView(R.id.input_del_3)
    View input_del_3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chuku_action);
        ButterKnife.bind(this);
        init();
    }

    public void init() {
        btn_left.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
        input1.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                input_del_1.setVisibility(TextUtils.isEmpty(input1.getText()) ? View.INVISIBLE : View.VISIBLE);
            }

            public void afterTextChanged(Editable s) {
                BaseApplication.app.dm.outDetailBean.location = s.toString();
            }
        });
        input2.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                input_del_2.setVisibility(TextUtils.isEmpty(input2.getText()) ? View.INVISIBLE : View.VISIBLE);
            }

            public void afterTextChanged(Editable s) {
                BaseApplication.app.dm.outDetailBean.bstPartNo = s.toString();
            }
        });
        input3.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                input_del_3.setVisibility(TextUtils.isEmpty(input3.getText()) ? View.INVISIBLE : View.VISIBLE);
            }

            public void afterTextChanged(Editable s) {
                if (s.toString().equals("")) {
                    BaseApplication.app.dm.outDetailBean.requireQty = "0";
                } else {
                    BaseApplication.app.dm.outDetailBean.requireQty = s.toString();
                }
            }
        });

        input_del_1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                input1.setText("");
            }
        });
        input_del_2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                input2.setText("");
            }
        });
        input_del_3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                input3.setText("");
            }
        });

        btn_add.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                do_out();
            }
        });

        btn_right.setVisibility(View.INVISIBLE);
        btn_right.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                do_out();
            }
        });

        //        input1.setText("D0104A");
        //        input2.setText("37810095S");
        //        input3.setText("2");
        //
        //        BaseApplication.app.dm.inaAddBean.tLocation = input1.getText().toString();
        //        BaseApplication.app.dm.inaAddBean.bstPartNo = input2.getText().toString();
        //        BaseApplication.app.dm.inaAddBean.qty = Integer.valueOf(input3.getText().toString());

        input1.setText(BaseApplication.app.dm.outDetailBean.location);
        input2.setText(BaseApplication.app.dm.outDetailBean.bstPartNo);
        input3.setText(BaseApplication.app.dm.outDetailBean.requireQty);
        this.reader = new Reader() {
            @Override
            public void back(String code) {
                on_back(code);
            }
        };
    }

    private void do_out() {
        if (input1.getText().toString().equals("")) {
            BaseApplication.showToast("请填写库位编码");
            return;
        } else if (input2.getText().toString().equals("")) {
            BaseApplication.showToast("请填写BST物料编码");
            return;
        }
        hideKeyBoard(input1);

        String pickId = BaseApplication.app.dm.outDetailBean.pickId + "";
        String location = BaseApplication.app.dm.outDetailBean.location;
        String bstPartNo = BaseApplication.app.dm.outDetailBean.bstPartNo;
        String requireQty = BaseApplication.app.dm.outDetailBean.requireQty;

        BaseApplication.app.net.pick_onpick(new NetCallback() {
            @Override
            public void failure(Call call, IOException e) {
            }

            @Override
            public void response(Call call, String responseStr) throws IOException {
                Type cvbType = new TypeToken<InAAddBackListBean>() {
                }.getType();
                InAAddBackListBean bean = new Gson().fromJson(responseStr, cvbType);
                if (bean.isOK()) {
                    finish();
                    BaseApplication.app.showToast("出库成功");
                } else {
                    BaseApplication.app.showToast(bean.msg);
                }
            }
        }, pickId, bstPartNo, location, requireQty);

    }

    public void on_back(final String str) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (str.equals("")) {
                    return;
                } else if (str.startsWith("#")) {
                    String str_real = str.replace("#", "");
                    BaseApplication.app.dm.outDetailBean.location = str_real;
                    input1.setText(str_real.toString());
                } else {
                    BaseApplication.app.dm.outDetailBean.bstPartNo = str;
                    input2.setText(str.toString());
                }

            }
        });
    }

}