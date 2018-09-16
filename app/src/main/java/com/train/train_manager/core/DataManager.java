package com.train.train_manager.core;

import com.train.train_manager.act.bean.CartBean;
import com.train.train_manager.act.bean.InABean;
import com.train.train_manager.act.bean.OrderBean;
import com.train.train_manager.act.bean.OrderParamsBean;
import com.train.train_manager.act.bean.PartBean;
import com.train.train_manager.act.bean.TsTypeDataBean;
import com.train.train_manager.act.bean.UserBean;

import java.util.ArrayList;
import java.util.List;

public class DataManager {

    //    login
    public String token = null;
    public String userName = null;

    //    个人信息
    public UserBean userBean;

    // 一类入库单
    public List<InABean> listInA = new ArrayList<>();
    public String ina_transNo = "";
    public String ina_timeStart = "";
    public String ina_timeEnd = "";
    public int ina_status = 0;  //状态：1已启动2已完成


    //=============================

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

    //     订单
    public List<OrderBean> orderList;
    public OrderParamsBean orderParamsBean;

    //     订单详情 bean
    public OrderBean orderInfoBean;

    //    搜索用
    //    ts type list
    public List<TsTypeDataBean> tsTypeList;


    public DataManager() {
        tsTypeList = new ArrayList();
        productList = new ArrayList<>();
        cartList = new ArrayList<>();
        orderList = new ArrayList<>();
        orderParamsBean = new OrderParamsBean();
    }
}
