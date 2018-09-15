package com.train.train_manager.act.bean;

public class UserBean {

    public String userId; // String	是		用户ID
    public String userName; // String	是		用户姓名
    public String userHeader; // String	否		用户头像
    public String userDesc; // String	否		描述
    public String post; // String	否		职位
    public String createTime; // String	否		创建时间
    public String updateTime; // String	否		更新时间
    public String lastLoginTime; // String	否		最近登录时间
    public String deptId; // Int	是		部门ID
    public String deptName; // String	否		部门名称

}


//{
//        "data": {
//        "userName": "超级管理员1",
//        "deptId": 2,
//        "userHeader": null,
//        "userDesc": null,
//        "post": "系统管理",
//        "createTime": null,
//        "lastLoginTime": null,
//        "updateTime": "2018-06-02 13:44:25",
//        "userId": "admin",
//        "deptName": null
//        },
//        "code": 200,
//        "msg": "参数不正确！"
//        }