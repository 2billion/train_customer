package com.train.train_manager.act.bean;

public class OutParamsBean {

    public String orderNo = ""; //String	否		订单号
    public String pickNo = ""; //String	否		领料单号
    public String genTimeStart = ""; //String	否		生成开始时间
    public String genTimeEnd = ""; //String	否		生成结束时间
    public String status = ""; //int	否		状态
    public String page = ""; //int	是		页码
    public String pageSize = ""; //int	是		每页记录数

}

//    transNo	String	是		入库单号
//    operTime	String	是		创建时间
//    printTime	String	是		打印时间
//    compTime	String	是		完成时间
//    userId	String	是		创建人
//    status	int	是		状态：1已启动2已完成

//{
//        "status": 2,
//        "transNo": "20180813122941766",
//        "transType": 1,
//        "userId": "admin",
//        "compTime": "2018-08-13 12:29:48",
//        "printTime": null,
//        "operTime": "2018-08-13 12:29:44"
//},
