package com.train.train_manager.act.mine;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.linchaolong.android.imagepicker.ImagePicker;
import com.linchaolong.android.imagepicker.cropper.CropImage;
import com.linchaolong.android.imagepicker.cropper.CropImageView;
import com.squareup.picasso.Picasso;
import com.train.train_manager.R;
import com.train.train_manager.act.bean.BaseBean;
import com.train.train_manager.act.bean.UserBean;
import com.train.train_manager.act.bean.UserDataBean;
import com.train.train_manager.base.BaseActivity;
import com.train.train_manager.base.BaseApplication;
import com.train.train_manager.core.Net;
import com.train.train_manager.core.NetCallback;

import java.io.IOException;
import java.lang.reflect.Type;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;

public class MineInfoActivity extends BaseActivity {

    @BindView(R.id.btn_left)
    View btn_left;

    @BindView(R.id.btn_right)
    View btn_right;

    @BindView(R.id.customer_name)
    EditText customer_name;

    @BindView(R.id.customer_sex)
    EditText customerSex;

    @BindView(R.id.customer_tel)
    EditText customer_tel;

    @BindView(R.id.customer_mail)
    EditText customer_mail;

    @BindView(R.id.customer_addr)
    EditText customer_addr;

    @BindView(R.id.avatar)
    ImageView avatar;

    @BindView(R.id.avatar_item)
    View avatar_item;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mine_info);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        btn_left.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
        btn_right.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(MineInfoActivity.this, MineInfoUpdateActivity.class));
            }
        });
        UserBean bean = BaseApplication.app.dm.userBean;
        if (bean != null) {
            if (bean.customerName != null) {
                customer_name.setText(bean.customerName);
                customer_name.setSelection(customer_name.getText().toString().length());
            }
            if (bean.customerSex > -1) {
                customerSex.setText(bean.customerSex == 0 ? "男" : "女");
                customerSex.setSelection(customerSex.getText().toString().length());
            }
            if (bean.customerTel != null) {
                customer_tel.setText(bean.customerTel);
            }
            if (bean.customerMail != null) {
                customer_mail.setText(bean.customerMail);
            }
            if (bean.customerAddr != null) {
                customer_addr.setText(bean.customerAddr);
            }
        }
        customer_name.setEnabled(false);
        customerSex.setEnabled(false);
        customer_tel.setEnabled(false);
        customer_mail.setEnabled(false);
        customer_addr.setEnabled(false);

        load_avatar();
        initImagePicker();
        avatar_item.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                takePhoto();
            }
        });

    }

    private void takePhoto() {
        imagePicker.startCamera(MineInfoActivity.this, new ImagePicker.Callback() {
            public void onPickImage(Uri imageUri) {
                Log.e("app", "onPickImage=============== " + imageUri.getPath());

            }

            public void onCropImage(Uri imageUri) {
                Log.e("app", "onCropImage=============== " + imageUri.getPath());
                avatar.setImageURI(imageUri);
                path = imageUri.getPath();
                BaseApplication.showToast("裁剪成功，开始上传");
                upload_avatar();
                //                draweeView.getHierarchy().setRoundingParams(RoundingParams.asCircle());
            }


            // 自定义裁剪配置
            public void cropConfig(CropImage.ActivityBuilder builder) {
                builder
                        // 是否启动多点触摸
                        .setMultiTouchEnabled(false)
                        // 设置网格显示模式
                        .setGuidelines(CropImageView.Guidelines.OFF)
                        // 圆形/矩形
                        .setCropShape(CropImageView.CropShape.RECTANGLE)
                        // 调整裁剪后的图片最终大小
                        .setRequestedSize(200, 200)
                        // 宽高比
                        .setAspectRatio(1, 1);
            }
        });

    }

    ImagePicker imagePicker;

    private void initImagePicker() {
        imagePicker = new ImagePicker();
        // 设置标题
        imagePicker.setTitle("设置头像");
        // 设置是否裁剪图片
        imagePicker.setCropImage(true);
        // 启动图片选择器
        //        imagePicker.startChooser(this, new ImagePicker.Callback() {
        //            // 选择图片回调
        //            @Override
        //            public void onPickImage(Uri imageUri) {
        //            }
        //
        //            // 裁剪图片回调
        //            @Override
        //            public void onCropImage(Uri imageUri) {
        //                Log.e("app", "onCropImage=============== " + imageUri.getPath());
        //                avatar.setImageURI(imageUri);
        //                //                draweeView.getHierarchy().setRoundingParams(RoundingParams.asCircle());
        //            }
        //
        //            // 自定义裁剪配置
        //            @Override
        //            public void cropConfig(CropImage.ActivityBuilder builder) {
        //                builder
        //                        // 是否启动多点触摸
        //                        .setMultiTouchEnabled(false)
        //                        // 设置网格显示模式
        //                        .setGuidelines(CropImageView.Guidelines.OFF)
        //                        // 圆形/矩形
        //                        .setCropShape(CropImageView.CropShape.RECTANGLE)
        //                        // 调整裁剪后的图片最终大小
        //                        .setRequestedSize(200, 200)
        //                        // 宽高比
        //                        .setAspectRatio(1, 1);
        //            }
        //
        //            // 用户拒绝授权回调
        //            @Override
        //            public void onPermissionDenied(int requestCode, String[] permissions,
        //                                           int[] grantResults) {
        //            }
        //        });
    }

    private void load_avatar() {
        String url = Net.HOST + BaseApplication.app.dm.userBean.customerImg;
        Picasso.get()
                .load(url)
                .resize(200, 200)
                .centerCrop()
                .into(avatar);
    }

    public String path;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        imagePicker.onActivityResult(MineInfoActivity.this, requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        imagePicker.onRequestPermissionsResult(MineInfoActivity.this, requestCode, permissions, grantResults);
    }

    private void upload_avatar() {
        BaseApplication.app.net.uploadFile(new NetCallback() {
            public void failure(Call call, IOException e) {
            }

            public void response(Call call, String responseStr) throws IOException {
                Type cvbType = new TypeToken<BaseBean>() {
                }.getType();
                BaseBean bean = new Gson().fromJson(responseStr, cvbType);
                if (bean.isOK()) {
                    BaseApplication.showToast("头像上传成功");
                    BaseApplication.app.showToast(bean.msg);
                    //                    更新用户信息
                    updateUserInfo();
                } else {
                    BaseApplication.app.showToast("请求失败" + bean.msg);
                }
            }
        }, this.path);
    }

    private void updateUserInfo() {
        BaseApplication.app.net.findMyInfo(new NetCallback() {
            public void failure(Call call, IOException e) {
            }

            public void response(Call call, String responseStr) throws IOException {
                Type cvbType = new TypeToken<UserDataBean>() {
                }.getType();
                UserDataBean bean = new Gson().fromJson(responseStr, cvbType);
                if (bean.isOK()) {
                    BaseApplication.app.dm.userBean = bean.data;
                } else {
                    BaseApplication.app.showToast("请求失败" + bean.msg);
                }
            }
        });

    }
}
