package com.train.train_customer.act.cart;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.train.train_customer.R;
import com.train.train_customer.base.BaseFragment;

/**
 * Created by solo on 2018/1/8.
 */

public class CartFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_card, null);
        return view;

    }
}
