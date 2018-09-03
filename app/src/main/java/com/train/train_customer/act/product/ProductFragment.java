package com.train.train_customer.act.product;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.train.train_customer.R;
import com.train.train_customer.act.bean.PartListBean;
import com.train.train_customer.act.bean.TsTypeBean;
import com.train.train_customer.base.BaseApplication;
import com.train.train_customer.base.BaseFragment;
import com.train.train_customer.core.NetCallback;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import org.w3c.dom.Text;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

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

    EditText searchInput = null;
    View searchBtn = null;

    TagFlowLayout id_flowlayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.product_main, null);
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

        searchInput = view.findViewById(R.id.search_input);
        searchBtn = view.findViewById(R.id.search_btn);
        searchBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ProductFragment.this.page = 1;
                getDate();
            }
        });



        //    右侧数据
        id_flowlayout = view.findViewById(R.id.id_flowlayout);

        //    获取数据放最后
        getDate();
        //    搜索数据
        initTypeData();


        view.findViewById(R.id.ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initRightView();
//                BaseApplication.showToast("count:" + BaseApplication.app.dm.tsTypeList.size());
//                tagAdapter.notifyDataChanged();
            }
        });

        return view;
    }

    TagAdapter tagAdapter;

    private void initRightView() {
        tagAdapter = new TagAdapter(BaseApplication.app.dm.tsTypeList) {
            @Override
            public View getView(FlowLayout parent, int position, Object o) {
                TextView tag = (TextView) View.inflate(getContext(), R.layout.product_right_search_item, null);
                String str = BaseApplication.app.dm.tsTypeList.get(position).tsType;
                tag.setText(str);
                return tag;
            }
        };
        id_flowlayout.setAdapter(tagAdapter);
    }

    public int page = 1;
    public int pageSize = 10;

    private void getDate() {
        String partName = "";
        String tsType = "";
        String partNo = searchInput.getText().toString();
        String buPartNo = "";
        BaseApplication.app.net.getPartList(new NetCallback() {
            @Override
            public void failure(Call call, IOException e) {
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

    }

    private void finishLoad() {
        Log.e("app", "------------- finish load");
        if (this.page == 1) {
            refreshLayout.finishRefresh();
        } else {
            refreshLayout.finishLoadMore();
        }
    }

    //    搜索筛选数据
    private void initTypeData() {
        if (BaseApplication.app.dm.tsTypeList == null || BaseApplication.app.dm.tsTypeList.size() == 0) {
            BaseApplication.app.net.findTsTypeList(new NetCallback() {
                @Override
                public void failure(Call call, IOException e) {
                    finishLoad();
                }

                @Override
                public void response(Call call, String responseStr) throws IOException {
                    Type cvbType = new TypeToken<TsTypeBean>() {
                    }.getType();
                    TsTypeBean bean = new Gson().fromJson(responseStr, cvbType);
                    BaseApplication.app.dm.tsTypeList = bean.data;

                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            initRightView();
                            tagAdapter.notifyDataChanged();
                        }
                    });
                }
            });
        }

    }

    //    显示筛选
    private void showSearchView() {

    }

}
