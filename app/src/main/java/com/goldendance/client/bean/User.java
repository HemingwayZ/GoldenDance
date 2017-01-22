package com.goldendance.client.bean;

import com.goldendance.client.http.GDHttpManager;

/**
 * 已登录的用户
 * Created by hemingway on 2017/1/17.
 */

public class User {
    //
    public static String tel = "";
    public static String icon = "";//头像
    public static String defoulttime = "";
    public static int points = -1;
    public static String cardname = "";
    public static String tokenid = "";
    public static String tokenovertime = "";
    public static String signature = "";
    public static String name = "";
    public static String inserttime = "";
    public static String gender = "";
    public static String userid = "";

    public static void setUser(UserBean userBean) {
        if (userBean == null) {
            return;
        }
        tel = userBean.getTel();
        inserttime = userBean.getInserttime();
        gender = userBean.getGender();
        icon = userBean.getIcon();
        defoulttime = userBean.getDefoulttime();
        points = userBean.getPoints();
        cardname = userBean.getCardname();
        tokenid = userBean.getTokenid();
        tokenovertime = userBean.getTokenovertime();
        signature = userBean.getSignature();
        name = userBean.getName();
        userid = userBean.getUserid();
    }

    public static void logOut() {
        tel = null;
        icon = null;
        defoulttime = null;
        points = -1;
        cardname = null;
        tokenid = null;
        signature = null;
        tokenovertime = null;
        name = null;
        inserttime = null;
    }
}
