package com.train.train_manager.act.ruku;

import android.os.Bundle;
import android.widget.ListView;

import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.train.train_manager.R;
import com.train.train_manager.base.BaseActivity;

import butterknife.ButterKnife;

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
