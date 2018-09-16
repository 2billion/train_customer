package com.train.train_manager.act.ruku;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.train.train_manager.R;
import com.train.train_manager.act.ruku_record.RukuRecordActivity;
import com.train.train_manager.base.BaseActivity;
import com.train.train_manager.base.BaseApplication;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class RukuOneActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ruku_one);
        ButterKnife.bind(this);
        init();
    }

    private void init() {

    }

}
