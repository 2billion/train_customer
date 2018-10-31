package com.train.train_manager.act.ruku_two.ruku_record;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.train.train_manager.R;
import com.train.train_manager.act.bean.InABean;
import com.train.train_manager.base.BaseApplication;

public class RukuRecordAdapter extends BaseAdapter {

    RukuRecordActivity activity;


    public RukuRecordAdapter(RukuRecordActivity activity) {
        this.activity = activity;
    }

    static class ViewHolder {
        public TextView title;
        public CheckBox select;
        public TextView info1;
        public TextView info2;
        public TextView status;

        public View item;
    }

    @Override
    public int getCount() {
        if (BaseApplication.app.dm.listInB == null) {
            return 0;
        } else {
            return BaseApplication.app.dm.listInB.size();
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
            convertView = View.inflate(this.activity, R.layout.ruku_record_list_item, null);
            holder.title = convertView.findViewById(R.id.title);
            holder.select = convertView.findViewById(R.id.select);
            holder.info1 = convertView.findViewById(R.id.info1);
            holder.info2 = convertView.findViewById(R.id.info2);
            holder.status = convertView.findViewById(R.id.status);
            holder.item = convertView.findViewById(R.id.item);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        InABean bean = BaseApplication.app.dm.listInB.get(position);

        holder.title.setText(bean.compTimeStr() + " 一类入库单");
        holder.info1.setText("入库单号：" + bean.billNo);
        holder.info2.setText("创建时间：" + bean.operTime);
        if (bean.status == 1) {
            holder.status.setText("已启动");
            holder.status.setBackgroundResource(R.drawable.v_label_round_blue);
        } else if (bean.status == 2) {
            holder.status.setText("已完成");
            holder.status.setBackgroundResource(R.drawable.v_label_round_green);
        }
        holder.item.setTag(bean);
        holder.item.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                InABean bean = (InABean) v.getTag();
                BaseApplication.app.dm.inaAddBean_2.billNo = bean.billNo;
                BaseApplication.app.dm.inaAddBean_2.status = bean.status;
//                TODO
//                activity.startActivity(new Intent(activity, RukuAddActivity.class));

            }
        });

        holder.item.setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View v) {
                InABean bean = (InABean) v.getTag();
                BaseApplication.app.dm.info_InABean = bean;
                activity.delete_ruku_record(bean.transNo);
                return false;
            }
        });


        return convertView;
    }
}
