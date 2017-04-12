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
     * type 课程类型
     *
     * @param type
     * @param handler
     */
    public void getListCourse(String type, String date, String storeid, int page, int rows, GDOnResponseHandler handler) {
        String url = "getListCourse";
        Map<String, String> params = new HashMap<>();
        params.put("type", type);
        params.put("starttime", date);
        params.put("storeid", storeid);
        params.put("pageSize", String.valueOf(rows));
        params.put("page", String.valueOf(page));
        GDHttpManager.doGet(url, params, handler);
    }

    /**
     * 预约
     *
     * @param handler
     */
    public void orderCourse(String courseid, GDOnResponseHandler handler) {
        String url = "orderCourse";
        Map<String, String> params = new HashMap<>();
        params.put("courseid", courseid);
        GDHttpManager.doPost(url, params, handler);
    }


    /**
     * 预约
     *
     * @param handler
     */
    public void getOrderedMember(String courseid, GDOnResponseHandler handler) {
        String url = "getOrderedMember";
        Map<String, String> params = new HashMap<>();
        params.put("courseid", courseid);
        GDHttpManager.doGet(url, params, handler);
    }

    /**
     * 预约
     *
     * @param handler
     */
    public void getCourseRecord(GDOnResponseHandler handler) {
        String url = "getCourseRecord";
        Map<String, String> params = new HashMap<>();
//        params.put("courseid", courseid);
        GDHttpManager.doGet(url, params, handler);
    }


    /**
     * 获取单个课程信息
     *
     * @param handler
     */
    public void findCourseMes(String courseid, String tel, GDOnResponseHandler handler) {
        String url = "findCourseMes";
        Map<String, String> params = new HashMap<>();
        params.put("courseid", courseid);
        params.put("tel", tel);
        GDHttpManager.doGet(url, params, handler);
    }

    /**
     * 获取单个课程信息
     *
     * @param handler
     */
    public void unOrderCourse(String courseid, GDOnResponseHandler handler) {
        String url = "unOrderCourse";
        Map<String, String> params = new HashMap<>();
        params.put("courseid", courseid);
        GDHttpManager.doPost(url, params, handler);
    }
}
