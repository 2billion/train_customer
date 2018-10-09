package com.train.train_customer.act.cart;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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
                createOrder();
            }
        });
        btn_del_cart = view.findViewById(R.id.btn_del_cart);
        btn_del_cart.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                delete_cart_selected();
            }
        });

        //    获取数据放最后
        getDate();

        Log.e("app", "----------------- cart onCreateView ");

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
        }, cart_tsType, cart_bstPartNo, cart_buPartNo);

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
                if (bean.isOK()) {
                    refresh();
                }
                BaseApplication.app.showToast(bean.msg);
            }
        }, bean);
    }


    public void delete_cart(final CartBean bean) {
        showDeleteDialog(bean);
    }

    public void delete_cart_selected() {
        JSONArray jsonArray = new JSONArray();
        for (CartBean bean : BaseApplication.app.dm.cartList) {
            if (bean.ischecked) {
                jsonArray.put(bean.api_json());
            }
        }
        if (jsonArray.length() > 0) {
            showDeleteDialog(jsonArray);
        } else {
            BaseApplication.showToast("请选择要删除的配件");
        }
    }

    public void do_delete_cart(JSONArray jsonArray) {
        BaseApplication.app.net.deleteCartInfo(new NetCallback() {
            @Override
            public void failure(Call call, IOException e) {
            }

            @Override
            public void response(Call call, String responseStr) throws IOException {
                BaseBean bean = new BaseBean().onBack(responseStr);
                if (bean.isOK()) {
                    refresh();
                }
                BaseApplication.app.showToast(bean.msg);
            }
        }, jsonArray);

    }

    public void showDeleteDialog(final JSONArray jsonArray) {
        new AlertDialog.Builder(getActivity()).setMessage("确定删除所选配件？")
                .setNeutralButton("取消", null)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        do_delete_cart(jsonArray);
                    }
                }).show();
    }

    public void showDeleteDialog(final CartBean bean) {
        new AlertDialog.Builder(getActivity()).setMessage("确定删除(" + bean.partName + ")？")
                .setNeutralButton("取消", null)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        JSONArray jsonArray = new JSONArray();
                        jsonArray.put(bean.api_json());
                        do_delete_cart(jsonArray);
                    }
                }).show();
    }

    private void createOrder() {
        boolean is_checked_one = false;
        for (CartBean bean : BaseApplication.app.dm.cartList) {
            if (bean.ischecked) {
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
                        for (CartBean bean : BaseApplication.app.dm.cartList) {
                            if (bean.ischecked) {
                                jsonArray.put(bean.clearCartList_params_json());
                            }
                        }
                        BaseApplication.app.net.clearCartList(new NetCallback() {
                            public void failure(Call call, IOException e) {
                            }

                            public void response(Call call, String responseStr) throws IOException {
                                BaseBean bean = new BaseBean().onBack(responseStr);
                                if (bean.isOK()) {
                                    refresh();
                                }
                                BaseApplication.app.showToast(bean.msg);
                                BaseApplication.app.reload_list_order = true;
                            }
                        }, jsonArray);
                    }
                }).show();


    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("app", "----------------- cart onResume ");
    }

    @Override
    public void reload_list() {
        Log.e("app", "----------------- cart reload_list ");
        if (BaseApplication.app.reload_list_cart) {
            BaseApplication.app.reload_list_cart = false;
            Log.e("app", "----------------- cart reload_list start... ");
            refresh();
        }
    }
}
