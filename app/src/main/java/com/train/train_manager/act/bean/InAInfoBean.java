package com.train.train_manager.act.bean;

public class InAInfoBean {

    public int transId; //int	是		入库单内容详情ID
    public String transNo; //String	是		一类入库单号
    public String contractNo; //String	否		项目号
    public String bstPartNo; //String	是		BST物资编码
    public String fLocation; //String	是		中间库位
    public String tLocation; //String	是		入库库位
    public double qty; //Double	是		数量
    public int lineNo; //int	是		入库单行号
    public String operTime; //String	是		入库时间
    public String operReason; //String	是		入库事由
    public String ume; //String	是		单位
    public String des; //String	是		配件名称

}

//transId	int	是		入库单内容详情ID
//        transNo	String	是		一类入库单号
//        contractNo	String	否		项目号
//        bstPartNo	String	是		BST物资编码
//        fLocation	String	是		中间库位
//        tLocation	String	是		入库库位
//        qty	Double	是		数量
//        lineNo	int	是		入库单行号
//        operTime	String	是		入库时间
//        operReason	String	是		入库事由
//        ume	String	是		单位
//        des	String	是		配件名称


//{
//        "data":  [
//        {
//        "bstPartNo": "38001202S",
//        "contractNo": "797",
//        "fLocation": "D0103A",
//        "tLocation": "D0104A",
//        " transId": 1,
//        "transNo": "11111111111111111",
//        "detailNo": 1,
//        "qty": 0,
//        "partName": "速度传感器"
//        }
//        ],
//        "code": 200,
//        "msg": "获得我的订单信息成功！"
//        }