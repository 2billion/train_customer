package com.train.train_manager.act.ruku_add;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.train.train_manager.R;
import com.train.train_manager.act.bean.InAInfoBean;
import com.train.train_manager.base.BaseApplication;

public class RukuAddAdapter extends BaseAdapter {

    RukuAddActivity activity;


    public RukuAddAdapter(RukuAddActivity activity) {
        this.activity = activity;
    }

    static class ViewHolder {
        public TextView title;
        public TextView info1;
        public TextView info2;
        public TextView info3;
        public TextView info;

        public View item;
    }

    @Override
    public int getCount() {
        if (BaseApplication.app.dm.list_InAInfo == null) {
            return 0;
        } else {
            return BaseApplication.app.dm.list_InAInfo.size();
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
            convertView = View.inflate(this.activity, R.layout.ruku_add_list_item, null);
            holder.title = convertView.findViewById(R.id.title);
            holder.info1 = convertView.findViewById(R.id.info1);
            holder.info2 = convertView.findViewById(R.id.info2);
            holder.info3 = convertView.findViewById(R.id.info3);
            holder.info = convertView.findViewById(R.id.info);
            holder.item = convertView.findViewById(R.id.item);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        InAInfoBean bean = BaseApplication.app.dm.list_InAInfo.get(position);

        holder.title.setText(bean.des);
        holder.info1.setText("BST物料编码：" + bean.bstPartNo);
        holder.info2.setText("入库库位：" + bean.tLocation);
        holder.info3.setText("入库时间：" + bean.operTime);
        holder.info.setText((int) bean.qty + bean.ume);
        holder.item.setTag(bean);
        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InAInfoBean bean = (InAInfoBean) v.getTag();

                BaseApplication.app.dm.inaAddBean.transNo = bean.transNo;
                BaseApplication.app.dm.inaAddBean.transId = bean.transId;
                BaseApplication.app.dm.inaAddBean.bstPartNo = bean.bstPartNo;
                BaseApplication.app.dm.inaAddBean.tLocation = bean.tLocation;
                BaseApplication.app.dm.inaAddBean.qty = (int) bean.qty;

                activity.startActivity(new Intent(activity, RukuUpdateActivity.class));
            }
        });
        holder.item.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                InAInfoBean bean = (InAInfoBean) v.getTag();
                activity.delete_one(bean.transNo, bean.transId + "", bean.des);
                return false;
            }
        });
        return convertView;
    }
}
