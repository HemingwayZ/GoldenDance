package com.goldendance.client.bean;

import java.io.Serializable;

/**
 * Created by hemingway on 2017/2/2.
 */

public class CourseBean implements Serializable {
    //           "introduce": "简介",
//                   "coachid": "COA20170113165208488050421",
//                   "price": 100,
//                   "name": "课程",
//                   "starttime": "2017-01-16 00:00:00",
//                   "endtime": "2017-01-15 00:00:00",
//                   "ordertime": "2017-01-16 00:00:00",
//                   "totalnum": 30,
//                   "ordernum": 0,
//                   "storename": "宝龙店",
//                   "coachname": "教练1",
//                   "picture": "http://120.77.206.145:8080/JinWuTuan/COURSE/COU20170114152329133846569_1.jpg",
//                   "storeid": "STO20170112195733205410731",
//                   "courseid": "COU20170114152329133846569"
    private String introduce;
    private String coachid;
    private int price;
    private String coursename;
    private String starttime;
    private String endtime;
    private String ordertime;
    private int totalnum;
    private int ordernum;
    private String picture;
    private String courseid;
    private Store2Bean store;
    private CoachBean coach;

    private String isordered;//用户是否预约该课程

    public String getIsordered() {
        return isordered;
    }

    public void setIsordered(String isordered) {
        this.isordered = isordered;
    }

    public String getCoursename() {
        return coursename;
    }

    public void setCoursename(String coursename) {
        this.coursename = coursename;
    }

    public Store2Bean getStore() {
        return store;
    }

    public void setStore(Store2Bean store) {
        this.store = store;
    }

    public CoachBean getCoach() {
        return coach;
    }

    public void setCoach(CoachBean coach) {
        this.coach = coach;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getCoachid() {
        return coachid;
    }

    public void setCoachid(String coachid) {
        this.coachid = coachid;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }


    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public String getOrdertime() {
        return ordertime;
    }

    public void setOrdertime(String ordertime) {
        this.ordertime = ordertime;
    }

    public int getTotalnum() {
        return totalnum;
    }

    public void setTotalnum(int totalnum) {
        this.totalnum = totalnum;
    }

    public int getOrdernum() {
        return ordernum;
    }

    public void setOrdernum(int ordernum) {
        this.ordernum = ordernum;
    }


    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getCourseid() {
        return courseid;
    }

    public void setCourseid(String courseid) {
        this.courseid = courseid;
    }
}
