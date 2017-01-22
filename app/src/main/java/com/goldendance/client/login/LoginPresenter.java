package com.goldendance.client.login;

import android.text.TextUtils;

import com.goldendance.client.R;
import com.goldendance.client.bean.DataResultBean;
import com.goldendance.client.bean.TokenBean;
import com.goldendance.client.http.GDHttpManager;
import com.goldendance.client.http.GDOnResponseHandler;
import com.goldendance.client.model.IUserModel;
import com.goldendance.client.model.UserModel;
import com.goldendance.client.utils.GDTextUtils;
import com.goldendance.client.utils.JsonUtils;
import com.google.gson.reflect.TypeToken;

/**
 * Created by hemingway on 2017/1/13.
 */

public class LoginPresenter implements ILoginContract.IPresenter {
    ILoginContract.IView mView;
    IUserModel mModel;

    public LoginPresenter(ILoginContract.IView mView) {
        this.mView = mView;
        this.mView.setPresenter(this);
        mModel = new UserModel();
    }

    @Override
    public void onStart() {

    }

    @Override
    public void doLogin() {

    }

    @Override
    public void getToken() {
        String mobile = mView.getMobile();
        String  password = mView.getPassword();
        password = GDTextUtils.getMD5(password);
        final String finalPsw = password;
        mView.showProgress();
        mModel.getToken(mobile, password, new GDOnResponseHandler() {
            @Override
            public void onEnd() {
                mView.hideProgress();
                super.onEnd();
            }

            @Override
            public void onSuccess(int code, String json) {
                if (GDHttpManager.CODE200 != code) {
                    mView.showToast(R.string.network_error, String.valueOf(code));
                    return;
                }
                DataResultBean<TokenBean> data = JsonUtils.fromJson(json, new TypeToken<DataResultBean<TokenBean>>() {
                });
                if (data == null) {
                    mView.showToast(R.string.json_parse_error, json);
                    return;
                }
                if (GDHttpManager.CODE200 != data.getCode()) {
                    mView.showToast(R.string.empty_msg, String.valueOf(data.getCode()) + ":" + data.getMessage());
                    return;
                }
                TokenBean tokenBean = data.getData();
                if (tokenBean == null) {
                    mView.showToast(R.string.empty_msg, "data content is null");
                    return;
                }
                String tokenid = tokenBean.getTokenid();
                if (TextUtils.isEmpty(tokenid)) {
                    mView.showToast(R.string.empty_msg, "tokenid is null");
                    return;
                }
                mView.showToast(R.string.empty_msg, tokenid);
                mView.loginSuccess(tokenid,finalPsw);
                super.onSuccess(code, json);
            }
        });
    }
}
