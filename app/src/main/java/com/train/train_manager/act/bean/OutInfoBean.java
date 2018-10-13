package com.train.train_manager.act.bean;

import java.util.ArrayList;

public class OutInfoBean {

    public int pickId; //	int	是		领料单ID
    public String pickNo; //	String	是		领料单号
    public int orderId; //	int	是		订单ID
    public String orderNo; //	String	是		订单号
    public int status; //	int	是		领料单状态：0待下架1下架中2待交接3已完成
    public String genTime; //	String	是		生成时间
    public String takeTime; //	String	否		下架开始时间
    public String onOutStockTime; //	String	否		到达待出库区时间
    public String deliveryTime; //	String	否		交接时间
    public String wo; //	String	是		项目号W.O
    public String contractNo; //	String	否		项目号Contract
    public String deliveryTo; //	String	否		发送到
    public String reason; //	String	否		发料原因
    public String analyst; //	String	否		分析员
    public String store; //	String	否		库
    public int isDelivered; //	int	否		是否已发货
    public int buearuId; //	int	否		路局ID
    public String buearuName; //	String	否		路局名称
    public int orgId; //	int	否		单位ID
    public String orgName; //	String	否		单位名称
    public ArrayList<OutDetailBean> rqPickDetailList = new ArrayList<>(); //	Array	是		领料单内容列表

    public String deliveryTime_show() {
        if (this.deliveryTime != null) {
            return this.deliveryTime.split(" ")[0];
        } else {
            return "";
        }
    }

}

//{
//        "data": {
//        "reason": "20180727163914853",
//        "pickNo": "20180728234806344",
//        "batchId": 443,
//        "wo": "790",
//        "partType": 1,
//        "ownership": "上海动车段",
//        "deliveryTo": null,
//        "store": "D",
//        "analyst": "超级管理员",
//        “genTime”:”2018-07-25 01:01:01”,
//        “takeTime”:”2018-07-25 01:01:01”,
//        “onOutStockTime”:”2018-07-25 01:01:01”,
//        “deliveryTime”:”2018-07-25 01:01:01”,
//        "printTime": null,
//        " status": 0,
//        "isDelivered": 0,
//        "rqPickDetailList": [
//        {
//        "location": "D0204A",
//        "partName": "牵引电机冷却进风口格栅",
//        "orderDetailId": "7cfdb72b-2312-45be-8983-de7bd99f87c2",
//        "deliveryQty": 1,
//        "pickQty": 1,
//        "mplNo": null,
//        "buPartNo": "972500010004",
//        "bstPartNo": "37810095S",
//        "orderId": 443,
//        "detailId": 463,
//        "pickId": 463,
//        "locQty": 1,
//        "requireQty": 1,
//        "umi": "个",
//        "totalRequireQty": 1
//        }
//        ],
//        "buearuName": "中国铁路上海局集团有限公司",
//        "buearuId": 1,
//        "orderId": 443,
//        "contractNo": "代管件",
//        "orgName": "上海动车段",
//        "orgId": 1,
//        "orderNo": "20180727163914853",
//        "pickId": 463
//        },
//        "code": 200,
//        "msg": "领料单详情获取成功！"
//        }
