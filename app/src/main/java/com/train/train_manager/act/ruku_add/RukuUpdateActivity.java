package com.train.train_manager.act.ruku_add;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.train.train_manager.R;
import com.train.train_manager.act.bean.InAAddBackListBean;
import com.train.train_manager.base.BaseActivity;
import com.train.train_manager.base.BaseApplication;
import com.train.train_manager.core.NetCallback;

import java.io.IOException;
import java.lang.reflect.Type;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;

public class RukuUpdateActivity extends BaseActivity {

    @BindView(R.id.btn_left)
    View btn_left;

    @BindView(R.id.btn_right)
    View btn_right;

    @BindView(R.id.btn_add)
    View btn_add;

    @BindView(R.id.input_3)
    EditText input3;

    @BindView(R.id.input_del_3)
    View input_del_3;

    @BindView(R.id.lable1)
    TextView lable1;

    @BindView(R.id.lable2)
    TextView lable2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ruku_add_update);
        ButterKnife.bind(this);
        init();
    }

    public void init() {
        btn_left.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
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
                    BaseApplication.app.dm.inaAddBean.qty = 0;
                } else {
                    BaseApplication.app.dm.inaAddBean.qty = Integer.valueOf(s.toString());
                }
            }
        });

        input_del_3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                input3.setText("");
            }
        });

        btn_add.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                do_add();
            }
        });

        btn_right.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                do_add();
            }
        });

        lable1.setText("库位编码：" + BaseApplication.app.dm.inaAddBean.tLocation);
        lable2.setText("BST物料编码：" + BaseApplication.app.dm.inaAddBean.bstPartNo);
        input3.setText("" + BaseApplication.app.dm.inaAddBean.qty);
    }

    private void do_add() {

        hideKeyBoard(input3);

        BaseApplication.app.net.saveInA(new NetCallback() {
            @Override
            public void failure(Call call, IOException e) {
            }

            @Override
            public void response(Call call, String responseStr) throws IOException {
                Type cvbType = new TypeToken<InAAddBackListBean>() {
                }.getType();
                InAAddBackListBean bean = new Gson().fromJson(responseStr, cvbType);
                if (bean.isOK()) {
                    BaseApplication.app.showToast("添加成功");
                    BaseApplication.app.dm.inaAddBean.transNo = bean.data.transNo;
                    BaseApplication.app.dm.inaAddBean.transId = bean.data.transId;

                } else {
                    BaseApplication.app.showToast(bean.msg);
                }
            }
        });

    }

}