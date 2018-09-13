package com.train.train_manager.act.bean;

import org.json.JSONException;
import org.json.JSONObject;

public class CartBean {

    public int customerId;
    public String bstPartNo;
    public String buPartNo;
    public String partName;
    public String ume;
    public String tsType;
    public String contractNo;
    public double qty;
    public String buearuId;
    public String editTime;
    public String price;
    public String totalFee;
    public String requireDate;


    public boolean showAmountView = false;
    public boolean ischecked = false;

    public JSONObject api_json() {
        JSONObject json = new JSONObject();
        try {
            json.put("bstPartNo", bstPartNo);
            json.put("buPartNo", buPartNo);
            json.put("contractNo", contractNo);
            json.put("tsType", tsType);
            json.put("qty", (int)qty);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }

    public JSONObject clearCartList_params_json() {
        JSONObject json = new JSONObject();
        try {
            json.put("bstPartNo", bstPartNo);
            json.put("buPartNo", buPartNo);
            json.put("contractNo", contractNo);
            json.put("tsType", tsType);
            json.put("totalQty", qty);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }

}

//customerId	int	是		客户ID
//bstPartNo	String	是		BST物资编码
//buPartNo	String	是		路局物资编码
//partName	String	是		中文名称
//ume	String	是		单位
//tsType	String	是		车型
//contractNo	String	是		项目号
//Qty	Double	是		数量
//buearuId	String	否		路局ID
//editTime	String	否		修改时间
//price	String	否		单价
//totalFee	String	否		总费用
//requireDate	String	否		需求时间

//{
//        "usage": null,
//        "partE": "Stainless steel tube",
//        "partC": "不锈钢管",
//        "ume": "米",
//        "des": null,
//        "buearuId": null,
//        "buPartNo": "978900040211",
//        "contractNo": "790",
//        "partNo": "33810027S",
//        "partType": null,
//        "tsType": "CRH1A-200",
//        "price": null
//        }, {
//        "usage": null,
//        "partE": "hook",
//        "partC": "挂钩",
//        "ume": "个",
//        "des": null,
//        "buearuId": null,
//        "buPartNo": "978100030085",
//        "contractNo": "790",
//        "partNo": "36810128S",
//        "partType": null,
//        "tsType": "CRH1A-200",
//        "price": null
//        }

//    参数	类型	是否必填	最大长度	描述	示例值
//    partNo	String	是		BST物资编码
//    buPartNo	String	是		路局物资编码
//    partE	String	是		英文名称
//    partC	String	是		中文名称
//    ume	String	是		单位
//    tsType	String	是		车型
//    contractNo	String	是		项目号
//    usage	String	否		用途
//    buearuId	String	否		路局ID
//    des	String	否		描述
//    price	String	否		价格
//    partType	String	否		配件类型