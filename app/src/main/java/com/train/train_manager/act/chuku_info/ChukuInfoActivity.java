package com.train.train_manager.act.chuku_info;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.train.train_manager.R;
import com.train.train_manager.act.bean.InABean;
import com.train.train_manager.act.bean.OutInfoBean;
import com.train.train_manager.act.bean.OutInfoListBean;
import com.train.train_manager.base.BaseActivity;
import com.train.train_manager.base.BaseApplication;
import com.train.train_manager.core.NetCallback;

import java.io.IOException;
import java.lang.reflect.Type;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;

public class ChukuInfoActivity extends BaseActivity {

    RefreshLayout refreshLayout;
    ListView list;
    ChukuInfoAdapter adapter;

    @BindView(R.id.btn_left)
    View btn_left;

    @BindView(R.id.btn_right)
    View btn_right;

    @BindView(R.id.bottom_btn_1)
    Button bottom_btn_1;

    @BindView(R.id.bottom_btn_2)
    Button bottom_btn_2;

    @BindView(R.id.top_info)
    TextView top_info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chuku_info);
        ButterKnife.bind(this);
        init();
    }

    public void init() {

        refreshLayout = findViewById(R.id.refreshLayout);
        refreshLayout.setEnableAutoLoadMore(false);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            public void onRefresh(RefreshLayout refreshlayout) {
                refresh();
            }
        });
        refreshLayout.setEnableLoadMore(false);
        //        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
        //            @Override
        //            public void onLoadMore(RefreshLayout refreshlayout) {
        //                loadMore();
        //            }
        //        });
        list = findViewById(R.id.list);
        adapter = new ChukuInfoAdapter(this);
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
                BaseApplication.showToast("扫码");
            }
        });

        //    获取数据放最后
        getDate();

    }

    private void ruku() {
        BaseApplication.showToast("入库");
    }

    public int page = 1;
    public int pageSize = 10;

    private void getDate() {
        String pickId = BaseApplication.app.dm.outBean.pickId + "";

        BaseApplication.app.net.pick_detail(new NetCallback() {
            @Override
            public void failure(Call call, IOException e) {
                finishLoad();
            }

            @Override
            public void response(Call call, String responseStr) throws IOException {
                Type cvbType = new TypeToken<OutInfoListBean>() {
                }.getType();
                OutInfoListBean bean = new Gson().fromJson(responseStr, cvbType);
                if (bean.isOK()) {
                    BaseApplication.app.dm.outInfoListBean = bean;
                    //刷新则删除列表
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
        }, pickId);

    }

    private void updateUI() {
        adapter.notifyDataSetChanged();
        try {
            bottom_btn_1.setText("记录数：" + BaseApplication.app.dm.outInfoListBean.data.rqPickDetailList.size());
            OutInfoBean bean = BaseApplication.app.dm.outInfoListBean.data;
            String str = "日期：" + bean.deliveryTime + "  联系人：" + bean.contractNo;
            str += "\n发料原因：" + bean.reason + "\n发送到:" + bean.deliveryTo;
            top_info.setText(str);
            String status_str = "";
            switch (bean.status) {
                case 0:
                    status_str = "待下架";
                    bottom_btn_2.setText("接单");
                    bottom_btn_2.setClickable(true);
                    bottom_btn_2.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                        BaseApplication.showToast("接单");
                        }
                    });
                    break;
                case 1:
                    status_str = "下架中";
                    break;
                case 2:
                    status_str = "待交接";
                    bottom_btn_2.setText("确认交接完成");
                    bottom_btn_2.setClickable(true);
                    break;
                case 3:
                    status_str = "已完成";
                    bottom_btn_2.setText(status_str);
                    bottom_btn_2.setClickable(false);
                    break;
            }
            bottom_btn_2.setText(status_str);

        } catch (Exception e) {
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
        ChukuInfoActivity.this.page = 1;
        getDate();
    }

    public void loadMore() {
        ChukuInfoActivity.this.page++;
        getDate();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BaseApplication.app.dm.info_InABean = null;
    }
}