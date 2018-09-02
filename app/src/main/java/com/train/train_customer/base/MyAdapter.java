package com.train.train_customer.base;

import android.content.Context;
import android.widget.BaseAdapter;

public abstract class MyAdapter extends BaseAdapter {
    public Context ctx;

    public MyAdapter(Context ctx) {
        this.ctx = ctx;
    }

    @Override
    public Object getItem(int arg0) {
        return null;
    }

    @Override
    public long getItemId(int arg0) {
        return 0;
    }
}
