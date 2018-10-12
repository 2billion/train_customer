package com.train.train_manager.act.chuku;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.train.train_manager.R;
import com.train.train_manager.act.bean.OutBean;
import com.train.train_manager.act.chuku_info.ChukuInfoActivity;
import com.train.train_manager.base.BaseApplication;

public class ChukuAdapter extends BaseAdapter {

    ChuKuActivity activity;


    public ChukuAdapter(ChuKuActivity activity) {
        this.activity = activity;
    }

    static class ViewHolder {
        public TextView title;
        public CheckBox select;
        public TextView info1;
        public TextView info2;
        public TextView info3;
        public TextView info4;
        public TextView info5;
        public TextView status;

        public View item;
    }

    @Override
    public int getCount() {
        if (BaseApplication.app.dm.outList == null) {
            return 0;
        } else {
            return BaseApplication.app.dm.outList.size();
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
            convertView = View.inflate(this.activity, R.layout.chuku_record_list_item, null);
            holder.title = convertView.findViewById(R.id.title);
            holder.select = convertView.findViewById(R.id.select);
            holder.info1 = convertView.findViewById(R.id.info1);
            holder.info2 = convertView.findViewById(R.id.info2);
            holder.info3 = convertView.findViewById(R.id.info3);
            holder.info4 = convertView.findViewById(R.id.info4);
            holder.info5 = convertView.findViewById(R.id.info5);
            holder.status = convertView.findViewById(R.id.status);
            holder.item = convertView.findViewById(R.id.item);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        OutBean bean = BaseApplication.app.dm.outList.get(position);

        holder.title.setText(bean.store + "库-WO:" + bean.pickId + "-CSSALE");
        holder.info1.setText("领料单号：" + bean.pickNo);
        holder.info2.setText("日期：" + bean.genTime);
        holder.info3.setText("发料原因：" + bean.reason);
        holder.info4.setText("联系人：" + bean.contractNo);
        holder.info5.setText("发送到：" + (bean.deliveryTo == null ? "" : bean.deliveryTo));

        if (bean.status == 0) {
            holder.status.setText("待下架");
            holder.status.setBackgroundResource(R.drawable.v_label_round_blue);
        } else if (bean.status == 1) {
            holder.status.setText("下架中");
            holder.status.setBackgroundResource(R.drawable.v_label_round_gray);
        } else if (bean.status == 2) {
            holder.status.setText("待交接");
            holder.status.setBackgroundResource(R.drawable.v_label_round_red);
        } else if (bean.status == 3) {
            holder.status.setText("已完成");
            holder.status.setBackgroundResource(R.drawable.v_label_round_green);
        }

        holder.item.setTag(bean);
        holder.item.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                OutBean bean = (OutBean) v.getTag();
                BaseApplication.app.dm.outBean = bean;
                activity.startActivity(new Intent(activity, ChukuInfoActivity.class));
            }
        });


        //        holder.info3.setText("适用车型：" + bean.tsType + "(" + bean.contractNo + ")");

        //        holder.checkBox.setTag(bean);
        //        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
        //            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        //                PartBean bean= (PartBean) buttonView.getTag();
        //                bean.isChecked = isChecked;
        //                activity.runOnUiThread(new Runnable() {
        //                    public void run() {
        //                        RukuRecordAdapter.this.notifyDataSetChanged();
        //                    }
        //                });
        //            }
        //        });
        //        holder.checkBox.setChecked(bean.isChecked);
        //        if (bean.isChecked) {
        //            holder.amountView.setVisibility(View.VISIBLE);
        //        } else {
        //            holder.amountView.setVisibility(View.GONE);
        //        }
        //        holder.amountView.setTag(bean);
        //        holder.amountView.setOnAmountChangeListener(new AmountView.OnAmountChangeListener() {
        //            public void onAmountChange(View view, int amount) {
        //                ((PartBean) view.getTag()).count = amount;
        //            }
        //        });

        return convertView;
    }
}
