package com.train.train_manager.act.ruku;

import android.os.Bundle;

import com.train.train_manager.R;
import com.train.train_manager.base.BaseActivity;

import butterknife.ButterKnife;

public class RukuOKActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ruku_ok);
        ButterKnife.bind(this);
        init();
    }

    private void init() {

    }

}
