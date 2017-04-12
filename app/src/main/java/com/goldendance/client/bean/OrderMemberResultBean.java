package com.goldendance.client.bean;

import java.util.ArrayList;

/**
 * Created by hemingway on 2017/4/13.
 */

public class OrderMemberResultBean {
    private String imgUrl;
    private ArrayList<UserBean> list;

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public ArrayList<UserBean> getList() {
        return list;
    }

    public void setList(ArrayList<UserBean> list) {
        this.list = list;
    }
}
