package com.train.train_manager.act.chuku;

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
import com.train.train_manager.act.bean.OutListBean;
import com.train.train_manager.act.bean.OutParamsBean;
import com.train.train_manager.base.BaseActivity;
import com.train.train_manager.base.BaseApplication;
import com.train.train_manager.base.Reader;
import com.train.train_manager.core.NetCallback;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.Call;

public class ChuKuActivity extends BaseActivity {

    View search;
    RefreshLayout refreshLayout;
    ListView list;

    EditText searchInput;
    View searchBtn;
    View right_view_btn;

    ChukuAdapter adapter;
    ChukuRightView rightView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chuku_record);
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
        adapter = new ChukuAdapter(this);
        list.setAdapter(adapter);

        searchInput = findViewById(R.id.search_input);
        searchInput.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            public void afterTextChanged(Editable s) {
                BaseApplication.app.dm.outParams.pickNo = s.toString();
            }
        });

        searchBtn = findViewById(R.id.search_btn);
        searchBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                hideKeyBoard(v);
                refresh();
            }
        });

        //    右侧搜索 view
        rightView = new ChukuRightView(this, findViewById(R.id.right_search_view));
        //    显示右侧 btn
        right_view_btn = findViewById(R.id.right_view_btn);
        right_view_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                rightView.switchVisible();
            }
        });

        // 返回
        findViewById(R.id.btn_left).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
        //        首次进来，初始化状态
        BaseApplication.app.dm.outParams.status = "";


        //    获取数据放最后
        log("------------------------- create refresh");
        getData();

        this.reader = new Reader() {
            @Override
            public void back(String code) {
                on_back(code);
            }
        };

    }


    public int page = 1;
    public int pageSize = 10;

    private void getData() {


        OutParamsBean bean = BaseApplication.app.dm.outParams;


        BaseApplication.app.net.listforstock(new NetCallback() {
            @Override
            public void failure(Call call, IOException e) {
                finishLoad();
            }

            @Override
            public void response(Call call, String responseStr) throws IOException {
                Type cvbType = new TypeToken<OutListBean>() {
                }.getType();
                OutListBean bean = new Gson().fromJson(responseStr, cvbType);
                if (bean.isOK()) {
                    //刷新则删除列表
                    if (page == 1) {
                        BaseApplication.app.dm.outList.clear();
                    }
                    BaseApplication.app.dm.outList.addAll(bean.data.data);
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
        }, bean, "" + page, "" + pageSize);

    }

    public void finishLoad() {
        if (this.page == 1) {
            refreshLayout.finishRefresh();
        } else {
            refreshLayout.finishLoadMore();
        }
    }

    public void refresh() {
        ChuKuActivity.this.page = 1;
        getData();
    }

    public void loadMore() {
        ChuKuActivity.this.page++;
        getData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BaseApplication.app.dm.outParams.pickNo = "";
    }

    @Override
    protected void onResume() {
        super.onResume();
        log("------------------------- onResume refresh");
        refresh();
    }


    public void on_back(final String str) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (str.equals("")) {
                    return;
                    //                } else if (str.startsWith("#")) {
                    //                    String str_real = str.replace("#", "");
                    //                    BaseApplication.app.dm.kuCunParams.location = str_real;
                    //                    kuwei.setText(str_real.toString());
                } else {
                    BaseApplication.app.dm.outParams.pickNo = str;
                    searchInput.setText(str.toString());
                }
                refresh();
            }
        });

    }
}