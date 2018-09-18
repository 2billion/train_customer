package com.train.train_manager.act.ruku_add;

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
import com.train.train_manager.core.NetCallback;

import java.io.IOException;
import java.lang.reflect.Type;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;

public class RukuDoAddActivity extends BaseActivity {

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
        setContentView(R.layout.ruku_add_do_add);
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
                BaseApplication.app.dm.inaAddBean.transId = s.toString();
            }
        });
        input2.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                input_del_2.setVisibility(TextUtils.isEmpty(input2.getText()) ? View.INVISIBLE : View.VISIBLE);
            }

            public void afterTextChanged(Editable s) {
                BaseApplication.app.dm.inaAddBean.bstPartNo = s.toString();
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
                do_add();
            }
        });

        btn_right.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                do_add();
            }
        });

        input1.setText("D0104A");
        input2.setText("37810095S");
        input3.setText("2");



    }

    private void do_add() {
        if (input1.getText().toString().equals("")) {
            BaseApplication.showToast("请填写库位编码");
            return;
        } else if (input2.getText().toString().equals("")) {
            BaseApplication.showToast("请填写BST物料编码");
            return;
        }

        hideKeyBoard(input1);

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
                    //                    runOnUiThread(new Runnable() {
                    //                        public void run() {
                    //                            updateUI();
                    //                        }
                    //                    });
                } else {
                    BaseApplication.app.showToast(bean.msg);
                }
            }
        });

    }

}