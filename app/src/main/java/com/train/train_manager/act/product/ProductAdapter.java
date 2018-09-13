package com.train.train_manager.act.product;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.train.train_manager.R;
import com.train.train_manager.act.bean.PartBean;
import com.train.train_manager.base.BaseApplication;
import com.train.train_manager.view.AmountView;

public class ProductAdapter extends BaseAdapter {

    ProductFragment fragment;


    public ProductAdapter(ProductFragment fragment) {
        this.fragment = fragment;
    }

    static class ViewHolder {
        public TextView title;
        public CheckBox select;
        public TextView info1;
        public TextView info2;
        public TextView info3;
        public AmountView amountView;
        public CheckBox checkBox;
    }

    @Override
    public int getCount() {
        if (BaseApplication.app.dm.productList == null) {
            return 0;
        } else {
            return BaseApplication.app.dm.productList.size();
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
            convertView = View.inflate(this.fragment.getContext(), R.layout.product_list_item, null);
            holder.title = convertView.findViewById(R.id.title);
            holder.select = convertView.findViewById(R.id.select);
            holder.info1 = convertView.findViewById(R.id.info1);
            holder.info2 = convertView.findViewById(R.id.info2);
            holder.info3 = convertView.findViewById(R.id.info3);
            holder.amountView = convertView.findViewById(R.id.amount_view);
            holder.checkBox = convertView.findViewById(R.id.select);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        PartBean bean = BaseApplication.app.dm.productList.get(position);
        holder.title.setText(bean.partC);
        holder.info1.setText("BST物料编码：" + bean.partNo);
        holder.info2.setText("路局物料编码：" + bean.buPartNo);
        holder.info3.setText("适用车型：" + bean.tsType + "(" + bean.contractNo + ")");

        holder.checkBox.setTag(bean);
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                PartBean bean= (PartBean) buttonView.getTag();
                bean.isChecked = isChecked;
                fragment.getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        ProductAdapter.this.notifyDataSetChanged();
                    }
                });
            }
        });
        holder.checkBox.setChecked(bean.isChecked);
        if (bean.isChecked) {
            holder.amountView.setVisibility(View.VISIBLE);
        } else {
            holder.amountView.setVisibility(View.GONE);
        }
        holder.amountView.setTag(bean);
        holder.amountView.setOnAmountChangeListener(new AmountView.OnAmountChangeListener() {
            public void onAmountChange(View view, int amount) {
                ((PartBean) view.getTag()).count = amount;
            }
        });

        return convertView;
    }
}
