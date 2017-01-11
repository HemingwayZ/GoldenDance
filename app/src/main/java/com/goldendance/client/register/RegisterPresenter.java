package com.goldendance.client.register;

import com.goldendance.client.R;
import com.goldendance.client.http.GDHttpManager;
import com.goldendance.client.http.GDOnResponseHandler;
import com.goldendance.client.model.MessageBean;
import com.goldendance.client.utils.GDLogUtils;
import com.goldendance.client.utils.JsonUtils;
import com.google.gson.reflect.TypeToken;

/**
 * Created by hemingway on 2017/1/9.
 */

public class RegisterPresenter implements IRegisterContract.IPresenter {
    private static final String TAG = RegisterPresenter.class.getSimpleName();
    IRegisterContract.IView mView;
    IRegisterContract.IModel mModel;

    public RegisterPresenter(IRegisterContract.IView mView) {
        this.mView = mView;
        mView.setPresenter(this);
        mModel = new RegisterModel();
    }

    public void getCode() {
        String mobile = mView.getMobile();

        if (mobile.length() != 11) {
            mView.showToast(R.string.mobile_length_limit, "");
            return;
        }
        if(mView.isCounting()){
            mView.showToast(R.string.try_after_60s, "");
            return;
        }
        //请求数据
        mView.showProgress();
        mModel.getCode(mobile, "0", new GDOnResponseHandler() {
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
                if (GDHttpManager.CODE200 == msgBean.getCode()) {
                    mView.showToast(R.string.empty_msg, msgBean.getMessage());
                    mView.startCount();
                } else {
                    StringBuffer sb = new StringBuffer();
                    sb.append(msgBean.getCode()).append(":").append(msgBean.getMessage());
                    mView.showToast(R.string.empty_msg, sb.toString());
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
    public void onStart() {

    }
}
