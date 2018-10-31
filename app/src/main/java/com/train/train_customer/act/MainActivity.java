package com.train.train_customer.act;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;

import com.train.train_customer.R;
import com.train.train_customer.act.cart.CartFragment;
import com.train.train_customer.act.mine.MineFragment;
import com.train.train_customer.act.order.OrderFragment;
import com.train.train_customer.act.product.ProductFragment;
import com.train.train_customer.base.BaseApplication;
import com.train.train_customer.base.BaseFragment;
import com.train.train_customer.view.CustomBottomTabWidget;

import java.util.ArrayList;
import java.util.List;

//AppCompatActivity
public class MainActivity extends AppCompatActivity {

    CustomBottomTabWidget tabWidget;
    private List<BaseFragment> fragmentList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        //初始化
        init();
        BaseApplication.app.initApp(this);
    }

    private void init() {
        tabWidget = findViewById(R.id.tabWidget);

        //构造Fragment的集合
        fragmentList = new ArrayList<>();
        fragmentList.add(new ProductFragment());
        fragmentList.add(new CartFragment());
        fragmentList.add(new OrderFragment());
        fragmentList.add(new MineFragment());
        //初始化CustomBottomTabWidget
        tabWidget.init(getSupportFragmentManager(), fragmentList);
    }

    // 定义一个变量，来标识是否退出
    private static boolean isExit = false;

    Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            isExit = false;
        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void exit() {
        if (!isExit) {
            isExit = true;
            BaseApplication.showToast("再按一次退出程序");
            // 利用handler延迟发送更改状态信息
            mHandler.sendEmptyMessageDelayed(0, 2000);
        } else {
            finish();
            System.exit(0);
        }
    }

}
