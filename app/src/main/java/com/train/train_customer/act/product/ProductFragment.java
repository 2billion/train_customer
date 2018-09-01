package com.train.train_customer.act.product;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.train.train_customer.R;
import com.train.train_customer.base.BaseFragment;

/**
 * Created by solo on 2018/1/8.
 */

public class ProductFragment extends BaseFragment {

    View search;
    ListView list;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_product, null);
        search = view.findViewById(R.id.search);
        list = view.findViewById(R.id.list);
        getDate();
        return view;
    }

    private void getDate(){
        
    }


}
