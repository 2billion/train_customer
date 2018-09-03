package com.train.train_customer.act.product;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.train.train_customer.R;
import com.train.train_customer.act.bean.LoginBean;
import com.train.train_customer.act.bean.PartListBean;
import com.train.train_customer.base.BaseApplication;
import com.train.train_customer.base.BaseFragment;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by solo on 2018/1/8.
 */

public class ProductFragment extends BaseFragment {

    View search;
    RefreshLayout refreshLayout;
    ListView list;
    ProductAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_product, null);
        search = view.findViewById(R.id.search);
        //        list = view.findViewById(R.id.recyclerView);
        refreshLayout = view.findViewById(R.id.refreshLayout);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshlayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败
                Log.i("list", "onRefresh");
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                refreshlayout.finishLoadMore(2000/*,false*/);//传入false表示加载失败
                Log.i("list", "setOnLoadMoreListener");
            }
        });
        list = view.findViewById(R.id.list);
        adapter = new ProductAdapter(getContext());
        list.setAdapter(adapter);
        getDate();
        return view;
    }

    private void getDate() {
        String partName = "";
        String tsType = "";
        String partNo = "";
        String buPartNo = "";
        int page = 1;
        int pageSize = 20;
        BaseApplication.app.net.getPartList(new Callback() {
            public void onFailure(Call call, IOException e) {
                BaseApplication.app.showToast("请求失败");
            }

            public void onResponse(Call call, Response response) throws IOException {
                String back = response.body().string();
                Type cvbType = new TypeToken<PartListBean>() {
                }.getType();
                PartListBean bean = new Gson().fromJson(back, cvbType);
                if (bean.isOK()) {
                    BaseApplication.app.dm.partList.addAll(bean.data.data);
                    BaseApplication.app.showToast("请求成功");
                      getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            adapter.notifyDataSetChanged();
                        }
                    });
                } else {
                    BaseApplication.app.showToast("请求失败");
                }

            }
        }, page, pageSize,partName,tsType,partNo,buPartNo);


    }


}
