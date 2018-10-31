package com.train.train_manager.act.ruku_one;

import android.os.Bundle;
import android.view.View;

import com.train.train_manager.R;
import com.train.train_manager.base.BaseActivity;
import com.train.train_manager.base.BaseApplication;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RukuOKActivity extends BaseActivity {


    @BindView(R.id.view1)
    View view1;

    @BindView(R.id.view2)
    View view2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ruku_ok);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        view1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
        view2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                BaseApplication.app.goHome = true;
                finish();
            }
        });

    }

}
