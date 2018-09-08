package com.train.train_customer.act.order;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.train.train_customer.R;
import com.train.train_customer.act.bean.OrderBean;
import com.train.train_customer.base.BaseApplication;

public class OrderAdapter extends BaseAdapter {

    OrderFragment fragment;


    public OrderAdapter(OrderFragment fragment) {
        this.fragment = fragment;
    }

    static class ViewHolder {
        public TextView title;
        public TextView info1;
        public TextView info2;
        public TextView info3;
        public TextView info4;
        public TextView info1_right;
        public TextView info2_right;
        public TextView info3_right;
        public TextView info4_right;
        public Button change;
    }

    @Override
    public int getCount() {
        if (BaseApplication.app.dm.orderList == null) {
            return 0;
        } else {
            return BaseApplication.app.dm.orderList.size();
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
            convertView = View.inflate(this.fragment.getContext(), R.layout.order_list_item, null);
            holder.title = convertView.findViewById(R.id.title);
            holder.info1 = convertView.findViewById(R.id.info1);
            holder.info2 = convertView.findViewById(R.id.info2);
            holder.info3 = convertView.findViewById(R.id.info3);
            holder.info4 = convertView.findViewById(R.id.info4);
            holder.info1_right = convertView.findViewById(R.id.info1_right);
            holder.info2_right = convertView.findViewById(R.id.info2_right);
            holder.info3_right = convertView.findViewById(R.id.info3_right);
            holder.info4_right = convertView.findViewById(R.id.info4_right);
            holder.change = convertView.findViewById(R.id.btn_change);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        OrderBean bean = BaseApplication.app.dm.orderList.get(position);
        holder.title.setText(bean.partName);
        holder.info1.setText("BST物料编码：" + bean.bstPartNo);
        holder.info2.setText("路局物料编码：" + bean.buPartNo);
        holder.info3.setText("适用车型：" + bean.tsType + "(" + bean.contractNo + ")");
        holder.info4.setText("订单时间：" + bean.orderTime);

        holder.info1_right.setText("总量："+bean.oTotalQty);
        holder.info2_right.setText("剩余："+bean.lastQty);
        holder.info3_right.setText("可领："+bean.pickQty);
        holder.info4_right.setText("");

        holder.change.setTag(bean);
        holder.change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrderBean bean = (OrderBean) v.getTag();
                BaseApplication.showToast("" + bean.partName);
            }
        });


        return convertView;
    }
}
