package com.train.train_manager.act.bean;

import org.json.JSONException;
import org.json.JSONObject;

public class PartBean {

    public String partNo = null;
    public String buPartNo = null;
    public String partE = null;
    public String partC = null;
    public String ume = null;
    public String tsType = null;
    public String contractNo = null;
    public String usage = null;
    public String buearuId = null;
    public String des = null;
    public String price = null;
    public String partType = null;

    public int count = 1;
    public boolean isChecked = false;

    //    添加购物车用
    public JSONObject jsonForCart() {
        JSONObject json = new JSONObject();
        try {
            json.put("bstPartNo", partNo);
            json.put("buPartNo", buPartNo);
            json.put("contractNo", contractNo);
            json.put("tsType", tsType);
            json.put("qty", count);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }

    //   创建订单用
    public JSONObject jsonForCreateOrder() {
        JSONObject json = new JSONObject();
        try {
            json.put("bstPartNo", partNo);
            json.put("buPartNo", buPartNo);
            json.put("contractNo", contractNo);
            json.put("tsType", tsType);
            json.put("totalQty", count);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }

}

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