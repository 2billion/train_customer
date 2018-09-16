package com.train.train_manager.act.kucun;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.train.train_manager.R;
import com.train.train_manager.act.bean.KuCunListBean;
import com.train.train_manager.base.BaseActivity;
import com.train.train_manager.base.BaseApplication;
import com.train.train_manager.core.NetCallback;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.Call;

public class KuCunActivity extends BaseActivity {

    View search;
    RefreshLayout refreshLayout;
    ListView list;

    EditText searchInput;
    View searchBtn;

    KuCunAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kucun_list);
        init();
    }

    public void init() {
        search = findViewById(R.id.search);
        refreshLayout = findViewById(R.id.refreshLayout);
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
        list = findViewById(R.id.list);
        adapter = new KuCunAdapter(this);
        list.setAdapter(adapter);

        searchInput = findViewById(R.id.search_input);
        searchInput.setText(BaseApplication.app.dm.kuCunParams.bstPartNo + "");
        searchInput.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            public void afterTextChanged(Editable s) {
                BaseApplication.app.dm.kuCunParams.bstPartNo = s.toString();
            }
        });

        searchBtn = findViewById(R.id.search_btn);
        searchBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                hideKeyBoard(v);
                refresh();
            }
        });

        // 返回
        findViewById(R.id.btn_left).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });

        //    获取数据放最后
        getDate();

    }


    public int page = 1;
    public int pageSize = 10;

    private void getDate() {
        BaseApplication.app.net.stock_list(new NetCallback() {
            @Override
            public void failure(Call call, IOException e) {
                finishLoad();
            }

            @Override
            public void response(Call call, String responseStr) throws IOException {
                Type cvbType = new TypeToken<KuCunListBean>() {
                }.getType();
                KuCunListBean bean = new Gson().fromJson(responseStr, cvbType);
                if (bean.isOK()) {
                    //刷新则删除列表
                    if (page == 1) {
                        BaseApplication.app.dm.kuCunList.clear();
                    }
                    BaseApplication.app.dm.kuCunList.addAll(bean.data.data);
                    runOnUiThread(new Runnable() {
                        public void run() {
                            adapter.notifyDataSetChanged();
                        }
                    });
                } else {
                    BaseApplication.app.showToast("请求失败" + bean.msg);
                }
                finishLoad();
            }
        }, page + "", pageSize + "");

    }

    public void finishLoad() {
        if (this.page == 1) {
            refreshLayout.finishRefresh();
        } else {
            refreshLayout.finishLoadMore();
        }
    }

    public void refresh() {
        KuCunActivity.this.page = 1;
        getDate();
    }

    public void loadMore() {
        KuCunActivity.this.page++;
        getDate();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BaseApplication.app.dm.kuCunParams.bstPartNo = "";
        BaseApplication.app.dm.kuCunParams.location = "";
    }
}