package com.train.train_manager.act.bean;

public class OutDetailBean {

    public int detailId; //内容详情ID
    public int pickId; //领料单ID
    public String location; //int	是		库位
    public String partName; //String	是		配件名称
    public String orderDetailId; //int	是		订单详情ID
    public double pickQty; //Double	是		已下架数量
    public double deliveryQty; //Double	是		已交货数量
    public int mplNo; //int	是		领料单号
    public String buPartNo; //String	是		路局物资编码
    public String bstPartNo; //String	否		BST物资编码
    public String orderId; //String	否		订单ID
    public String locQty; //String	否		库存数量
    public String requireQty; //String	否		剩余需求数量
    public String umi; //String	是		单位
    public String totalRequireQty; //String	否		总需求数量

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
