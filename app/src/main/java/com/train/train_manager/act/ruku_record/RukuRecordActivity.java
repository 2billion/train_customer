package com.train.train_manager.act.ruku_record;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import com.train.train_manager.act.bean.BaseBean;
import com.train.train_manager.act.bean.InAListBean;
import com.train.train_manager.base.BaseActivity;
import com.train.train_manager.base.BaseApplication;
import com.train.train_manager.core.NetCallback;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.Call;

public class RukuRecordActivity extends BaseActivity {

    View search;
    RefreshLayout refreshLayout;
    ListView list;

    EditText searchInput;
    View searchBtn;
    View right_view_btn;

    RukuRecordAdapter adapter;
    RukuRecordRightView rightView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ruku_record);
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
        adapter = new RukuRecordAdapter(this);
        list.setAdapter(adapter);

        searchInput = findViewById(R.id.search_input);
        searchInput.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            public void afterTextChanged(Editable s) {
                BaseApplication.app.dm.ina_transNo = s.toString();
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
        rightView = new RukuRecordRightView(this, findViewById(R.id.right_search_view));
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

        //    获取数据放最后
        getDate();

    }


    public int page = 1;
    public int pageSize = 10;

    private void getDate() {
        String transNo = BaseApplication.app.dm.ina_transNo;
        String timeStart = BaseApplication.app.dm.ina_timeStart;
        String timeEnd = BaseApplication.app.dm.ina_timeEnd;
        int status = BaseApplication.app.dm.ina_status;


        BaseApplication.app.net.listInA(new NetCallback() {
            @Override
            public void failure(Call call, IOException e) {
                finishLoad();
            }

            @Override
            public void response(Call call, String responseStr) throws IOException {
                Type cvbType = new TypeToken<InAListBean>() {
                }.getType();
                InAListBean bean = new Gson().fromJson(responseStr, cvbType);
                if (bean.isOK()) {
                    //刷新则删除列表
                    if (page == 1) {
                        BaseApplication.app.dm.listInA.clear();
                    }
                    BaseApplication.app.dm.listInA.addAll(bean.data.data);
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
        }, page + "", pageSize + "", transNo, timeStart, timeEnd, status + "");

    }

    public void finishLoad() {
        if (this.page == 1) {
            refreshLayout.finishRefresh();
        } else {
            refreshLayout.finishLoadMore();
        }
    }

    public void refresh() {
        RukuRecordActivity.this.page = 1;
        getDate();
    }

    public void loadMore() {
        RukuRecordActivity.this.page++;
        getDate();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BaseApplication.app.dm.ina_transNo = "";
    }

    //    删除一类入库
    public void delete_ruku_record(final String transNo) {
        new AlertDialog.Builder(this).setMessage("确认要删除这条入库单吗？")
                .setNeutralButton("取消", null)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        BaseApplication.app.net.deleteInA(new NetCallback() {
                            public void failure(Call call, IOException e) {
                            }

                            public void response(Call call, String responseStr) throws IOException {
                                BaseBean bean = new BaseBean().onBack(responseStr);
                                if (bean.isOK()) {
                                    refresh();
                                }
                                BaseApplication.app.showToast(bean.msg);
                            }
                        }, transNo);
                    }
                }).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (BaseApplication.app.goHome == true) {
            finish();
        } else {
            refresh();
        }
    }
}