package com.train.train_manager.act;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.train.train_manager.R;
import com.train.train_manager.act.mine.MineActivity;
import com.train.train_manager.act.ruku.RukuActivity;
import com.train.train_manager.act.ruku_record.RukuRecordActivity;
import com.train.train_manager.base.BaseActivity;
import com.train.train_manager.base.BaseApplication;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @BindView(R.id.input_kuwei)
    EditText kuwei;
    @BindView(R.id.input_bst)
    EditText bst;
    @BindView(R.id.submit)
    Button submit;

    @BindView(R.id.main_btn_ico_1)
    View main_btn_ico_1;
    @BindView(R.id.main_btn_ico_2)
    View main_btn_ico_2;
    @BindView(R.id.main_btn_ico_3)
    View main_btn_ico_3;
    @BindView(R.id.main_btn_ico_4)
    View main_btn_ico_4;
    @BindView(R.id.main_btn_ico_5)
    View main_btn_ico_5;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        ButterKnife.bind(this);
        //初始化
        init();
    }

    private void init() {
    }

    @OnClick({R.id.main_btn_ico_1, R.id.main_btn_ico_2, R.id.main_btn_ico_3, R.id.main_btn_ico_4, R.id.main_btn_ico_5})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.main_btn_ico_1:
                startActivity(new Intent(MainActivity.this, RukuActivity.class));
                break;
            case R.id.main_btn_ico_2:
                startActivity(new Intent(MainActivity.this, RukuRecordActivity.class));
                break;
            case R.id.main_btn_ico_3:
                startActivity(new Intent(MainActivity.this, RukuActivity.class));
                break;
            case R.id.main_btn_ico_4:
                startActivity(new Intent(MainActivity.this, RukuActivity.class));
                break;
            case R.id.main_btn_ico_5:
                startActivity(new Intent(MainActivity.this, MineActivity.class));
                break;
        }


    }

    // 定义一个变量，来标识是否退出
    private static boolean isExit = false;

    Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            isExit = false;
        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void exit() {
        if (!isExit) {
            isExit = true;
            BaseApplication.showToast("再按一次退出程序");
            // 利用handler延迟发送更改状态信息
            mHandler.sendEmptyMessageDelayed(0, 2000);
        } else {
            finish();
            System.exit(0);
        }
    }
}
