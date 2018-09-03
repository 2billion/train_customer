package com.train.train_customer.act.product;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.train.train_customer.R;
import com.train.train_customer.act.bean.PartListBean;
import com.train.train_customer.base.BaseApplication;
import com.train.train_customer.base.BaseFragment;
import com.train.train_customer.core.NetCallback;

import java.io.IOException;
import java.lang.reflect.Type;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * 配件列表
 * <p>
 * search key : partNo
 */

public class ProductFragment extends BaseFragment {

    View search;
    RefreshLayout refreshLayout;
    ListView list;
    ProductAdapter adapter;

    String searchStr = null;

    EditText searchInput = null;
    View searchBtn = null;


    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_product, null);
        search = view.findViewById(R.id.search);
        refreshLayout = view.findViewById(R.id.refreshLayout);
        refreshLayout.setEnableAutoLoadMore(true);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
//                refreshlayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败
                ProductFragment.this.page = 1;
                getDate();
                Log.e("list", "onRefresh");
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
//                refreshlayout.finishLoadMore(2000/*,false*/);//传入false表示加载失败
                ProductFragment.this.page++;
                getDate();
                Log.e("list", "setOnLoadMoreListener");
            }
        });
        list = view.findViewById(R.id.list);
        adapter = new ProductAdapter(getContext());
        list.setAdapter(adapter);

        searchInput = (EditText) view.findViewById(R.id.search_input);
        searchBtn = view.findViewById(R.id.search_btn);
        searchBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ProductFragment.this.page=1;
                getDate();
            }
        });

//获取数据放最后
        getDate();


        return view;
    }

    public int page = 1;
    public int pageSize = 5;

    private void getDate() {
        String partName = "";
        String tsType = "";
        String partNo = searchInput.getText().toString();
        String buPartNo = "";
        BaseApplication.app.net.getPartList(new NetCallback() {
            @Override
            public void failure(Call call, IOException e) {
                BaseApplication.app.showToast("请求失败" + e.getMessage());
                finishLoad();
            }

            @Override
            public void response(Call call, String responseStr) throws IOException {
                Type cvbType = new TypeToken<PartListBean>() {
                }.getType();
                PartListBean bean = new Gson().fromJson(responseStr, cvbType);
                if (bean.isOK()) {
//                   刷新则删除列表
                    if (page == 1) {
                        BaseApplication.app.dm.partList.clear();
                    }
                    BaseApplication.app.dm.partList.addAll(bean.data.data);
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
        }, page, pageSize, partName, tsType, partNo, buPartNo);


//        BaseApplication.app.net.getPartList(new Callback() {
//            public void onFailure(Call call, IOException e) {
//                BaseApplication.app.showToast("请求失败"+e.getMessage());
//                finishLoad();
//            }
//
//            public void onResponse(Call call, Response response) throws IOException {
//                String back = response.body().string();
//                Type cvbType = new TypeToken<PartListBean>() {
//                }.getType();
//                PartListBean bean = new Gson().fromJson(back, cvbType);
//                if (bean.isOK()) {
////                   刷新则删除列表
//                    if (page == 1) {
//                        BaseApplication.app.dm.partList.clear();
//                    }
//                    BaseApplication.app.dm.partList.addAll(bean.data.data);
//                    getActivity().runOnUiThread(new Runnable() {
//                        public void run() {
//                            adapter.notifyDataSetChanged();
//                        }
//                    });
//                } else {
//                    BaseApplication.app.showToast("请求失败"+bean.msg);
//                }
//                finishLoad();
//            }
//        }, page, pageSize, partName, tsType, partNo, buPartNo);

    }

    private void finishLoad() {
        Log.e("app", "------------- finish load");
        if (this.page == 1) {
            refreshLayout.finishRefresh();
        } else {
            refreshLayout.finishLoadMore();
        }
    }


}
