package com.goldendance.client.model;

import android.text.TextUtils;

import com.goldendance.client.http.GDHttpManager;
import com.goldendance.client.http.GDOnResponseHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * 购物卡
 * Created by hemingway on 2017/1/25.
 */

public class CardModel {
    public void getCardList(GDOnResponseHandler handler) {
        String url = "getCards";
        Map<String, String> params = new HashMap<>();
        GDHttpManager.doGet(url, params, handler);
    }

    public void buyCard(String tel, String cardId, GDOnResponseHandler handler) {
        String url = "buyCard";

        Map<String, String> params = new HashMap<>();
        if (!TextUtils.isEmpty(tel)) {
            params.put("tel", tel);
        }
        params.put("cardid", cardId);
        GDHttpManager.doGet(url, params, handler);
    }

    public void getCardRecord(GDOnResponseHandler handler) {
        String url = "getCardRecord";

        Map<String, String> params = new HashMap<>();
        GDHttpManager.doGet(url, params, handler);
    }

}
