package com.goldendance.client.register;

import com.goldendance.client.http.GDHttpManager;
import com.goldendance.client.http.GDOnResponseHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hemingway on 2017/1/9.
 */

public class RegisterModel implements IRegisterContract.IModel {

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


    public void register(String mobile, String password, String code) {
    }
}
