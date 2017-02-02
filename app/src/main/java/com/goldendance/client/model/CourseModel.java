package com.goldendance.client.model;

import com.goldendance.client.http.GDHttpManager;
import com.goldendance.client.http.GDOnResponseHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hemingway on 2017/1/31.
 */

public class CourseModel {
    /**
     * 获取书店
     *
     * @param handler
     */
    public void getStore(GDOnResponseHandler handler) {
        String url = "storeComboBox";
        Map<String, String> params = new HashMap<>();
        GDHttpManager.doGet(url, params, handler);
    }

    /**
     * 获取书店
     *
     * @param handler
     */
    public void getListCourse(String date, String storeid, int page, int rows, GDOnResponseHandler handler) {
        String url = "getListCourse";
        Map<String, String> params = new HashMap<>();
//        params.put("starttime", date);
        params.put("storied",storeid);
        params.put("rows", String.valueOf(rows));
        params.put("page", String.valueOf(page));
        GDHttpManager.doGet(url, params, handler);
    }
}
