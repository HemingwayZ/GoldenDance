package com.goldendance.client.model;

import com.goldendance.client.http.GDHttpManager;
import com.goldendance.client.http.GDOnResponseHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hemingway on 2017/2/12.
 */

public class SettingModel {

    public void getHomeHead(GDOnResponseHandler handler) {
        String url = "homeHead";
        Map<String, String> params = new HashMap<>();
        GDHttpManager.doGet(url, params, handler);
    }

}
