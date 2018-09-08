package com.train.train_customer.act.order;

import android.app.DatePickerDialog;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.train.train_customer.R;
import com.train.train_customer.base.BaseApplication;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

/**
 * 右侧搜索
 * <p>
 * "partName":"不" -1
 * partNo":"338 -2
 * "buPartNo":"978" -3
 */
public class OrderRightView {
    View view;
    TagFlowLayout id_flowlayout;
    OrderFragment fragment;
    TagAdapter tagAdapter;
    View right_search_view;

    EditText input0;
    EditText input1;
    EditText input2;
    EditText input3;
    View input_del_0;
    View input_del_1;
    View input_del_2;
    View input_del_3;

    Button data_picker_1;
    Button data_picker_2;
    Button data_picker_3;
    Button data_picker_4;

    public OrderRightView(OrderFragment fragment, View view) {
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
                BaseApplication.app.dm.orderParamsBean.orderNo = input0.getText().toString();
                BaseApplication.app.dm.orderParamsBean.partName = input1.getText().toString();
                BaseApplication.app.dm.orderParamsBean.bstPartNo = input2.getText().toString();
                BaseApplication.app.dm.orderParamsBean.buPartNo = input3.getText().toString();

                BaseApplication.app.dm.orderParamsBean.orderTimeStart = getDateTime(data_picker_1);
                BaseApplication.app.dm.orderParamsBean.orderTimeEnd = getDateTime(data_picker_2);
                BaseApplication.app.dm.orderParamsBean.orderCompTimeStart = getDateTime(data_picker_3);
                BaseApplication.app.dm.orderParamsBean.orderCompTimeEnd = getDateTime(data_picker_4);

                switchVisible();
                fragment.refresh();
                fragment.hideKeyBoard(v);
                fragment.searchInput.setText(input2.getText().toString() + "");
            }
        });
        //        重置
        view.findViewById(R.id.reset).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                input0.setText("");
                input1.setText("");
                input2.setText("");
                input3.setText("");
                initDelUI();
                tagAdapter.setSelectedList();
                data_picker_1.setText("");
                data_picker_2.setText("");
                data_picker_3.setText("");
                data_picker_4.setText("");
                BaseApplication.app.dm.orderParamsBean.orderStatus = "";
            }
        });

        input0 = this.view.findViewById(R.id.et_order_no);
        input0.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                input_del_0.setVisibility(TextUtils.isEmpty(input0.getText()) ? View.INVISIBLE : View.VISIBLE);
            }

            public void afterTextChanged(Editable s) {

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
        input_del_0 = this.view.findViewById(R.id.input_del_order_no);
        input_del_0.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                input0.setText("");
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

        View.OnClickListener listener = new View.OnClickListener() {
            public void onClick(View v) {
                showDatePicker((Button) v);
            }
        };
        data_picker_1 = this.view.findViewById(R.id.data_picker_1);
        data_picker_2 = this.view.findViewById(R.id.data_picker_2);
        data_picker_3 = this.view.findViewById(R.id.data_picker_3);
        data_picker_4 = this.view.findViewById(R.id.data_picker_4);
        data_picker_1.setOnClickListener(listener);
        data_picker_2.setOnClickListener(listener);
        data_picker_3.setOnClickListener(listener);
        data_picker_4.setOnClickListener(listener);

        initTagAdapter();
    }

    public void switchVisible() {
        int show = this.right_search_view.getVisibility();
        if (show == View.INVISIBLE) {
            this.right_search_view.setVisibility(View.VISIBLE);
        } else {
            this.right_search_view.setVisibility(View.INVISIBLE);
        }
        input0.setText(BaseApplication.app.dm.orderParamsBean.orderNo);
        input1.setText(BaseApplication.app.dm.orderParamsBean.partName);
        input2.setText(BaseApplication.app.dm.orderParamsBean.bstPartNo);
        input3.setText(BaseApplication.app.dm.orderParamsBean.buPartNo);

    }

    private void initTagAdapter() {
        ArrayList<String> list = new ArrayList<>();
        list.add("全部");
        list.add("未完成");
        list.add("已完成");
        tagAdapter = new TagAdapter(list) {
            public View getView(FlowLayout parent, int position, Object o) {
                TextView tag = (TextView) View.inflate(fragment.getContext(), R.layout.common_right_search_item, null);
                String txt = "全部";
                if (position == 1) {
                    txt = "未完成";
                } else if (position == 2) {
                    txt = "已完成";
                }
                tag.setText(txt);
                return tag;
            }

            @Override
            public void onSelected(int position, View view) {
                super.onSelected(position, view);
                String value = "-1";
                if (position == 0) {
                    value = "-1";
                } else if (position == 1) {
                    value = "0";
                } else if (position == 2) {
                    value = "1";
                }
                BaseApplication.app.dm.orderParamsBean.orderStatus = value;
            }

            @Override
            public void unSelected(int position, View view) {
                super.unSelected(position, view);
                BaseApplication.app.dm.orderParamsBean.orderStatus = "-1";
            }
        };
        id_flowlayout.setAdapter(tagAdapter);
        tagAdapter.notifyDataChanged();
        initDelUI();
    }

    private void initDelUI() {
        boolean del0 = TextUtils.isEmpty(input0.getText());
        boolean del1 = TextUtils.isEmpty(input1.getText());
        boolean del2 = TextUtils.isEmpty(input2.getText());
        boolean del3 = TextUtils.isEmpty(input3.getText());
        input_del_0.setVisibility(del0 ? View.INVISIBLE : View.VISIBLE);
        input_del_1.setVisibility(del1 ? View.INVISIBLE : View.VISIBLE);
        input_del_2.setVisibility(del2 ? View.INVISIBLE : View.VISIBLE);
        input_del_3.setVisibility(del3 ? View.INVISIBLE : View.VISIBLE);
    }

    private void showDatePicker(final Button btn) {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        int month = Calendar.getInstance().get(Calendar.MONTH);
        int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);

        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA).parse(btn.getText().toString()));
            year = calendar.get(Calendar.YEAR);
            month = calendar.get(Calendar.MONTH);
            day = calendar.get(Calendar.DAY_OF_MONTH);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        DatePickerDialog dateDialog = new DatePickerDialog(fragment.getActivity(), new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker arg0, int year, int monthOfYear,
                                  int dayOfMonth) {
                // 把获取的日期显示在文本框内，月份从0开始计数，所以要加1
                monthOfYear = monthOfYear + 1;
                String monthOfYear_str = monthOfYear < 10 ? "0" + monthOfYear : monthOfYear + "";
                String day_str = dayOfMonth < 10 ? "0" + dayOfMonth : "" + dayOfMonth;
                String text = year + "-" + monthOfYear_str + "-" + day_str;
                btn.setText(text);
            }
        }, year, month, day); // 后面的三个参数对应于上面的年、月、日
        dateDialog.show();
    }


    private String getDateTime(Button v) {
        String str = v.getText().toString();
        if (str != null && !str.equals("")) {
            str = str + " 00:00:00";
        }
        return str;
    }


}
