package com.train.train_customer.act.bean;

public class OrderChangeBean {

    public String changeId;   //String	是		变更ID
    public String detailId; //String	是		订单内容详情ID
    public int orderId;  //int	是		订单号
    public String changeTime;   //String	是		变更时间
    public String changeUser;   //String	是		变更用户ID
    public String changeReason; //String	是		变更原因
    public String oldTotalQty;  //String	是		变更前总数量
    public Double newTotalQty;  //Double	是		变更后总数量
    public String oldLastQty;   //String	是		变更前剩余数量
    public Double newLastQty;   //Double	是		变更后剩余数量
    public Double status;   //Double	是		状态 0待审核1审核通过2审核不通过
    public String authTime; //String	否		审核时间
    public String authUser; //String	否		审核用户ID
    public String authOpinion;  //String	否		审核意见
    public String changeUserName;   //String	是		变更用户名称
    public String authUserName; //String	否		审核用户名称
    public String orderNo;  //String	是		订单号
    public String bstPartNo;    //String	是		BST物资编码
    public String buPartNo; //String	是		路局物资编码
    public int orgId;    //int	是		客户单位ID
    public String orgName;  //String	是		客户单位名称
    public String partName; //String	是		配件名称
    public int buearuId; //int	是		路局ID
    public String buearuName;   //String	是		路局名称
    public String contractNo;   //String	是		项目号
    public String tsType;   //String	是		车型
    public int detailSerial; //int	是		订单行号

}

//{
//        "status":0,
//        "orgId":2,
//        "buearuName":"中" +
//        "国铁路广州局集团有限公司","buearuId":2,
//        "buPartNo":"9" +
//        "76900010179","bstPartNo":"4" +
//        "1810553S","orderId":828,
//        "contractNo":"7" +
//        "90","orgName":"广" +
//        "州动车段","orderNo":"2" +
//        "0180908201831549",
//        "detailId":"9a9cda41-d667-4c02-96b8-17164778e061","partName":"左污水口连接盒",
//        "tsType":"CRH1A-200","changeUserName":"广" +
//        "州动车段","changeId":427,
//        "changeTime":"2" +
//        "018-09-09 09:56:34","changeUser":"1" +
//        "04","changeReason":"邻" +
//        "居","oldTotalQty":7,
//        "newTotalQty":2,
//        "oldLastQty":7,
//        "newLastQty":2,
//        "authTime":null,
//        "authUser":null,
//        "authOpinion":null,
//        "authUserName":null,
//        "detailSerial":1
//}
//{
//        "status":0,
//        "orgId":2,
//        "buearuName":"中国铁路广州局集团有限公司",
//        "buearuId":2,
//        "buPartNo":"976900010179",
//        "bstPartNo":"41810553S",
//        "orderId":828,
//        "contractNo":"790",
//        "orgName":"广州动车段",
//        "orderNo":"20180908201831549",
//        "detailId":"9a9cda41-d667-4c02-96b8-17164778e061",
//        "partName":"左污水口连接盒",
//        "tsType":"CRH1A-200",
//        "changeUserName":"广州动车段",
//        "changeId":425,
//        "changeTime":"2018-09-09 09:33:21",
//        "changeUser":"104",
//        "changeReason":"浏览量",
//        "oldTotalQty":7,
//        "newTotalQty":3,
//        "oldLastQty":7,
//        "newLastQty":3,
//        "authTime":null,
//        "authUser":null,
//        "authOpinion":null,
//        "authUserName":null,
//        "detailSerial":1
//}

//        changeId   String	是		变更ID
//        detailId	String	是		订单内容详情ID
//        orderId	int	是		订单号
//        changeTime	String	是		变更时间
//        changeUser	String	是		变更用户ID
//        changeReason	String	是		变更原因
//        oldTotalQty	String	是		变更前总数量
//        newTotalQty	Double	是		变更后总数量
//        oldLastQty	String	是		变更前剩余数量
//        newLastQty	Double	是		变更后剩余数量
//        status	Double	是		状态 0待审核1审核通过2审核不通过
//        authTime	String	否		审核时间
//        authUser	String	否		审核用户ID
//        authOpinion	String	否		审核意见
//        changeUserName	String	是		变更用户名称
//        authUserName	String	否		审核用户名称
//        orderNo	String	是		订单号
//        bstPartNo	String	是		BST物资编码
//        buPartNo	String	是		路局物资编码
//        orgId	int	是		客户单位ID
//        orgName	String	是		客户单位名称
//        partName	String	是		配件名称
//        buearuId	int	是		路局ID
//        buearuName	String	是		路局名称
//        contractNo	String	是		项目号
//        tsType	String	是		车型
//        detailSerial	int	是		订单行号