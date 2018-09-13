package com.train.train_manager.act.bean;

public class UserBean {

    public String customerId; //    String	是		客户ID
    public String orgId; //    String	是		客户单位
    public String userName; //    String	是		客户用户名
    public String customerName; //    String	是		客户姓名
    public int customerSex; //    int	是		性别
    public String customerTel; //    String	否		电话
    public String customerMail; //    String	否		邮箱
    public String customerAddr; //    String	否		地址
    public String customerDes; //    String	否		描述
    public String customerImg; //    String	否		头像

}

//{
//        "data": {
//        "userName": "gzdcd",
//        "password": "1BBD886460827015E5D605ED44252251",
//        "orgId": 2,
//        "buearuId": null,
//        "customerId": 104,
//        "orgName": null,
//        "customerName": "广州动车段",
//        "customerSex": null,
//        "customerTel": null,
//        "customerMail": null,
//        "customerAddr": null,
//        "customerDes": "null",
//        "newPassword": null,
//        "reNewPassword": null,
//        "customerImg": null,
//        "cardId": null
//        },
//        "code": 200,
//        "msg": "获得个人信息成功！"
//        }


//customerId	String	是		客户ID
//orgId	String	是		客户单位
//userName	String	是		客户用户名
//customerName	String	是		客户姓名
//customerSex	int	是		性别
//customerTel	String	否		电话
//customerMail	String	否		邮箱
//customerAddr	String	否		地址
//customerDes	String	否		描述
//customerImg	String	否		头像

//Json示例：
//        {
//        "code": 200,
//        "msg": "个人信息加载成功！"
//        "data": {
//        "userName": "customer",
//        "orgId": 1,
//        "buearuId": 1,
//        "customerId": 1,
//        "customerName": "上海动车段",
//        "customerSex": 0,
//        "customerTel": "18363671109",
//        "customerMail": "971059634@qq.com",
//        "customerAddr": "上海市",
//        "customerDes": "上海动车段",
//        "customerImg": null
//        },
//
//        }