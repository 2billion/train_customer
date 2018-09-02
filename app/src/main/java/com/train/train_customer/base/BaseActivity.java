package com.train.train_customer.base;

import android.app.Activity;
import android.os.Bundle;

import com.train.train_customer.R;

import butterknife.ButterKnife;

public abstract class BaseActivity extends Activity{

    public BaseApplication app(){
        return (BaseApplication)getApplication();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        ButterKnife.bind(this);
    }


    protected void onResume() {
        super.onResume();
        app().initApp(this);
    }

}
