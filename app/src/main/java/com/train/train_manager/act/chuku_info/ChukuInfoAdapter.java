package com.train.train_manager.act.chuku_info;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.train.train_manager.R;
import com.train.train_manager.act.bean.OutDetailBean;
import com.train.train_manager.base.BaseApplication;

public class ChukuInfoAdapter extends BaseAdapter {

    ChukuInfoActivity activity;


    public ChukuInfoAdapter(ChukuInfoActivity activity) {
        this.activity = activity;
    }

    static class ViewHolder {
        public TextView title;
        public TextView info1;
        public TextView info2;
        public TextView info3;
        public TextView info4;
        public TextView info5;

        public TextView info21;
        public TextView info22;

        public View item;
    }

    @Override
    public int getCount() {
        try {
            if (BaseApplication.app.dm.outInfoListBean.data.rqPickDetailList == null) {
                return 0;
            } else {
                return BaseApplication.app.dm.outInfoListBean.data.rqPickDetailList.size();
            }
        } catch (Exception e) {
            return 0;
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
            convertView = View.inflate(this.activity, R.layout.chuku_info_list_item, null);
            holder.title = convertView.findViewById(R.id.title);
            holder.info1 = convertView.findViewById(R.id.info1);
            holder.info2 = convertView.findViewById(R.id.info2);
            holder.info3 = convertView.findViewById(R.id.info3);
            holder.info4 = convertView.findViewById(R.id.info4);
            holder.info5 = convertView.findViewById(R.id.info5);

            holder.info21 = convertView.findViewById(R.id.info21);
            holder.info22 = convertView.findViewById(R.id.info22);

            holder.item = convertView.findViewById(R.id.item);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final OutDetailBean bean = BaseApplication.app.dm.outInfoListBean.data.rqPickDetailList.get(position);

        holder.title.setText(bean.partName);
        holder.info1.setText("BST物料编码：" + (bean.bstPartNo == null ? "" : bean.bstPartNo));
        holder.info2.setText("路局物料编码：" + (bean.buPartNo == null ? "" : bean.buPartNo));
        holder.info3.setText("库位编码：" + bean.location);
        holder.info4.setText("剩余需求：" + bean.requireQty + " 已交货数：" + (int) bean.deliveryQty);
        holder.info5.setText("库存数量：" + bean.locQty + " 总需求量：" + bean.totalRequireQty);

        final int status = BaseApplication.app.dm.outInfoListBean.data.status;
        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (status == 0) {
                    BaseApplication.showToast("您还未接单，不可进行出库操作！");
                } else if (status == 1) {
                    BaseApplication.app.dm.outDetailBean = bean;
                    activity.goto_chuku();
                } else if (status == 2) {
                    BaseApplication.showToast("已到达待出库区，不可操作");
                } else if (status == 3) {
                    BaseApplication.showToast("领料单出库已完成，不可操作！");
                }
            }
        });

        //        OutBean outBean = BaseApplication.app.dm.outList.get(position);
        //        //        领料单状态：0待下架1下架中2待交接3已完成
        //        if (outBean.status == 0) {
        //            holder.info22.setText("待出");
        //        } else if (outBean.status == 1) {
        //            holder.info22.setText("待出");
        //        } else if (outBean.status == 2) {
        //            holder.info22.setText("待出");
        //        } else if (outBean.status == 3) {
        //            holder.info22.setText("待出");
        //        }

        holder.info21.setText("" + (Integer.valueOf(bean.totalRequireQty) - (int) bean.deliveryQty) + (bean.umi == null ? "" : bean.umi));
        holder.info22.setText("待出");
        return convertView;
    }
}
