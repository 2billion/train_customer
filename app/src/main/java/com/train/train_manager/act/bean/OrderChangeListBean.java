package com.train.train_manager.act.bean;

//param ->  {"partName":"","tsType":"","partNo":"","buPartNo":"","page":1,"pageSize":20}

import java.util.ArrayList;
import java.util.List;

public class OrderChangeListBean extends BaseBean {
    public List<OrderChangeBean> data = new ArrayList();
}


