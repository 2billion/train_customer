package com.train.train_customer.act.cart;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.train.train_customer.R;
import com.train.train_customer.act.bean.CartBean;
import com.train.train_customer.base.BaseApplication;
import com.train.train_customer.view.AmountView;

public class CartAdapter extends BaseAdapter {

    CartFragment fragment;


    public CartAdapter(CartFragment fragment) {
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
        public Button cart_btn_del;
        public EditText etAmount;
    }

    @Override
    public int getCount() {
        if (BaseApplication.app.dm.cartList == null) {
            return 0;
        } else {
            return BaseApplication.app.dm.cartList.size();
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
            convertView = View.inflate(this.fragment.getContext(), R.layout.cart_list_item, null);
            holder.title = convertView.findViewById(R.id.title);
            holder.select = convertView.findViewById(R.id.select);
            holder.info1 = convertView.findViewById(R.id.info1);
            holder.info2 = convertView.findViewById(R.id.info2);
            holder.info3 = convertView.findViewById(R.id.info3);
            holder.amountView = convertView.findViewById(R.id.amount_view);
            holder.checkBox = convertView.findViewById(R.id.select);
            holder.cart_btn_del = convertView.findViewById(R.id.cart_btn_del);
            holder.etAmount = convertView.findViewById(R.id.etAmount);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        CartBean bean = BaseApplication.app.dm.cartList.get(position);
        holder.title.setText(bean.partName);
        holder.info1.setText("BST物料编码：" + bean.bstPartNo);
        holder.info2.setText("路局物料编码：" + bean.buPartNo);
        holder.info3.setText("适用车型：" + bean.tsType + "(" + bean.contractNo + ")");

        holder.checkBox.setTag(bean);
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ((CartBean) buttonView.getTag()).ischecked = isChecked;
                fragment.getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        CartAdapter.this.notifyDataSetChanged();
                    }
                });
            }
        });
        holder.checkBox.setChecked(bean.ischecked);
        holder.amountView.setTag(bean);
        holder.etAmount.setText("" + (int) (bean.qty));
        holder.amountView.setOnAmountChangeListener(new AmountView.OnAmountChangeListener() {
            public void onAmountChange(View view, int amount) {
                CartBean bean = (CartBean) view.getTag();
                Log.e("app","amount + " + amount + "bean" + bean.qty);
                if (bean.qty != amount) {
                    bean.qty = amount;
                    fragment.update_cart(bean);
                }
            }
        });
        holder.cart_btn_del.setTag(bean);
        holder.cart_btn_del.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                CartBean bean = (CartBean) v.getTag();
                fragment.delete_cart(bean);
            }
        });

        return convertView;
    }
}
