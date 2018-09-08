package com.train.train_customer.act.product;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.train.train_customer.R;
import com.train.train_customer.act.bean.TsTypeBean;
import com.train.train_customer.base.BaseApplication;
import com.train.train_customer.core.NetCallback;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.Call;

/**
 * 右侧搜索
 * <p>
 * "partName":"不" -1
 * partNo":"338 -2
 * "buPartNo":"978" -3
 */
public class ProductRightView {
    View view;
    TagFlowLayout id_flowlayout;
    ProductFragment fragment;
    TagAdapter tagAdapter;
    View right_search_view;

    EditText input1;
    EditText input2;
    EditText input3;
    View input_del_1;
    View input_del_2;
    View input_del_3;

    public ProductRightView(ProductFragment fragment, View view) {
        this.fragment = fragment;
        this.view = view;

        initRightView();//    右侧数据
    }

    private void initRightView() {
        right_search_view = this.view.findViewById(R.id.right_search_view);
        id_flowlayout = this.view.findViewById(R.id.id_flowlayout);
        //        搜索
        view.findViewById(R.id.ok).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                BaseApplication.app.dm.product_partName = input1.getText().toString();
                BaseApplication.app.dm.product_partNo = input2.getText().toString();
                BaseApplication.app.dm.product_buPartNo = input3.getText().toString();
                switchVisible();
                fragment.refresh();
                fragment.hideKeyBoard(v);
                fragment.searchInput.setText(input2.getText().toString() + "");
            }
        });
        //        重置
        view.findViewById(R.id.reset).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                input1.setText("");
                input2.setText("");
                input3.setText("");
                initDelUI();
                tagAdapter.setSelectedList();
            }
        });

        input1 = this.view.findViewById(R.id.et_name);
        input1.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                input_del_1.setVisibility(TextUtils.isEmpty(input1.getText()) ? View.INVISIBLE : View.VISIBLE);
            }

            public void afterTextChanged(Editable s) {
            }
        });
        input2 = this.view.findViewById(R.id.et_bst_wz_code);
        input2.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                input_del_2.setVisibility(TextUtils.isEmpty(input2.getText()) ? View.INVISIBLE : View.VISIBLE);
            }

            public void afterTextChanged(Editable s) {
            }
        });
        input3 = this.view.findViewById(R.id.et_lj_wz_code);
        input3.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                input_del_3.setVisibility(TextUtils.isEmpty(input3.getText()) ? View.INVISIBLE : View.VISIBLE);
            }

            public void afterTextChanged(Editable s) {
            }
        });

        input_del_1 = this.view.findViewById(R.id.input_del_1);
        input_del_1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                input1.setText("");
            }
        });
        input_del_2 = this.view.findViewById(R.id.input_del_2);
        input_del_2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                input2.setText("");
            }
        });
        input_del_3 = this.view.findViewById(R.id.input_del_3);
        input_del_3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                input3.setText("");
            }
        });

        view.findViewById(R.id.hide).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                switchVisible();
                fragment.searchInput.setText(input2.getText().toString() + "");
            }
        });
        initTypeData();
    }

    /**
     * 搜索筛选数据
     */
    private void initTypeData() {
        if (BaseApplication.app.dm.tsTypeList == null || BaseApplication.app.dm.tsTypeList.size() == 0) {
            BaseApplication.app.net.findTsTypeList(new NetCallback() {
                @Override
                public void failure(Call call, IOException e) {
                }

                @Override
                public void response(Call call, String responseStr) throws IOException {
                    Type cvbType = new TypeToken<TsTypeBean>() {
                    }.getType();
                    TsTypeBean bean = new Gson().fromJson(responseStr, cvbType);
                    BaseApplication.app.dm.tsTypeList = bean.data;

                    fragment.getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            initTagAdapter();
                        }
                    });
                }
            });
        } else {
            initTagAdapter();
        }
    }

    public void switchVisible() {
        int show = this.right_search_view.getVisibility();
        if (show == View.INVISIBLE) {
            this.right_search_view.setVisibility(View.VISIBLE);
        } else {
            this.right_search_view.setVisibility(View.INVISIBLE);
        }
        input1.setText(BaseApplication.app.dm.product_partName);
        input2.setText(BaseApplication.app.dm.product_partNo);
        input3.setText(BaseApplication.app.dm.product_buPartNo);
    }

    private void initTagAdapter() {
        tagAdapter = new TagAdapter(BaseApplication.app.dm.tsTypeList) {
            public View getView(FlowLayout parent, int position, Object o) {
                TextView tag = (TextView) View.inflate(fragment.getContext(), R.layout.common_right_search_item, null);
                String str = BaseApplication.app.dm.tsTypeList.get(position).tsType
                        + "(" + BaseApplication.app.dm.tsTypeList.get(position).contractNo + ")";
                tag.setText(str);
                return tag;
            }

            @Override
            public void onSelected(int position, View view) {
                super.onSelected(position, view);
                String str = BaseApplication.app.dm.tsTypeList.get(position).tsType
                        + "(" + BaseApplication.app.dm.tsTypeList.get(position).contractNo + ")";
                BaseApplication.app.dm.product_tsType = str;
            }

            @Override
            public void unSelected(int position, View view) {
                super.unSelected(position, view);
                BaseApplication.app.dm.product_tsType = "";
            }

        };
        id_flowlayout.setAdapter(tagAdapter);
        tagAdapter.notifyDataChanged();
        initDelUI();
    }

    private void initDelUI() {
        boolean del1 = TextUtils.isEmpty(input1.getText());
        boolean del2 = TextUtils.isEmpty(input2.getText());
        boolean del3 = TextUtils.isEmpty(input3.getText());
        input_del_1.setVisibility(del1 ? View.INVISIBLE : View.VISIBLE);
        input_del_2.setVisibility(del2 ? View.INVISIBLE : View.VISIBLE);
        input_del_3.setVisibility(del3 ? View.INVISIBLE : View.VISIBLE);
    }


}
