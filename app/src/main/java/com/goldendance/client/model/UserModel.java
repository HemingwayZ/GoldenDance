package com.goldendance.client.model;

import com.goldendance.client.http.GDHttpManager;
import com.goldendance.client.http.GDOnResponseHandler;
import com.goldendance.client.register.IRegisterContract;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hemingway on 2017/1/9.
 */

public class UserModel implements IUserModel {

    /**
     * @param mobile 需要获取验证码的手机
     * @param type   获取验证码的方式 0 注册 1 ：重置密码
     */
    public void getCode(String mobile, String type, GDOnResponseHandler handler) {
        String url = "sendsms";
        Map<String, String> params = new HashMap<>();
        params.put("tel", mobile);
        params.put("type", type);
        GDHttpManager.doGet(url, params, handler);
    }


    public void doRegister(String mobile, String password, String code, GDOnResponseHandler handler) {
        String url = "regist";
        Map<String, String> params = new HashMap<>();
        params.put("tel", mobile);
        params.put("telcode", code);
        params.put("password", password);
        GDHttpManager.doPost(url, params, handler);
    }

    @Override
    public void getToken(String mobile, String password, GDOnResponseHandler handler) {
        String url = "getToken";
        Map<String, String> params = new HashMap<>();
        params.put("tel", mobile);
        params.put("password", password);
        GDHttpManager.doPost(url, params, handler);
    }

    @Override
    public void getUserInfo(String userId, String tokenid, GDOnResponseHandler handler) {

    }
}
