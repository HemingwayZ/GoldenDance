package com.goldendance.client.model;

import com.goldendance.client.http.GDHttpManager;
import com.goldendance.client.http.GDOnResponseHandler;
import com.goldendance.client.register.RegisterFragment;

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


    public void doRegister(String action, String mobile, String password, String code, GDOnResponseHandler handler) {
        String url = "regist";
        if (RegisterFragment.ACTION_RESET_PSW.equals(action)) {
            url = "forgetPassword";
        }
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
        String url = "getUserMessage";
        Map<String, String> params = new HashMap<>();
        if (userId != null) {
            //获取用户个人资料
            params.put("userid", userId);
        }
        GDHttpManager.doGet(url, params, handler);
    }

    @Override
    public void updateUsername(String userId, String nickname, GDOnResponseHandler handler) {
        String url = "updateName";
        Map<String, String> params = new HashMap<>();
        if (userId != null) {
            //获取用户个人资料
            params.put("userid", userId);
        }
        params.put("name", nickname);
        GDHttpManager.doPost(url, params, handler);
    }

    @Override
    public void updateGender(String userId, String gender, GDOnResponseHandler handler) {
        String url = "updateSex";
        Map<String, String> params = new HashMap<>();
        if (userId != null) {
            //获取用户个人资料
            params.put("userid", userId);
        }
        params.put("sex", gender);
        GDHttpManager.doPost(url, params, handler);
    }

    @Override
    public void updateIcon(String userId, String base64Icon, GDOnResponseHandler handler) {
        String url = "updateIcon";
        Map<String, String> params = new HashMap<>();
        if (userId != null) {
            //获取用户个人资料
            params.put("userid", userId);
        }
        params.put("icon", base64Icon);
        GDHttpManager.doPost(url, params, handler);
    }

    @Override
    public void updatePsw(String userId, String password, GDOnResponseHandler handler) {
        String url = "updatePassword";
        Map<String, String> params = new HashMap<>();
        if (userId != null) {
            //获取用户个人资料
            params.put("userid", userId);
        }
        params.put("password", password);
        GDHttpManager.doPost(url, params, handler);
    }
}
