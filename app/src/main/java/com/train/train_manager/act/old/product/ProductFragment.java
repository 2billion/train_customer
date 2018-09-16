package com.train.train_manager.act.old.product;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import com.train.train_manager.R;
import com.train.train_manager.act.bean.BaseBean;
import com.train.train_manager.act.bean.PartBean;
import com.train.train_manager.act.bean.PartListBean;
import com.train.train_manager.base.BaseApplication;
import com.train.train_manager.base.BaseFragment;
import com.train.train_manager.core.NetCallback;

import org.json.JSONArray;

import java.io.IOException;
import java.lang.reflect.Type;

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

    EditText searchInput;
    View searchBtn;
    View right_view_btn;

    ProductAdapter adapter;
    ProductRightView rightView;

    Button btn_create_order;
    Button btn_add_cart;

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.product_main, null);
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
        adapter = new ProductAdapter(this);
        list.setAdapter(adapter);

        searchInput = view.findViewById(R.id.search_input);
        searchInput.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            public void afterTextChanged(Editable s) {
                BaseApplication.app.dm.product_partNo = s.toString();
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
        rightView = new ProductRightView(this, view);
        //    显示右侧 btn
        right_view_btn = view.findViewById(R.id.right_view_btn);
        right_view_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                rightView.switchVisible();
            }
        });

        //        俩按钮
        btn_create_order = view.findViewById(R.id.btn_create_order);
        btn_create_order.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                create_order();
            }
        });
        btn_add_cart = view.findViewById(R.id.btn_add_cart);
        btn_add_cart.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                add_cart();
            }
        });

        //    获取数据放最后
        getDate();


        return view;
    }


    public int page = 1;
    public int pageSize = 10;

    private void getDate() {
        String partName = BaseApplication.app.dm.product_partName;
        String tsType = BaseApplication.app.dm.product_tsType;
        String partNo = BaseApplication.app.dm.product_partNo;
        String buPartNo = BaseApplication.app.dm.product_buPartNo;

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
                        BaseApplication.app.dm.productList.clear();
                    }
                    BaseApplication.app.dm.productList.addAll(bean.data.data);
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

    public void finishLoad() {
        if (this.page == 1) {
            refreshLayout.finishRefresh();
        } else {
            refreshLayout.finishLoadMore();
        }
    }

    public void refresh() {
        ProductFragment.this.page = 1;
        getDate();
    }

    public void loadMore() {
        ProductFragment.this.page++;
        getDate();
    }

    public void add_cart() {
        int a = 0;
        int b = a / 0;
        JSONArray jsonArray = new JSONArray();
        for (PartBean bean : BaseApplication.app.dm.productList) {
            if (bean.isChecked) {
                jsonArray.put(bean.jsonForCart());
            }
        }
        if (jsonArray.length() == 0) {
            BaseApplication.showToast("请选择添加购物车的配件");
            return;
        }
        BaseApplication.app.net.saveCartListInfo(new NetCallback() {
            @Override
            public void failure(Call call, IOException e) {
            }

            @Override
            public void response(Call call, String responseStr) throws IOException {
                BaseBean bean = new BaseBean().onBack(responseStr);
                BaseApplication.app.showToast(bean.msg);
                if (bean.isOK()) {
                    initAfterAddCart();
                }
            }
        }, jsonArray);
    }

    public void initAfterAddCart() {
        for (PartBean bean : BaseApplication.app.dm.productList) {
            bean.isChecked = false;
            bean.count = 0;
        }
        getActivity().runOnUiThread(new Runnable() {
            public void run() {
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void create_order() {
        boolean is_checked_one = false;
        for (PartBean bean : BaseApplication.app.dm.productList) {
            if (bean.isChecked) {
                is_checked_one = true;
                break;
            }
        }
        if (!is_checked_one) {
            BaseApplication.showToast("请选择要下单的配件");
            return;
        }

        new AlertDialog.Builder(getActivity()).setMessage("确认对选中的配件下单么？")
                .setNeutralButton("取消", null)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        JSONArray jsonArray = new JSONArray();
                        for (PartBean bean : BaseApplication.app.dm.productList) {
                            if (bean.isChecked) {
                                jsonArray.put(bean.jsonForCreateOrder());
                            }
                        }
                        BaseApplication.app.net.saveOrderInfo(new NetCallback() {
                            public void failure(Call call, IOException e) {
                            }

                            public void response(Call call, String responseStr) throws IOException {
                                BaseBean bean = new BaseBean().onBack(responseStr);
                                if (bean.isOK()) {
                                    refresh();
                                }
                                BaseApplication.app.showToast(bean.msg);
                            }
                        }, jsonArray);
                    }
                }).show();

    }
}