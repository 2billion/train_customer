package com.train.train_manager.act.ruku_add;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.train.train_manager.R;
import com.train.train_manager.act.bean.CompInABean;
import com.train.train_manager.act.bean.InAInfoListBean;
import com.train.train_manager.act.ruku.RukuOKActivity;
import com.train.train_manager.base.BaseActivity;
import com.train.train_manager.base.BaseApplication;
import com.train.train_manager.core.NetCallback;

import java.io.IOException;
import java.lang.reflect.Type;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;

public class RukuAddActivity extends BaseActivity {

    RefreshLayout refreshLayout;
    ListView list;
    RukuAddAdapter adapter;

    @BindView(R.id.btn_left)
    View btn_left;

    @BindView(R.id.btn_right)
    View btn_right;

    @BindView(R.id.bottom_btn_1)
    Button bottom_btn_1;

    @BindView(R.id.bottom_btn_2)
    Button bottom_btn_2;

    @BindView(R.id.view_none)
    View view_none;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ruku_add);
        ButterKnife.bind(this);
        init();
    }

    public void init() {
        refreshLayout = findViewById(R.id.refreshLayout);
        refreshLayout.setEnableAutoLoadMore(false);
        //        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
        //            public void onRefresh(RefreshLayout refreshlayout) {
        //                refresh();
        //            }
        //        });
        refreshLayout.setEnableLoadMore(false);
        refreshLayout.setEnableRefresh(false);
        //        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
        //            @Override
        //            public void onLoadMore(RefreshLayout refreshlayout) {
        //                loadMore();
        //            }
        //        });
        list = findViewById(R.id.list);
        adapter = new RukuAddAdapter(this);
        list.setAdapter(adapter);

        // 返回
        findViewById(R.id.btn_left).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });

        // 新增
        btn_right.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(RukuAddActivity.this, RukuDoAddActivity.class));
            }
        });


        bottom_btn_2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ruku();
            }
        });

    }

    private void ruku() {
        if (BaseApplication.app.dm.list_InAInfo.size() > 0) {
            new AlertDialog.Builder(this).setMessage("确定入库？")
                    .setNeutralButton("取消", null)
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            do_ruku();
                        }
                    }).show();
        } else {
            BaseApplication.showToast("还没有入库配件");
        }
    }

    private void do_ruku() {
        String transNo = BaseApplication.app.dm.inaAddBean.transNo;
        if (transNo == null || transNo == "") {
            return;
        }

        BaseApplication.app.net.compInA(new NetCallback() {
            @Override
            public void failure(Call call, IOException e) {
                finishLoad();
            }

            @Override
            public void response(Call call, String responseStr) throws IOException {
                Type cvbType = new TypeToken<CompInABean>() {
                }.getType();
                CompInABean bean = new Gson().fromJson(responseStr, cvbType);
                if (bean.isOK()) {
                    do_ruku_back(bean);
                } else {
                    BaseApplication.app.showToast("请求失败" + bean.msg);
                }
                finishLoad();
            }
        }, transNo);
    }

    private void do_ruku_back(CompInABean bean) {
        if (BaseApplication.app.dm.inaAddBean.transNo.equals(bean.data.transNo)) {
            BaseApplication.showToast("添加成功");
            BaseApplication.app.dm.inaAddBean.clear();
            startActivity(new Intent(RukuAddActivity.this, RukuOKActivity.class));
        }
    }

    public int page = 1;
    public int pageSize = 10;

    private void getDate() {
        //        String transNo = BaseApplication.app.dm.info_InABean.transNo;
        String transNo = BaseApplication.app.dm.inaAddBean.transNo;
        if (transNo == null || transNo == "") {
            updateUI();
            return;
        }

        BaseApplication.app.net.getDetailByTransNo(new NetCallback() {
            @Override
            public void failure(Call call, IOException e) {
                finishLoad();
            }

            @Override
            public void response(Call call, String responseStr) throws IOException {
                Type cvbType = new TypeToken<InAInfoListBean>() {
                }.getType();
                InAInfoListBean bean = new Gson().fromJson(responseStr, cvbType);
                if (bean.isOK()) {
                    //刷新则删除列表
                    if (page == 1) {
                        BaseApplication.app.dm.list_InAInfo.clear();
                    }
                    BaseApplication.app.dm.list_InAInfo.addAll(bean.data);
                    runOnUiThread(new Runnable() {
                        public void run() {
                            updateUI();
                        }
                    });
                } else {
                    BaseApplication.app.showToast("请求失败" + bean.msg);
                }
                finishLoad();
            }
        }, transNo);

    }

    private void updateUI() {
        adapter.notifyDataSetChanged();
        bottom_btn_1.setText("记录数：" + BaseApplication.app.dm.list_InAInfo.size());
        if (BaseApplication.app.dm.list_InAInfo.size() == 0) {
            view_none.setVisibility(View.VISIBLE);
        } else {
            view_none.setVisibility(View.GONE);
        }
    }

    public void finishLoad() {
        if (this.page == 1) {
            refreshLayout.finishRefresh();
        } else {
            refreshLayout.finishLoadMore();
        }
    }

    public void refresh() {
        RukuAddActivity.this.page = 1;
        getDate();
    }

    public void loadMore() {
        RukuAddActivity.this.page++;
        getDate();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BaseApplication.app.dm.info_InABean = null;
    }

    @Override
    protected void onResume() {
        log("onResume-----------------");
        super.onResume();
        refresh();
    }
}