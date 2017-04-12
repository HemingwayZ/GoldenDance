package com.goldendance.client.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by hemingway on 2017/4/13.
 */

public class CourseHistoryBean implements Serializable{
    private String imgUrl;
    ArrayList<CourseBean> list;

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public ArrayList<CourseBean> getList() {
        return list;
    }

    public void setList(ArrayList<CourseBean> list) {
        this.list = list;
    }
}
