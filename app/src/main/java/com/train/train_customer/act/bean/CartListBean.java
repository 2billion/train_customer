package com.train.train_customer.act.bean;

//param ->  {"partName":"","tsType":"","partNo":"","buPartNo":"","page":1,"pageSize":20}

import java.util.ArrayList;
import java.util.List;

public class CartListBean extends BaseBean {
//    public CartListDataBean data = null;
    public List<CartBean> data = new ArrayList();
}


