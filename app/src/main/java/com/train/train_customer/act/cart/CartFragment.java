package com.train.train_customer.act.cart;

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
import com.train.train_customer.act.bean.CartBean;
import com.train.train_customer.act.bean.CartListBean;
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

public class CartFragment extends BaseFragment {

    View search;
    RefreshLayout refreshLayout;
    ListView list;

    EditText searchInput;
    View searchBtn;
    View right_view_btn;

    CartAdapter adapter;
    CartRightView rightView;

    Button btn_create_order;
    Button btn_del_cart;

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.cart_main, null);
        search = view.findViewById(R.id.search);
        refreshLayout = view.findViewById(R.id.refreshLayout);
        refreshLayout.setEnableAutoLoadMore(true);
        refreshLayout.setEnableLoadMore(false);
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
        adapter = new CartAdapter(this);
        list.setAdapter(adapter);

        searchInput = view.findViewById(R.id.search_input);
        searchInput.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            public void afterTextChanged(Editable s) {
                BaseApplication.app.dm.cart_bstPartNo = s.toString();
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
        rightView = new CartRightView(this, view);
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
                BaseApplication.showToast(BaseApplication.app.dm.cartList.size()+ "create order");
            }
        });
        btn_del_cart = view.findViewById(R.id.btn_del_cart);
        btn_del_cart.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                BaseApplication.showToast(BaseApplication.app.dm.cartList.size()+ "del");
            }
        });

        //    获取数据放最后
        getDate();

        return view;
    }


    public int page = 1;
    public int pageSize = 10;

    private void getDate() {

        String cart_tsType = BaseApplication.app.dm.cart_tsType;
        String cart_bstPartNo = BaseApplication.app.dm.cart_bstPartNo;
        String cart_buPartNo = BaseApplication.app.dm.cart_buPartNo;

        BaseApplication.app.net.getCartList(new NetCallback() {
            @Override
            public void failure(Call call, IOException e) {
                finishLoad();
            }

            @Override
            public void response(Call call, String responseStr) throws IOException {
                Type cvbType = new TypeToken<CartListBean>() {
                }.getType();
                CartListBean bean = new Gson().fromJson(responseStr, cvbType);
                if (bean.isOK()) {
                    //                   刷新则删除列表
                    if (page == 1) {
                        BaseApplication.app.dm.cartList.clear();
                    }
                    BaseApplication.app.dm.cartList.addAll(bean.data);
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
        }, cart_tsType,cart_bstPartNo,cart_buPartNo);

    }

    public void finishLoad() {
        if (this.page == 1) {
            refreshLayout.finishRefresh();
        } else {
            refreshLayout.finishLoadMore();
        }
    }

    public void refresh() {
        CartFragment.this.page = 1;
        getDate();
    }

    public void loadMore() {
        CartFragment.this.page++;
        getDate();
    }

    public void update_cart(CartBean bean) {
        BaseApplication.app.net.updateCartInfo(new NetCallback() {
            @Override
            public void failure(Call call, IOException e) {
            }

            @Override
            public void response(Call call, String responseStr) throws IOException {
                BaseBean bean = new BaseBean().onBack(responseStr);
                if(bean.isOK()){
                    refresh();
                }
                BaseApplication.app.showToast(bean.msg);
            }
        }, bean);
    }

    public void delete_cart(CartBean bean) {
        BaseApplication.app.net.deleteCartInfo(new NetCallback() {
            @Override
            public void failure(Call call, IOException e) {
            }

            @Override
            public void response(Call call, String responseStr) throws IOException {
                BaseBean bean = new BaseBean().onBack(responseStr);
                if(bean.isOK()){
                    refresh();
                }
                BaseApplication.app.showToast(bean.msg);
            }
        }, bean);
    }

}
