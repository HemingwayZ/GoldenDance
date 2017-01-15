package com.goldendance.client.register;

import android.text.TextUtils;

import com.goldendance.client.R;
import com.goldendance.client.http.GDHttpManager;
import com.goldendance.client.http.GDOnResponseHandler;
import com.goldendance.client.bean.DataResultBean;
import com.goldendance.client.bean.MessageBean;
import com.goldendance.client.bean.RegistResultBean;
import com.goldendance.client.model.IUserModel;
import com.goldendance.client.model.UserModel;
import com.goldendance.client.utils.GDLogUtils;
import com.goldendance.client.utils.GDTextUtils;
import com.goldendance.client.utils.JsonUtils;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;

/**
 * Created by hemingway on 2017/1/9.
 */

public class RegisterPresenter implements IRegisterContract.IPresenter {
    private static final String TAG = RegisterPresenter.class.getSimpleName();
    IRegisterContract.IView mView;
    IUserModel mModel;

    public RegisterPresenter(IRegisterContract.IView mView) {
        this.mView = mView;
        mView.setPresenter(this);
        mModel = new UserModel();
    }

    public void getCode() {
        String mobile = mView.getMobile();

        if (TextUtils.isEmpty(mobile) || mobile.length() != 11) {
            mView.showToast(R.string.mobile_length_limit, "");
            return;
        }
        if (mView.isCounting()) {
            mView.showToast(R.string.try_after_60s, "");
            return;
        }
        //请求数据
        mView.showProgress();
        mModel.getCode(mobile, "0", new GDOnResponseHandler() {

            @Override
            public void onFailed(IOException e) {
                if (e != null) {
                    mView.showToast(R.string.network_error, e.getMessage());
                }
                super.onFailed(e);
            }

            @Override
            public void onSuccess(int code, String json) {
                GDLogUtils.i(TAG, json);
//                mView.hideProgress();
                MessageBean msgBean = JsonUtils.fromJson(json, new TypeToken<MessageBean>() {
                });
                if (msgBean == null) {
                    mView.showToast(R.string.json_parse_error, json);
                    return;
                }
//                短信平台的码是2
                if (GDHttpManager.CODE200 == msgBean.getCode() || 2 == msgBean.getCode()) {
                    mView.showToast(R.string.empty_msg, msgBean.getMessage());
                    mView.startCount();
                } else {
                    mView.showToast(R.string.empty_msg, String.valueOf(msgBean.getCode()) + ":" + msgBean.getMessage());
                }
                super.onSuccess(code, json);
            }

            @Override
            public void onEnd() {
                mView.hideProgress();
                super.onEnd();
            }
        });

    }

    @Override
    public void doRegister() {
        String mobile = mView.getMobile();
        if (TextUtils.isEmpty(mobile) || mobile.length() != 11) {
            mView.showToast(R.string.mobile_length_limit, "");
            return;
        }
        String code = mView.getMobileCode();
        if (TextUtils.isEmpty(code)) {
            mView.showToast(R.string.mobile_code_empty, "");
            return;
        }

        String password = mView.getPassword();
        if (TextUtils.isEmpty(password) || password.length() < 6) {
            mView.showToast(R.string.password_length_limit, "");
            return;
        }
        password = GDTextUtils.getMD5(password);
        mView.showProgress();
        mModel.doRegister(mobile, password, code, new GDOnResponseHandler() {

            @Override
            public void onFailed(IOException e) {
                if (e != null) {
                    mView.showToast(R.string.network_error, e.getMessage());
                }
                super.onFailed(e);
            }

            @Override
            public void onEnd() {
                mView.hideProgress();
                super.onEnd();
            }

            @Override
            public void onSuccess(int code, String json) {

                if (GDHttpManager.CODE200 != code) {
                    //普通网络请求
                    mView.showToast(R.string.network_error, String.valueOf(code));
                    return;
                }
                DataResultBean<RegistResultBean> data = JsonUtils.fromJson(json, new TypeToken<DataResultBean<RegistResultBean>>() {
                });
                if (data == null) {
                    mView.showToast(R.string.json_parse_error, json);
                    return;
                }
                if (GDHttpManager.CODE200 != data.getCode()) {
                    mView.showToast(R.string.empty_msg, String.valueOf(data.getCode()) + ":" + data.getMessage());
                    return;
                }
                //请求成功
                mView.showToast(R.string.empty_msg, data.getMessage());
                mView.registSucceed();
                super.onSuccess(code, json);
            }
        });

    }

    @Override
    public void onStart() {

    }
}
