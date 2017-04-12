package com.goldendance.client.bean;

import java.io.Serializable;

/**
 * Created by hemingway on 2017/1/12.
 */

public class UserBean implements Serializable {
    private String userid;
    private String tel;
    private String gender;
    private String icon;//头像
    private String defoulttime;
    private int points;
    private String cardname;//会员名称
    private String tokenid;
    private String tokenovertime;
    private String signature;
    private String name;
    private String inserttime;
    private String coursenum = "";//当前已预约，未结束课程
    private String cardovertime;//卡片超时时间

    public String getCoursenum() {
        return coursenum;
    }

    public void setCoursenum(String coursenum) {
        this.coursenum = coursenum;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getInserttime() {
        return inserttime;
    }

    public void setInserttime(String inserttime) {
        this.inserttime = inserttime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTokenid() {
        return tokenid;
    }

    public void setTokenid(String tokenid) {
        this.tokenid = tokenid;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getDefoulttime() {
        return defoulttime;
    }

    public void setDefoulttime(String defoulttime) {
        this.defoulttime = defoulttime;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String getCardname() {
        return cardname;
    }

    public void setCardname(String cardname) {
        this.cardname = cardname;
    }


    public String getTokenovertime() {
        return tokenovertime;
    }

    public void setTokenovertime(String tokenovertime) {
        this.tokenovertime = tokenovertime;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }
}
