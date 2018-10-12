package com.train.train_manager.act.chuku;

import android.app.DatePickerDialog;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.train.train_manager.R;
import com.train.train_manager.base.BaseApplication;
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
public class ChukuRightView {
    View view;
    TagFlowLayout id_flowlayout;
    ChuKuActivity activity;
    TagAdapter tagAdapter;
    View right_search_view;

    EditText input1;
    View input1_del;


    EditText input2;
    View input2_del;

    Button data_picker_1;
    Button data_picker_2;

    public ChukuRightView(ChuKuActivity activity, View view) {
        this.activity = activity;
        this.view = view;

        initRightView();//    右侧数据
    }

    private void initRightView() {
        right_search_view = this.view.findViewById(R.id.right_search_view);
        id_flowlayout = this.view.findViewById(R.id.id_flowlayout);
        //        搜索
        view.findViewById(R.id.ok).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                BaseApplication.app.dm.outParams.orderNo = input1.getText().toString();
                BaseApplication.app.dm.outParams.pickNo = input2.getText().toString();

                BaseApplication.app.dm.outParams.genTimeStart= getDateTime(data_picker_1);
                BaseApplication.app.dm.outParams.genTimeEnd= getDateTime(data_picker_2);

                switchVisible();
                activity.refresh();
                activity.hideKeyBoard(v);
                activity.searchInput.setText(input2.getText().toString() + "");
            }
        });
        //        重置
        view.findViewById(R.id.reset).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                input1.setText("");
                input2.setText("");
                initDelUI();
                tagAdapter.setSelectedList();
                data_picker_1.setText("");
                data_picker_2.setText("");
                BaseApplication.app.dm.outParams.status = "";
            }
        });

        input1 = this.view.findViewById(R.id.et_order_no);
        input1.setText(BaseApplication.app.dm.outParams.orderNo);
        input1.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                input1_del.setVisibility(TextUtils.isEmpty(input1.getText()) ? View.INVISIBLE : View.VISIBLE);
            }

            public void afterTextChanged(Editable s) {

            }
        });

        input1_del = this.view.findViewById(R.id.input_del_order_no);
        input1_del.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                input1.setText("");
            }
        });

        input2 = this.view.findViewById(R.id.et_pick_no);
        input2.setText(BaseApplication.app.dm.outParams.pickNo);
        input2.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                input2_del.setVisibility(TextUtils.isEmpty(input2.getText()) ? View.INVISIBLE : View.VISIBLE);
            }

            public void afterTextChanged(Editable s) {

            }
        });

        input2_del = this.view.findViewById(R.id.input_del_pick_no);
        input2_del.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                input2.setText("");
            }
        });

        view.findViewById(R.id.hide).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                switchVisible();
                activity.searchInput.setText(input2.getText().toString() + "");
            }
        });

        View.OnClickListener listener = new View.OnClickListener() {
            public void onClick(View v) {
                showDatePicker((Button) v);
            }
        };
        data_picker_1 = this.view.findViewById(R.id.data_picker_1);
        data_picker_2 = this.view.findViewById(R.id.data_picker_2);
        data_picker_1.setOnClickListener(listener);
        data_picker_2.setOnClickListener(listener);

        initTagAdapter();
    }

    public void switchVisible() {
        int show = this.right_search_view.getVisibility();
        if (show == View.INVISIBLE) {
            this.right_search_view.setVisibility(View.VISIBLE);
        } else {
            this.right_search_view.setVisibility(View.INVISIBLE);
        }
        input1.setText(BaseApplication.app.dm.outParams.orderNo);
        input2.setText(BaseApplication.app.dm.outParams.pickNo);
    }

    ArrayList<String> list = new ArrayList<>();

    private void initTagAdapter() {
//    TODO 状态
        list.add("全部");
        list.add("待下架");
        list.add("下架中");
        list.add("待交接");
        list.add("已完成");
        tagAdapter = new TagAdapter(list) {
            public View getView(FlowLayout parent, int position, Object o) {
                TextView tag = (TextView) View.inflate(activity, R.layout.common_right_search_item, null);
                tag.setText(list.get(position));
                return tag;
            }

            @Override
            public void onSelected(int position, View view) {
                super.onSelected(position, view);
                BaseApplication.app.dm.outParams.status = (position - 1) + "";
            }

            @Override
            public void unSelected(int position, View view) {
                super.unSelected(position, view);
                BaseApplication.app.dm.ina_status = -1;
                BaseApplication.app.dm.outParams.status = "";
            }
        };
        id_flowlayout.setAdapter(tagAdapter);
        tagAdapter.notifyDataChanged();
        initDelUI();
    }

    private void initDelUI() {
        input1_del.setVisibility(TextUtils.isEmpty(input1.getText()) ? View.INVISIBLE : View.VISIBLE);
        input2_del.setVisibility(TextUtils.isEmpty(input2.getText()) ? View.INVISIBLE : View.VISIBLE);
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

        DatePickerDialog dateDialog = new DatePickerDialog(activity, new DatePickerDialog.OnDateSetListener() {
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
        //        if (str != null && !str.equals("")) {
        //            str = str + " 00:00:00";
        //            str
        //        }
        return str;
    }


}
