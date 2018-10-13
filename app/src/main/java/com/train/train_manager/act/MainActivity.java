package com.train.train_manager.act;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.honeywell.aidc.AidcManager;
import com.honeywell.aidc.BarcodeFailureEvent;
import com.honeywell.aidc.BarcodeReadEvent;
import com.honeywell.aidc.BarcodeReader;
import com.honeywell.aidc.ScannerNotClaimedException;
import com.honeywell.aidc.ScannerUnavailableException;
import com.honeywell.aidc.TriggerStateChangeEvent;
import com.honeywell.aidc.UnsupportedPropertyException;
import com.train.train_manager.R;
import com.train.train_manager.act.chuku.ChuKuActivity;
import com.train.train_manager.act.kucun.KuCunActivity;
import com.train.train_manager.act.mine.MineActivity;
import com.train.train_manager.act.ruku.RukuActivity;
import com.train.train_manager.act.ruku_record.RukuRecordActivity;
import com.train.train_manager.base.BaseActivity;
import com.train.train_manager.base.BaseApplication;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements BarcodeReader.BarcodeListener,
        BarcodeReader.TriggerListener {

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

        AidcManager.create(this, new AidcManager.CreatedCallback() {
            @Override
            public void onCreated(AidcManager aidcManager) {
                manager = aidcManager;
                barcodeReader = manager.createBarcodeReader();

                // set the trigger mode to client control
                try {
                    barcodeReader.setProperty(BarcodeReader.PROPERTY_TRIGGER_CONTROL_MODE,
                            BarcodeReader.TRIGGER_CONTROL_MODE_CLIENT_CONTROL);
                } catch (UnsupportedPropertyException e) {
                    BaseApplication.showToast("Failed to apply properties");
                }

                barcodeReader.addBarcodeListener(MainActivity.this);
                barcodeReader.addTriggerListener(MainActivity.this);
                Log.e("app", "init OK");


                //                Map<String, Object> properties = new HashMap<String, Object>();
                //                // Set Symbologies On/Off
                //                properties.put(BarcodeReader.PROPERTY_CODE_128_ENABLED, true);
                //                properties.put(BarcodeReader.PROPERTY_GS1_128_ENABLED, true);
                //                properties.put(BarcodeReader.PROPERTY_QR_CODE_ENABLED, true);
                //                properties.put(BarcodeReader.PROPERTY_CODE_39_ENABLED, true);
                //                properties.put(BarcodeReader.PROPERTY_DATAMATRIX_ENABLED, true);
                //                properties.put(BarcodeReader.PROPERTY_UPC_A_ENABLE, true);
                //                properties.put(BarcodeReader.PROPERTY_EAN_13_ENABLED, false);
                //                properties.put(BarcodeReader.PROPERTY_AZTEC_ENABLED, false);
                //                properties.put(BarcodeReader.PROPERTY_CODABAR_ENABLED, false);
                //                properties.put(BarcodeReader.PROPERTY_INTERLEAVED_25_ENABLED, false);
                //                properties.put(BarcodeReader.PROPERTY_PDF_417_ENABLED, false);
                //                // Set Max Code 39 barcode length
                //                properties.put(BarcodeReader.PROPERTY_CODE_39_MAXIMUM_LENGTH, 10);
                //                // Turn on center decoding
                //                properties.put(BarcodeReader.PROPERTY_CENTER_DECODE, true);
                //                // Enable bad read response
                //                properties.put(BarcodeReader.PROPERTY_NOTIFICATION_BAD_READ_ENABLED, true);
                //                // Apply the settings
                //                barcodeReader.setProperties(properties);
            }
        });


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
    public void onBarcodeEvent(final BarcodeReadEvent event) {
        Log.e("app", "============================================onBarcodeEvent");
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // update UI to reflect the data
                Log.e("app", "============================================");
                Log.e("app", "Barcode data: " + event.getBarcodeData());
                Log.e("app", "Character Set: " + event.getCharset());
                Log.e("app", "Code ID: " + event.getCodeId());
                Log.e("app", "AIM ID: " + event.getAimId());
                Log.e("app", "Timestamp: " + event.getTimestamp());
            }
        });
    }

    @Override
    public void onFailureEvent(BarcodeFailureEvent barcodeFailureEvent) {
        Log.e("app", "============================================onFailureEvent");
        runOnUiThread(new Runnable() {
            public void run() {
                Log.e("app", "=========== no data");
            }
        });
    }

    @Override
    public void onTriggerEvent(TriggerStateChangeEvent event) {
        Log.e("app", "============================================onTriggerEvent");
        try {
            // only handle trigger presses
            // turn on/off aimer, illumination and decoding
            barcodeReader.aim(event.getState());
            barcodeReader.light(event.getState());
            barcodeReader.decode(event.getState());

        } catch (ScannerNotClaimedException e) {
            e.printStackTrace();
            BaseApplication.showToast("Scanner is not claimed");
        } catch (ScannerUnavailableException e) {
            e.printStackTrace();
            BaseApplication.showToast("Scanner unavailable");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (barcodeReader != null) {
            try {
                barcodeReader.claim();
            } catch (ScannerUnavailableException e) {
                e.printStackTrace();
                BaseApplication.showToast("Scanner unavailable");
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (barcodeReader != null) {
            // release the scanner claim so we don't get any scanner
            // notifications while paused.
            barcodeReader.release();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (barcodeReader != null) {
            // unregister barcode event listener
            barcodeReader.removeBarcodeListener(this);

            // unregister trigger state change listener
            barcodeReader.removeTriggerListener(this);
        }
    }
}
