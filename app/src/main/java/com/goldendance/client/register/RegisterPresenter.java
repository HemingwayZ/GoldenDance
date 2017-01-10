package com.goldendance.client.register;

import com.goldendance.client.http.GDOnResponseHandler;
import com.goldendance.client.utils.GDLogUtils;

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
        mView.showProgress();
        mModel.getCode(mobile, "0", new GDOnResponseHandler() {
            @Override
            public void onSuccess(int code, String json) {
                GDLogUtils.i(TAG, json);
//                mView.hideProgress();
                super.onSuccess(code, json);
            }

            @Override
            public void onEnd() {

                super.onEnd();
            }
        });

    }

    @Override
    public void onStart() {

    }
}
