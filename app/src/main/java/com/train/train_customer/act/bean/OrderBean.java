package com.train.train_customer.act.bean;

public class OrderBean {

    public String bstPartNo; //	String	是		BST物资编码
    public String buPartNo; //	String	是		路局物资编码
    public int orderId; //	int	是		订单ID
    public String orderNo; //	String	是		订单号
    public String detailId; //	String	是		订单内容详情ID
    public String partName; //	String	是		中文名称
    public String ume; //	String	是		单位
    public String tsType; //	String	是		车型
    public String contractNo; //	String	是		项目号
    public double totalQty; //	Double	是		当前总需求量
    public int detailSerial; //	int	是		订单行号
    public double lastQty; //	Double	是		当前剩余需求量
    public double oTotalQty; //	Double	是		原始总需求量
    public double pickQty; //	Double	否		当前可领数量
    public String orderTime; //	String	否		订单时间


    //    public JSONObject api_json() {
    //        JSONObject json = new JSONObject();
    //        try {
    //            json.put("bstPartNo", bstPartNo);
    //            json.put("buPartNo", buPartNo);
    //            json.put("contractNo", contractNo);
    //            json.put("tsType", tsType);
    //            json.put("qty", qty);
    //        } catch (JSONException e) {
    //            e.printStackTrace();
    //        }
    //        return json;
    //    }

}


//            {
//                    "ume": "个",
//                    "buPartNo": "971200010024",
//                    "bstPartNo": "57810227S",
//                    "orderId": 801,
//                    "totalQty": 2,
//                    "contractNo": "790",
//                    "orderNo": "20180903182810718",
//                    "detailId": "ca12125a-b10a-4eb3-9853-d169faf0c156",
//                    "partName": "平均阀",
//                    "tsType": "CRH1A-200",
//                    "detailSerial": 1,
//                    "lastQty": 2,
//                    "oTotalQty": 2,
//                    "orderTime": "2018-09-03 18:28:10"
//                    },