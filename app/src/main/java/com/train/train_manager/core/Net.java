package com.train.train_manager.core;

import android.content.Context;
import android.util.Log;

import com.train.train_manager.act.bean.CartBean;
import com.train.train_manager.act.bean.InAAddParamsBean;
import com.train.train_manager.act.bean.KuCunParams;
import com.train.train_manager.act.bean.OrderParamsBean;
import com.train.train_manager.act.bean.OutParamsBean;
import com.train.train_manager.base.BaseApplication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class Net {
    public static final String HOST = "http://115.28.136.235:8080/bstapi/";

    private OkHttpClient client;
    private Context ctx;

    public Net(Context ctx) {
        client = new OkHttpClient.Builder().readTimeout(30, TimeUnit.SECONDS).build();
        this.ctx = ctx;
    }

    public BaseApplication app() {
        return (BaseApplication) this.ctx.getApplicationContext();
    }

    //    2.1 登录（用户名/密码）
    //    请求地址：/login/checkinPwd
    public void checkinPwd(Callback callBack, String name, String pwd) {
        String url = HOST + "/login/checkinPwd";
        FormBody.Builder params = new FormBody.Builder();
        params.add("userName", name);
        params.add("password", pwd);
        Request request = new Request.Builder().post(params.build()).url(url).build();
        Call call = client.newCall(request);
        call.enqueue(callBack);
    }

    //    2.3登出
    //    请求地址：/login/checkout

    //    2.4获取用户信息
    //    请求地址： /user/info
    public void info(Callback callBack) {
        String url = HOST + "/user/info";
        FormBody.Builder params = new FormBody.Builder();
        Request request = new Request.Builder().url(url).post(params.build()).addHeader("token", BaseApplication.app.dm.getToken()).build();
        Call call = client.newCall(request);
        call.enqueue(callBack);
    }

    //    2.7 修改用户信息
    //    请求地址：/user/editpersonal
    //    userName	String	是		用户姓名
    //    userDesc	String	否		描述
    //    post	String	否		职位
    public void editpersonal(Callback callBack, String userName, String userDesc, String post) {
        String url = HOST + "/user/editpersonal";
        FormBody.Builder params = new FormBody.Builder();
        params.add("userName", userName);
        params.add("userDesc", userDesc);
        params.add("post", post);
        Request request = new Request.Builder().url(url).post(params.build())
                .addHeader("token", BaseApplication.app.dm.getToken()).build();
        Call call = client.newCall(request);
        call.enqueue(callBack);
    }

    //  上传头像
    public void uploadAvatar(Callback callBack, String path) {
        String url = HOST + "/user/uploadAvatar";
        File file = new File(path);
        MultipartBody.Builder requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM);
        if (file != null) {
            // MediaType.parse() 里面是上传的文件类型。
            RequestBody body = RequestBody.create(MediaType.parse("image/*"), file);
            String filename = file.getName();
            // 参数分别为， 请求key ，文件名称 ， RequestBody
            requestBody.addFormDataPart("avatarFile", file.getName(), body);
        } else {
            Log.e("app", "===================File is null");
        }
        //        RequestBody body = RequestBody.create(MediaType.parse(path), file);
        Request request = new Request.Builder().url(url).post(requestBody.build()).addHeader("token", BaseApplication.app.dm.getToken()).build();
        Call call = client.newCall(request);
        call.enqueue(callBack);
    }

    //    2.5修改密码
    //    请求地址： /user/password
    public void password(Callback callBack, String password, String newPassword) {
        String url = HOST + "/user/password";
        FormBody.Builder params = new FormBody.Builder();
        params.add("oldPassWord", password);
        params.add("Password", newPassword);
        Request request = new Request.Builder().url(url).post(params.build()).addHeader("token", BaseApplication.app.dm.getToken()).build();
        Call call = client.newCall(request);
        call.enqueue(callBack);
    }

    //    2.9保存一类入库单
    //    请求地址：/trans/saveInA
    //    transNo	String	否		入库单号，如果为空，则为新单据
    //    detail	String	是		入库单物料明细

    //    transId	int	否		物料明细ID,如果为空为新增，否则为修改
    //    bstPartNo	String	是		BST物资编码
    //    tLocation	String	是		入库库位
    //    qty	Double	是		数量

    public void saveInA(Callback callBack) {
        InAAddParamsBean bean = BaseApplication.app.dm.inaAddBean;
        String url = HOST + "/trans/saveInA";
        FormBody.Builder params = new FormBody.Builder();
        params.add("transNo", bean.transNo);
        JSONObject jsonArray = new JSONObject();
        try {
            jsonArray.put("transId", bean.transId);
            jsonArray.put("bstPartNo", bean.bstPartNo);
            jsonArray.put("tLocation", bean.tLocation);
            jsonArray.put("qty", bean.qty);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        params.add("detail", jsonArray.toString());
        Request request = new Request.Builder().url(url).post(params.build())
                .addHeader("token", BaseApplication.app.dm.getToken()).build();
        Call call = client.newCall(request);
        call.enqueue(callBack);
    }

    //     2.10 一类入库单列表
    //     请求地址： /trans/listInA
    //    transNo	String	否		入库单号
    //    timeStart	String	否		创建开始时间
    //    timeEnd	String	否		创建结束时间
    //    status	int	否		状态：1已启动2已完成
    //    page	int	是		页码
    //    pageSize	int	是		每页记录数
    public void listInA(Callback callBack, String page, String pageSize, String transNo, String timeStart, String timeEnd, String status) {
        String url = HOST + "/trans/listInA";
        FormBody.Builder params = new FormBody.Builder();
        params.add("transNo", transNo);
        params.add("timeStart", timeStart);
        params.add("timeEnd", timeEnd);
        params.add("status", status);
        params.add("page", page);
        params.add("pageSize", pageSize);
        Request request = new Request.Builder().url(url).post(params.build())
                .addHeader("token", BaseApplication.app.dm.getToken()).build();
        Call call = client.newCall(request);
        call.enqueue(callBack);
    }

    //     2.11 一类入库单详情
    //    请求地址： /trans/getDetailByTransNo
    //    transNo	String	否		入库单号
    public void getDetailByTransNo(Callback callBack, String transNo) {
        String url = HOST + "/trans/getDetailByTransNo";
        FormBody.Builder params = new FormBody.Builder();
        params.add("transNo", transNo);
        Request request = new Request.Builder().url(url).post(params.build())
                .addHeader("token", BaseApplication.app.dm.getToken()).build();
        Call call = client.newCall(request);
        call.enqueue(callBack);
    }

    //    2.12获取手工领料单基本信息列表
    //    请求地址：/pick/listforstock
    //    orderNo	String	否		订单号
    //    pickNo	String	否		领料单号
    //    genTimeStart	String	否		生成开始时间
    //    genTimeEnd	String	否		生成结束时间
    //    status	int	否		状态
    //    page	int	是		页码
    //    pageSize	int	是		每页记录数
    public void listforstock(Callback callBack, OutParamsBean bean, String page, String pageSize) {
        String url = HOST + "/pick/listforstock";
        FormBody.Builder params = new FormBody.Builder();
        params.add("orderNo", bean.orderNo);
        params.add("pickNo", bean.pickNo);
        params.add("genTimeStart", bean.genTimeStart);
        params.add("genTimeEnd", bean.genTimeEnd);
        params.add("status", bean.status);
        params.add("page", page);
        params.add("pageSize", pageSize);
        Request request = new Request.Builder().url(url).post(params.build())
                .addHeader("token", BaseApplication.app.dm.getToken()).build();
        Call call = client.newCall(request);
        call.enqueue(callBack);
    }

    //    2.13 手工领料单 详情
    //    请求地址： /pick/detail
    //    transNo	String	否		入库单号
    public void pick_detail(Callback callBack, String pickId) {
        String url = HOST + "/pick/detail";
        FormBody.Builder params = new FormBody.Builder();
        params.add("pickId", pickId);
        Request request = new Request.Builder().url(url).post(params.build())
                .addHeader("token", BaseApplication.app.dm.getToken()).build();
        Call call = client.newCall(request);
        call.enqueue(callBack);
    }


    //    2.19库存查询
    //    请求地址：/stock/list

    //    location	String	否		库位
    //    contractNo 	String	否		项目号
    //    bstPartNo	String	否		BST物资编码
    //    page	int	是		页码
    //    pageSize	int	是		每页记录数
    public void stock_list(Callback callBack, String page, String pageSize) {
        KuCunParams bean = BaseApplication.app.dm.kuCunParams;
        String url = HOST + "/stock/list";
        FormBody.Builder params = new FormBody.Builder();
        params.add("location", bean.location);
        params.add("contractNo", bean.contractNo);
        params.add("bstPartNo", bean.bstPartNo);
        params.add("page", page);
        params.add("pageSize", pageSize);
        Request request = new Request.Builder().url(url).post(params.build())
                .addHeader("token", BaseApplication.app.dm.getToken()).build();
        Call call = client.newCall(request);
        call.enqueue(callBack);
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    // 以下为客户端

    //    1.1登录（用户名/密码）
    //    请求地址：/login/checkCustomerPwd
    public void login(Callback callBack, String name, String pwd) {
        String url = HOST + "/login/checkCustomerPwd";
        FormBody.Builder params = new FormBody.Builder();
        params.add("userName", name);
        params.add("password", pwd);
        Request request = new Request.Builder().post(params.build()).url(url).build();
        Call call = client.newCall(request);
        call.enqueue(callBack);
    }

    //    1.3登出
    //    请求地址：/login/checkout
    public void checkOut(Callback callBack) {
        String url = HOST + "/login/checkout";
        FormBody.Builder params = new FormBody.Builder();
        Request request = new Request.Builder().url(url).post(params.build()).build();
        Call call = client.newCall(request);
        call.enqueue(callBack);
    }

    //    1.4获取用户信息
    //    请求地址： /customer/findMyInfo
    public void findMyInfo(Callback callBack) {
        String url = HOST + "/customer/findMyInfo";
        FormBody.Builder params = new FormBody.Builder();
        Request request = new Request.Builder().url(url).post(params.build()).addHeader("token", BaseApplication.app.dm.getToken()).build();
        Call call = client.newCall(request);
        call.enqueue(callBack);
    }

    //    1.5修改密码
    //    请求地址： /customer/updateCustomerPassword
    public void updateCustomerPassword(Callback callBack, String password, String newPassword) {
        String url = HOST + "/customer/updateCustomerPassword";
        FormBody.Builder params = new FormBody.Builder();
        params.add("password", password);
        params.add("newPassword", newPassword);
        Request request = new Request.Builder().url(url).post(params.build()).addHeader("token", BaseApplication.app.dm.getToken()).build();
        Call call = client.newCall(request);
        call.enqueue(callBack);
    }

    //    1.6获取适用车型
    //    请求地址： /tsType/findTsTypeList
    public void findTsTypeList(Callback callBack) {
        String url = HOST + "/tsType/findTsTypeList";
        FormBody.Builder params = new FormBody.Builder();
        Request request = new Request.Builder().url(url).post(params.build())
                .addHeader("token", BaseApplication.app.dm.getToken()).build();
        Call call = client.newCall(request);
        call.enqueue(callBack);
    }

    //     1.7获取配件列表
    //     请求地址： /part/findPartNumList
    //     partName":"","tsType":"","partNo":"","buPartNo":"","page":1,"pageSize":20
    public void getPartList(Callback callBack, int page, int pageSize, String partName, String tsType, String partNo, String buPartNo) {
        String url = HOST + "/part/findPartNumList";
        FormBody.Builder params = new FormBody.Builder();
        params.add("page", page + "");
        params.add("pageSize", pageSize + "");
        params.add("partName", partName);
        params.add("tsType", tsType);
        params.add("partNo", partNo);
        params.add("buPartNo", buPartNo);
        Request request = new Request.Builder().url(url).post(params.build())
                .addHeader("token", BaseApplication.app.dm.getToken()).build();
        Call call = client.newCall(request);
        call.enqueue(callBack);
    }


    //     1.8获取购物车列表
    //     请求地址： /cart/findCartList
    //     "tsType":"","bstPartNo":"","buPartNo":""}
    public void getCartList(Callback callBack, String tsType, String bstPartNo, String buPartNo) {
        String url = HOST + "/cart/findCartList";
        FormBody.Builder params = new FormBody.Builder();
        params.add("tsType", tsType);
        params.add("bstPartNo", bstPartNo);
        params.add("buPartNo", buPartNo);
        Request request = new Request.Builder().url(url).post(params.build())
                .addHeader("token", BaseApplication.app.dm.getToken()).build();
        Call call = client.newCall(request);
        call.enqueue(callBack);
    }


    //     1.9新增购物车
    //     请求地址： /cart/saveCartListInfo
    //     cartJson:    [
    //        {
    //            "bstPartNo": "63810647S",
    //            "buPartNo": "971900020001",
    //            "contractNo": "790",
    //            "tsType": "CRH1A-200",
    //            "qty": "1"
    //        }
    //    ]
    //    JSONObject api_json = new JSONObject();
    //        try {
    //        api_json.put("bstPartNo","63810647S");
    //        api_json.put("buPartNo","971900020001");
    //        api_json.put("contractNo","790");
    //        api_json.put("tsType","CRH1A-200");
    //        api_json.put("qty","1");
    //    } catch (JSONException e) {
    //        e.printStackTrace();
    //    }
    //    JSONArray jsonArray = new JSONArray();
    //        jsonArray.put(api_json);
    public void saveCartListInfo(Callback callBack, JSONArray jsonArray) {
        String url = HOST + "/cart/saveCartListInfo";
        FormBody.Builder params = new FormBody.Builder();
        //        MediaType JSON = MediaType.parse("application/api_json; charset=utf-8");
        //        okhttp3.RequestBody body = RequestBody.create(JSON, jsonArray.toString());
        params.add("cartJson", jsonArray.toString());
        Request request = new Request.Builder().url(url).post(params.build())
                .addHeader("token", BaseApplication.app.dm.getToken()).build();
        Call call = client.newCall(request);
        call.enqueue(callBack);
    }

    //    1.10更新购物车
    //    请求地址：/cart/updateCartInfo
    //    {"bstPartNo":"37810095S","buPartNo":"972500010004","contractNo":"790","tsType":"CRH1A-200","qty":"21"}
    public void updateCartInfo(Callback callBack, CartBean bean) {
        String url = HOST + "/cart/updateCartInfo";
        FormBody.Builder params = new FormBody.Builder();
        JSONArray jsonArray = new JSONArray();
        jsonArray.put(bean.api_json());
        params.add("cartJson", jsonArray.toString());
        Request request = new Request.Builder().url(url).post(params.build())
                .addHeader("token", BaseApplication.app.dm.getToken()).build();
        Call call = client.newCall(request);
        call.enqueue(callBack);
    }

    //    1.11删除购物车
    //    请求地址：/cart/deleteCartInfo
    //    {"bstPartNo":"37810095S","buPartNo":"972500010004","contractNo":"790","tsType":"CRH1A-200","qty":"21"}
    public void deleteCartInfo(Callback callBack, JSONArray jsonArray) {
        String url = HOST + "/cart/deleteCartInfo";
        FormBody.Builder params = new FormBody.Builder();
        params.add("cartJson", jsonArray.toString());
        Request request = new Request.Builder().url(url).post(params.build())
                .addHeader("token", BaseApplication.app.dm.getToken()).build();
        Call call = client.newCall(request);
        call.enqueue(callBack);
    }

    //    1.12从购物车创建订单
    //    请求地址：/cart/clearCartList
    //    bstPartNo	String	是		BST物资编码
    //    buPartNo	String	是		路局物资编码
    //    tsType	String	是		车型
    //    contractNo	String	是		项目号
    //    totalQty	Double	是		数量
    public void clearCartList(Callback callBack, JSONArray jsonArray) {
        String url = HOST + "/cart/clearCartList";
        FormBody.Builder params = new FormBody.Builder();
        params.add("cartJson", jsonArray.toString());
        Request request = new Request.Builder().url(url).post(params.build())
                .addHeader("token", BaseApplication.app.dm.getToken()).build();
        Call call = client.newCall(request);
        call.enqueue(callBack);
    }

    //    1.13 创建订单
    //    请求地址：/order/saveOrderInfo
    //    bstPartNo	String	是		BST物资编码
    //    buPartNo	String	是		路局物资编码
    //    tsType	String	是		车型
    //    contractNo	String	是		项目号
    //    totalQty	Double	是		数量
    public void saveOrderInfo(Callback callBack, JSONArray jsonArray) {
        String url = HOST + "/order/saveOrderInfo";
        FormBody.Builder params = new FormBody.Builder();
        params.add("orderDetailJson", jsonArray.toString());
        Request request = new Request.Builder().url(url).post(params.build())
                .addHeader("token", BaseApplication.app.dm.getToken()).build();
        Call call = client.newCall(request);
        call.enqueue(callBack);
    }

    //    1.14 获取订单列表
    //    请求地址： /orderDetail/findOrderDetailList
    //    orderNo	String	否		订单号
    //    bstPartNo	String	否		BST物资编码
    //    buPartNo	String	否		路局物资编码
    //    orderTimeStart	String	否		订单开始时间
    //    orderTimeEnd	String	否		订单结束时间
    //    orderCompTimeStart	String	否		订单完成开始时间
    //    orderCompTimeEnd	String	否		订单完成开始时间
    //    orderStatus	String	否		订单状态：-1全部0未完成1完成
    //    partName	String	否		配件名称
    //    page	int	是		页码
    //    pageSize	int	是		每页记录数
    public void findOrderDetailList(Callback callBack, int page, int pageSize, OrderParamsBean bean) {
        String url = HOST + "/orderDetail/findOrderDetailList";
        FormBody.Builder params = new FormBody.Builder();
        params.add("page", page + "");
        params.add("pageSize", pageSize + "");

        params.add("orderNo", bean.orderNo);   //String	否		订单号
        params.add("bstPartNo", bean.bstPartNo);   //String	否		BST物资编码
        params.add("buPartNo", bean.buPartNo);   //String	否		路局物资编码
        params.add("orderTimeStart", bean.orderTimeStart);   //String	否		订单开始时间
        params.add("orderTimeEnd", bean.orderTimeEnd);   //String	否		订单结束时间
        params.add("orderCompTimeStart", bean.orderCompTimeStart);   //String	否		订单完成开始时间
        params.add("orderCompTimeEnd", bean.orderCompTimeEnd);   //String	否		订单完成开始时间
        params.add("orderStatus", bean.orderStatus);   //String	否		订单状态：-1全部0未完成1完成
        params.add("partName", bean.partName);   //String	否		配件名称

        //        params.ico_add("orderCompTimeStart","2018-08-14 00:00:00");   //String	否		配件名称
        //        2018-08-14%2000%3A00%3A00
        Request request = new Request.Builder().url(url).post(params.build())
                .addHeader("token", BaseApplication.app.dm.getToken()).build();
        Call call = client.newCall(request);
        call.enqueue(callBack);
    }


    //    1.15 订单变更
    //    请求地址：/orderChange/saveOrderChangeInfo
    //    detailId	String	否		订单详情ID
    //    changeReason	String	否		变更原因
    //    changeNum	Double	否		变更后数量
    public void saveOrderChangeInfo(Callback callBack, String detailId, String changeReason, String changeNum) {
        String url = HOST + "/orderChange/saveOrderChangeInfo";
        FormBody.Builder params = new FormBody.Builder();
        params.add("detailId", detailId);
        params.add("changeReason", changeReason);
        params.add("changeNum", changeNum);
        Request request = new Request.Builder().url(url).post(params.build())
                .addHeader("token", BaseApplication.app.dm.getToken()).build();
        Call call = client.newCall(request);
        call.enqueue(callBack);
    }

    //    1.16 订单变更列表
    //    请求地址：/orderChange/findOrderChangeByDetailId
    //    detailId	String	否		订单详情ID
    public void findOrderChangeByDetailId(Callback callBack, String detailId) {
        String url = HOST + "/orderChange/findOrderChangeByDetailId";
        FormBody.Builder params = new FormBody.Builder();
        params.add("detailId", detailId);
        Request request = new Request.Builder().url(url).post(params.build())
                .addHeader("token", BaseApplication.app.dm.getToken()).build();
        Call call = client.newCall(request);
        call.enqueue(callBack);
    }


    //    1.18 修改用户信息
    //    请求地址：/customer/updateCustomerInfo
    //    customerName	"String"	否		姓名
    //    customerSex	int	否		性别
    //    customerTel	String	否		电话
    //    customerMail	String	否		邮箱
    //    customerAddr	String	否		地址
    public void updateCustomerInfo(Callback callBack, String customerName, String customerSex, String customerTel, String customerMail, String customerAddr) {
        String url = HOST + "/customer/updateCustomerInfo";
        FormBody.Builder params = new FormBody.Builder();
        params.add("customerName", customerName);
        params.add("customerSex", customerSex);
        params.add("customerTel", customerTel);
        params.add("customerMail", customerMail);
        params.add("customerAddr", customerAddr);
        Request request = new Request.Builder().url(url).post(params.build())
                .addHeader("token", BaseApplication.app.dm.getToken()).build();
        Call call = client.newCall(request);
        call.enqueue(callBack);
    }

    //  获取头像
    public void getAvatar(Callback callBack, String path) {
        Request request = new Request.Builder().url(HOST + path).build();
        Call call = client.newCall(request);
        call.enqueue(callBack);
    }

    //  上传头像
    public void uploadFile(Callback callBack, String path) {
        String url = HOST + "/customer/updateCustomerIcon";
        File file = new File(path);
        MultipartBody.Builder requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM);
        if (file != null) {
            // MediaType.parse() 里面是上传的文件类型。
            RequestBody body = RequestBody.create(MediaType.parse("image/*"), file);
            String filename = file.getName();
            // 参数分别为， 请求key ，文件名称 ， RequestBody
            requestBody.addFormDataPart("file", file.getName(), body);

        } else {
            Log.e("app", "===================File is null");
        }
        //        RequestBody body = RequestBody.create(MediaType.parse(path), file);
        Request request = new Request.Builder().url(url).post(requestBody.build()).addHeader("token", BaseApplication.app.dm.getToken()).build();
        Call call = client.newCall(request);
        call.enqueue(callBack);
    }

}
