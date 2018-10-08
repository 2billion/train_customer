package com.train.train_manager.act.bean;

public class InAAddParamsBean {

    public String transNo = "";
    public int transId;
    public String bstPartNo = "";
    public String tLocation = "";
    public int qty = 1;

    public void clear() {
        this.bstPartNo = "";
        this.tLocation = "";
        this.qty = 1;
    }

}
