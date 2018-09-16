package com.train.train_manager.act.kucun;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.train.train_manager.R;
import com.train.train_manager.act.bean.KuCunBean;
import com.train.train_manager.base.BaseApplication;

public class KuCunAdapter extends BaseAdapter {

    KuCunActivity activity;


    public KuCunAdapter(KuCunActivity activity) {
        this.activity = activity;
    }

    static class ViewHolder {
        public TextView title;
        public CheckBox select;
        public TextView info1;
        public TextView info2;
        public TextView info3;
        public TextView info;

        public View item;
    }

    @Override
    public int getCount() {
        if (BaseApplication.app.dm.kuCunList == null) {
            return 0;
        } else {
            return BaseApplication.app.dm.kuCunList.size();
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
            convertView = View.inflate(this.activity, R.layout.kucun_list_item, null);
            holder.title = convertView.findViewById(R.id.title);
            holder.select = convertView.findViewById(R.id.select);
            holder.info1 = convertView.findViewById(R.id.info1);
            holder.info2 = convertView.findViewById(R.id.info2);
            holder.info3 = convertView.findViewById(R.id.info3);
            holder.info = convertView.findViewById(R.id.info);

            holder.item = convertView.findViewById(R.id.item);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        KuCunBean bean = BaseApplication.app.dm.kuCunList.get(position);

        holder.title.setText(bean.bstPartNo);
        holder.info1.setText("库位编码：" + bean.location);
        holder.info2.setText("项目号：" + bean.contractNo);
        holder.info.setText(bean.qty + "个");

        return convertView;
    }
}
