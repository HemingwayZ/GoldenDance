package com.goldendance.client.bean;

import java.io.Serializable;

/**
 * Created by hemingway on 2017/2/3.
 */

public class Store2Bean implements Serializable {
    //           "storeid": "STO20170112195733205410731",
//                   "address": "福州宝龙路宝龙街100号",
//                   "nickname": "宝龙店",
//                   "picture": "http://120.77.206.145:8080/JinWuTuanhttp://120.77.206.145:8080/JinWuTuanhttp://120.77.206.145:8080/JinWuTuan/store/STO20170112195733205410731_1.jpg",
//                   "state": "0"
    private String storeid;
    private String address;
    private String nickname;
    private String picture;
    private String state;

    public String getStoreid() {
        return storeid;
    }

    public void setStoreid(String storeid) {
        this.storeid = storeid;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
