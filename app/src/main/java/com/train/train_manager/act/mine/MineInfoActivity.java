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

    @BindView(R.id.user_id)
    EditText user_id;

    @BindView(R.id.user_name)
    EditText user_name;

    @BindView(R.id.user_desc)
    EditText user_desc;

    @BindView(R.id.dept_name)
    EditText dept_name;

    @BindView(R.id.post)
    EditText post;

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
            if (bean.userId != null) {
                user_id.setText(bean.userId);
                user_id.setSelection(user_id.getText().toString().length());
            }
            if (bean.userName != null) {
                user_name.setText(bean.userName);
            }
            if (bean.userDesc != null) {
                user_desc.setText(bean.userDesc);
            }
            if (bean.deptName != null) {
                dept_name.setText(bean.deptName);
            }
            if (bean.post != null) {
                post.setText(bean.post);
            }
        }
        user_id.setEnabled(false);
        user_name.setEnabled(false);
        user_desc.setEnabled(false);
        dept_name.setEnabled(false);
        post.setEnabled(false);

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
    }

    private void load_avatar() {
        if (BaseApplication.app.dm.userBean == null || BaseApplication.app.dm.userBean.userHeader == null) {
            return;
        }
        String url = Net.HOST + BaseApplication.app.dm.userBean.userHeader;
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
        BaseApplication.app.net.uploadAvatar(new NetCallback() {
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
        Log.e("app", "-----------updateUserInfo");
        BaseApplication.app.net.info(new NetCallback() {
            public void failure(Call call, IOException e) {
            }

            public void response(Call call, String responseStr) throws IOException {
                Type cvbType = new TypeToken<UserDataBean>() {
                }.getType();
                final UserDataBean bean = new Gson().fromJson(responseStr, cvbType);
                if (bean.isOK()) {
                    BaseApplication.app.dm.userBean = bean.data;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (bean.data.userId != null) {
                                user_id.setText(bean.data.userId);
                                user_id.setSelection(user_id.getText().toString().length());
                            }
                            if (bean.data.userName != null) {
                                user_name.setText(bean.data.userName);
                            }
                            if (bean.data.userDesc != null) {
                                user_desc.setText(bean.data.userDesc);
                            }
                            if (bean.data.deptName != null) {
                                dept_name.setText(bean.data.deptName);
                            }
                            if (bean.data.post != null) {
                                post.setText(bean.data.post);
                            }
                        }
                    });
                } else {
                    BaseApplication.app.showToast("请求失败" + bean.msg);
                }
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        updateUserInfo();
    }

}
