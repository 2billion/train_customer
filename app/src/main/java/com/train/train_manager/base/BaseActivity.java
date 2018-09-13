package com.train.train_manager.base;

import android.app.Activity;
import android.os.Bundle;

public abstract class BaseActivity extends Activity{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        ButterKnife.bind(this);
    }


    protected void onResume() {
        super.onResume();
        BaseApplication.app.initApp(this);
    }

}
