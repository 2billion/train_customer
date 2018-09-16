package com.train.train_manager.act.bean;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class InABean {

    public String transNo;
    public String operTime;
    public String printTime;
    public String compTime;
    public String userId;
    public int status;

    public String compTimeStr() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = sdf.parse(this.operTime);
            return sdf.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }


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
