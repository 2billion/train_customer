package com.train.train_manager.act;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.train.train_manager.R;
import com.train.train_manager.act.bean.LoginBean;
import com.train.train_manager.base.BaseActivity;
import com.train.train_manager.base.BaseApplication;
import com.train.train_manager.core.Cache;
import com.train.train_manager.core.NetCallback;

import java.io.IOException;
import java.lang.reflect.Type;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.name)
    EditText et_name;
    @BindView(R.id.password)
    EditText et_password;

    private NfcAdapter mNfcAdapter;
    private PendingIntent pi;
    private IntentFilter tagDetected;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        et_name.setSelection(et_name.length());
        findViewById(R.id.btn_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
        autoLogin();

        //初始化NfcAdapter
        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
        //初始化PendingIntent
        // 初始化PendingIntent，当有NFC设备连接上的时候，就交给当前Activity处理
        pi = PendingIntent.getActivity(this, 0, new Intent(this, getClass())
                .addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);

    }

    private void autoLogin() {
        if (BaseApplication.app.getAutoLogin()) {
            String card_id = Cache.i().getString("card_id");
            if (card_id != null && !card_id.equals("")) {
                loginCard(card_id);
            } else {
                et_name.setText(Cache.i().getString("name"));
                et_password.setText(Cache.i().getString("password"));
                login();
            }
        }
    }

    private void gotoMainAct() {
        Intent i = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(i);
        finish();
    }

    private void login() {
        final String name = et_name.getText().toString();
        final String password = et_password.getText().toString();
        BaseApplication.app.net.checkinPwd(new NetCallback() {
            @Override
            public void failure(Call call, IOException e) {
            }

            @Override
            public void response(Call call, String responseStr) throws IOException {
                Type cvbType = new TypeToken<LoginBean>() {
                }.getType();
                LoginBean bean = new Gson().fromJson(responseStr, cvbType);
                if (bean.isOK()) {
                    BaseApplication.app.showToast("登录成功");
                    BaseApplication.app.setAutoLogin(true);
                    BaseApplication.app.dm.token = bean.data.token;
                    Cache.i().setString("name", "" + name);
                    Cache.i().setString("password", "" + password);
                    BaseApplication.app.dm.userName = bean.data.userName;
                    finish();
                    gotoMainAct();
                } else {
                    BaseApplication.app.showToast(bean.msg);
                }
            }
        }, name, password);
    }

    private void loginCard(final String card_id) {
        BaseApplication.app.net.checkinCard(new NetCallback() {
            @Override
            public void failure(Call call, IOException e) {
            }

            @Override
            public void response(Call call, String responseStr) throws IOException {
                Type cvbType = new TypeToken<LoginBean>() {
                }.getType();
                LoginBean bean = new Gson().fromJson(responseStr, cvbType);
                if (bean.isOK()) {
                    BaseApplication.app.showToast("登录成功");
                    BaseApplication.app.setAutoLogin(true);
                    BaseApplication.app.dm.token = bean.data.token;
                    BaseApplication.app.dm.userName = bean.data.userName;
                    Cache.i().setString("card_id", "" + card_id);
                    finish();
                    gotoMainAct();
                } else {
                    BaseApplication.app.showToast(bean.msg);
                }
            }
        }, card_id);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mNfcAdapter.enableForegroundDispatch(this, pi, null, null);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        // 当前app正在前端界面运行，这个时候有intent发送过来，那么系统就会调用onNewIntent回调方法，将intent传送过来
        // 我们只需要在这里检验这个intent是否是NFC相关的intent，如果是，就调用处理方法
        if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(intent.getAction())) {
            processIntent(intent);
        }
    }

    /**
     * Parses the NDEF Message from the intent and prints to the TextView
     */
    private void processIntent(Intent intent) {
        //取出封装在intent中的TAG
        Tag tagFromIntent = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        String CardId = ByteArrayToHexString(tagFromIntent.getId());
        BaseApplication.showToast("CardId:" + CardId);
        loginCard(CardId);
    }

    private String ByteArrayToHexString(byte[] inarray) {
        int i, j, in;
        String[] hex = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A",
                "B", "C", "D", "E", "F"};
        String out = "";


        for (j = 0; j < inarray.length; ++j) {
            in = (int) inarray[j] & 0xff;
            i = (in >> 4) & 0x0f;
            out += hex[i];
            i = in & 0x0f;
            out += hex[i];
        }
        return out;
    }

}
