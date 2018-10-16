package com.train.train_manager.act;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.honeywell.aidc.AidcManager;
import com.honeywell.aidc.BarcodeFailureEvent;
import com.honeywell.aidc.BarcodeReadEvent;
import com.honeywell.aidc.BarcodeReader;
import com.honeywell.aidc.UnsupportedPropertyException;
import com.honeywell.barcodeexample.ClientBarcodeActivity;
import com.train.train_manager.R;
import com.train.train_manager.act.chuku.ChuKuActivity;
import com.train.train_manager.act.kucun.KuCunActivity;
import com.train.train_manager.act.mine.MineActivity;
import com.train.train_manager.act.ruku.RukuActivity;
import com.train.train_manager.act.ruku_record.RukuRecordActivity;
import com.train.train_manager.base.BaseActivity;
import com.train.train_manager.base.BaseApplication;
import com.train.train_manager.base.Reader;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements BarcodeReader.BarcodeListener{

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

    @BindView(R.id.input_del_1)
    View input_del_1;
    @BindView(R.id.input_del_2)
    View input_del_2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        ButterKnife.bind(this);
        //初始化
        init();
    }

    private static BarcodeReader barcodeReader;
    private AidcManager manager;


    private void init() {

        submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, KuCunActivity.class));
            }
        });

        kuwei.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                input_del_1.setVisibility(TextUtils.isEmpty(kuwei.getText()) ? View.INVISIBLE : View.VISIBLE);
            }

            public void afterTextChanged(Editable s) {
                BaseApplication.app.dm.kuCunParams.location = s.toString();
            }
        });
        bst.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                input_del_2.setVisibility(TextUtils.isEmpty(bst.getText()) ? View.INVISIBLE : View.VISIBLE);
            }

            public void afterTextChanged(Editable s) {
                BaseApplication.app.dm.kuCunParams.bstPartNo = s.toString();
            }
        });

        input_del_1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                kuwei.setText("");
            }
        });
        input_del_2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                bst.setText("");
            }
        });

        this.reader = new Reader() {
            @Override
            public void back(String code) {
                on_back(code);
            }
        };

        AidcManager.create(this, new AidcManager.CreatedCallback() {

            @Override
            public void onCreated(AidcManager aidcManager) {
                manager = aidcManager;
                barcodeReader = manager.createBarcodeReader();

                if (barcodeReader != null) {

                    // register bar code event listener
                    barcodeReader.addBarcodeListener(MainActivity.this);

                    // set the trigger mode to client control
                    try {
                        barcodeReader.setProperty(BarcodeReader.PROPERTY_TRIGGER_CONTROL_MODE,
                                BarcodeReader.TRIGGER_CONTROL_MODE_CLIENT_CONTROL);
                    } catch (UnsupportedPropertyException e) {

                    }
                    // register trigger state change listener
//                    barcodeReader.addTriggerListener(MainActivity.this);

                    Map<String, Object> properties = new HashMap<String, Object>();
                    // Set Symbologies On/Off
                    properties.put(BarcodeReader.PROPERTY_CODE_128_ENABLED, true);
                    properties.put(BarcodeReader.PROPERTY_GS1_128_ENABLED, true);
                    properties.put(BarcodeReader.PROPERTY_QR_CODE_ENABLED, true);
                    properties.put(BarcodeReader.PROPERTY_CODE_39_ENABLED, true);
                    properties.put(BarcodeReader.PROPERTY_DATAMATRIX_ENABLED, true);
                    properties.put(BarcodeReader.PROPERTY_UPC_A_ENABLE, true);
                    properties.put(BarcodeReader.PROPERTY_EAN_13_ENABLED, false);
                    properties.put(BarcodeReader.PROPERTY_AZTEC_ENABLED, false);
                    properties.put(BarcodeReader.PROPERTY_CODABAR_ENABLED, false);
                    properties.put(BarcodeReader.PROPERTY_INTERLEAVED_25_ENABLED, false);
                    properties.put(BarcodeReader.PROPERTY_PDF_417_ENABLED, false);
                    // Set Max Code 39 barcode length
                    properties.put(BarcodeReader.PROPERTY_CODE_39_MAXIMUM_LENGTH, 10);
                    // Turn on center decoding
                    properties.put(BarcodeReader.PROPERTY_CENTER_DECODE, true);
                    // Disable bad read response, handle in onFailureEvent
                    properties.put(BarcodeReader.PROPERTY_NOTIFICATION_BAD_READ_ENABLED, false);
                    // Apply the settings
                    barcodeReader.setProperties(properties);
                }
            }
        });
    }

    @Override
    public void onBarcodeEvent(final BarcodeReadEvent event) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // update UI to reflect the data
                log("============================ "+event.getBarcodeData());

            }
        });
    }

    @Override
    public void onFailureEvent(BarcodeFailureEvent arg0) {
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                BaseApplication.showToast("-----------------wrong");
            }
        });
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
                startActivity(new Intent(MainActivity.this, ChuKuActivity.class));
                break;
            case R.id.main_btn_ico_4:
                BaseApplication.showToast("此功能还未开放");
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


    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void onDestroy() {
//        BaseApplication.app.needClearReader = true;
        super.onDestroy();

        if (barcodeReader != null) {
            // close BarcodeReader to clean up resources.
            barcodeReader.close();
            barcodeReader = null;
        }

        if (manager != null) {
            // close AidcManager to disconnect from the scanner service.
            // once closed, the object can no longer be used.
            manager.close();
        }
    }

    public void on_back(final String str) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (str.equals("")) {
                    return;
                } else if (str.startsWith("#")) {
                    String str_real = str.replace("#", "");
                    BaseApplication.app.dm.kuCunParams.location = str_real;
                    kuwei.setText(str_real.toString());
                } else {
                    BaseApplication.app.dm.kuCunParams.bstPartNo = str;
                    bst.setText(str.toString());
                }
                startActivity(new Intent(MainActivity.this, KuCunActivity.class));
            }
        });
    }

}
