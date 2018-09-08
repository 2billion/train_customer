package com.train.train_customer.act.order;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.train.train_customer.R;
import com.train.train_customer.act.bean.BaseBean;
import com.train.train_customer.act.bean.OrderListBean;
import com.train.train_customer.act.bean.OrderParamsBean;
import com.train.train_customer.act.bean.PartBean;
import com.train.train_customer.act.bean.PartListBean;
import com.train.train_customer.base.BaseApplication;
import com.train.train_customer.base.BaseFragment;
import com.train.train_customer.core.NetCallback;

import org.json.JSONArray;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.Call;

/**
 * Created by solo on 2018/1/8.
 */

public class OrderFragment extends BaseFragment {


    View search;
    RefreshLayout refreshLayout;
    ListView list;

    EditText searchInput;
    View searchBtn;
    View right_view_btn;

    OrderAdapter adapter;
    OrderRightView rightView;


    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.order_main, null);
        search = view.findViewById(R.id.search);
        refreshLayout = view.findViewById(R.id.refreshLayout);
        refreshLayout.setEnableAutoLoadMore(true);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            public void onRefresh(RefreshLayout refreshlayout) {
                refresh();
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                loadMore();
            }
        });
        list = view.findViewById(R.id.list);
        adapter = new OrderAdapter(this);
        list.setAdapter(adapter);

        searchInput = view.findViewById(R.id.search_input);
        searchInput.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            public void afterTextChanged(Editable s) {
                BaseApplication.app.dm.orderParamsBean.bstPartNo= s.toString();
            }
        });

        searchBtn = view.findViewById(R.id.search_btn);
        searchBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                hideKeyBoard(v);
                refresh();
            }
        });

        //    右侧搜索 view
        rightView = new OrderRightView(this, view);
        //    显示右侧 btn
        right_view_btn = view.findViewById(R.id.right_view_btn);
        right_view_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                rightView.switchVisible();
            }
        });

        //    获取数据放最后
        getDate();

        return view;
    }


    public int page = 1;
    public int pageSize = 10;

    private void getDate() {
//        String partName = BaseApplication.app.dm.order_partName;
//        String tsType = BaseApplication.app.dm.order_tsType;
//        String partNo = BaseApplication.app.dm.order_bstPartNo;
//        String buPartNo = BaseApplication.app.dm.order_buPartNo;

        BaseApplication.app.net.findOrderDetailList(new NetCallback() {
            @Override
            public void failure(Call call, IOException e) {
                finishLoad();
            }

            @Override
            public void response(Call call, String responseStr) throws IOException {
                Type cvbType = new TypeToken<OrderListBean>() {
                }.getType();
                OrderListBean bean = new Gson().fromJson(responseStr, cvbType);
                if (bean.isOK()) {
                    //                   刷新则删除列表
                    if (page == 1) {
                        BaseApplication.app.dm.orderList.clear();
                    }
                    BaseApplication.app.dm.orderList.addAll(bean.data.data);
                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            adapter.notifyDataSetChanged();
                        }
                    });
                } else {
                    BaseApplication.app.showToast("请求失败" + bean.msg);
                }
                finishLoad();
            }
        }, page, pageSize, BaseApplication.app.dm.orderParamsBean);

    }

    public void finishLoad() {
        if (this.page == 1) {
            refreshLayout.finishRefresh();
        } else {
            refreshLayout.finishLoadMore();
        }
    }

    public void refresh() {
        OrderFragment.this.page = 1;
        getDate();
    }

    public void loadMore() {
        OrderFragment.this.page++;
        getDate();
    }

    public void add_cart() {

    }


}
