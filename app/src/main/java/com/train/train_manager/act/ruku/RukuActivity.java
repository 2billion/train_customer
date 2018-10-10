package com.train.train_manager.act.ruku;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.train.train_manager.R;
import com.train.train_manager.act.ruku_add.RukuAddActivity;
import com.train.train_manager.base.BaseActivity;
import com.train.train_manager.base.BaseApplication;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RukuActivity extends BaseActivity {

    @BindView(R.id.btn_left)
    View btn_left;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ruku_main);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        btn_left.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });

    }

    @OnClick({R.id.line2, R.id.line1})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.line1:
                BaseApplication.app.dm.list_InAInfo.clear();
                startActivity(new Intent(RukuActivity.this, RukuAddActivity.class));
                break;
            case R.id.line2:
                BaseApplication.showToast("暂时不能选择二类入库单");
                break;
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (BaseApplication.app.goHome == true) {
            BaseApplication.app.goHome = false;
            finish();
        }
    }
}
