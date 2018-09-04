package com.train.train_customer.core;

import com.train.train_customer.act.bean.PartBean;
import com.train.train_customer.act.bean.TsTypeDataBean;

import java.util.ArrayList;
import java.util.List;

public class DataManager {

    //    login
    public String token = null;
    public String userName = null;

    //    配件列表
    //    part list
    public String product_tsType = ""; // 车型
    public String product_partName = ""; // 配件
    public String product_partNo = ""; // bst
    public String product_buPartNo = ""; // 路局

    public List<PartBean> partList;

    //    搜索用
    //    ts type list
    public List<TsTypeDataBean> tsTypeList;

    public DataManager() {
        tsTypeList = new ArrayList();
        partList = new ArrayList<>();
    }
}
