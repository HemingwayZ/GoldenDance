package com.goldendance.client.model;

import com.goldendance.client.http.GDOnResponseHandler;

/**
 * Created by hemingway on 2017/1/13.
 */

public interface IUserModel {
    void getCode(String mobile, String type, GDOnResponseHandler handler);

    void doRegister(String mobile, String password, String code, GDOnResponseHandler handler);

    void getToken(String tel, String password, GDOnResponseHandler handler);

    void getUserInfo(String userId, String tokenid, GDOnResponseHandler handler);
}
