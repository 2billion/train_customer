package com.train.train_customer.act.product;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;
import android.widget.TextView;

import com.train.train_customer.R;
import com.train.train_customer.act.bean.PartBean;
import com.train.train_customer.base.BaseApplication;
import com.train.train_customer.view.AmountView;

public class ProductAdapter extends BaseAdapter {

    public Context ctx = null;

    public ProductAdapter(Context ctx) {
        this.ctx = ctx;
    }

    static class ViewHolder {
        public TextView title;
        public RadioButton select;
        public TextView info1;
        public TextView info2;
        public TextView info3;
        public AmountView amountView;
    }

    @Override
    public int getCount() {
        if (BaseApplication.app.dm.partList == null) {
            return 0;
        } else {
            return BaseApplication.app.dm.partList.size();
        }
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(this.ctx, R.layout.product_list_item, null);
            holder.title = (TextView) convertView.findViewById(R.id.title);
            holder.select = (RadioButton) convertView.findViewById(R.id.select);
            holder.info1 = (TextView) convertView.findViewById(R.id.info1);
            holder.info2 = (TextView) convertView.findViewById(R.id.info2);
            holder.info3 = (TextView) convertView.findViewById(R.id.info3);
            holder.amountView = (AmountView) convertView.findViewById(R.id.amount_view);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        PartBean bean = BaseApplication.app.dm.partList.get(position);
        holder.title.setText(bean.partC);
        holder.info1.setText("BST物料编码：" + bean.partNo);
        holder.info2.setText("路局物料编码：" + bean.buPartNo);
        holder.info3.setText("适用车型：" + bean.tsType + "(" + bean.contractNo + ")");

        return convertView;
    }
}
