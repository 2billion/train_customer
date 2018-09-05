package com.train.train_customer.core;

import com.train.train_customer.act.bean.CartBean;
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
    public List<PartBean> productList;

    //    购物车
    public String cart_tsType = ""; // 车型
    public String cart_bstPartNo = ""; // BST物资编码
    public String cart_buPartNo = ""; // 路局物资编码

    public List<CartBean> cartList;

    //    搜索用
    //    ts type list
    public List<TsTypeDataBean> tsTypeList;

    public DataManager() {
        tsTypeList = new ArrayList();
        productList = new ArrayList<>();
        cartList = new ArrayList<>();
    }
}
