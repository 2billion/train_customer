package com.train.train_customer.act;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.train.train_customer.R;
import com.train.train_customer.act.cart.CartFragment;
import com.train.train_customer.act.mine.MineFragment;
import com.train.train_customer.act.order.OrderFragment;
import com.train.train_customer.act.product.ProductFragment;
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
}
