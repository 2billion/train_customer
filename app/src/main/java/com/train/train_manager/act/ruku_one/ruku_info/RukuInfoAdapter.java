package com.train.train_manager.act.ruku_one.ruku_info;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.train.train_manager.R;
import com.train.train_manager.act.bean.InAInfoBean;
import com.train.train_manager.base.BaseApplication;

public class RukuInfoAdapter extends BaseAdapter {

    RukuInfoActivity activity;


    public RukuInfoAdapter(RukuInfoActivity activity) {
        this.activity = activity;
    }

    static class ViewHolder {
        public TextView title;
        public TextView info1;
        public TextView info2;
        public TextView info3;
        public TextView info;
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
            convertView = View.inflate(this.activity, R.layout.ruku_info_list_item, null);
            holder.title = convertView.findViewById(R.id.title);
            holder.info1 = convertView.findViewById(R.id.info1);
            holder.info2 = convertView.findViewById(R.id.info2);
            holder.info3 = convertView.findViewById(R.id.info3);
            holder.info = convertView.findViewById(R.id.info);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        InAInfoBean bean = BaseApplication.app.dm.list_InAInfo.get(position);

        holder.title.setText(bean.des);
        holder.info1.setText("BST物料编码：" + bean.bstPartNo);
        holder.info2.setText("入库库位：" + bean.tLocation);
        holder.info3.setText("入库时间：" + bean.operTime);
        holder.info.setText(bean.qty + bean.ume);
        return convertView;
    }
}