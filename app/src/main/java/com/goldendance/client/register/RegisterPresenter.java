package com.goldendance.client.register;

/**
 * Created by hemingway on 2017/1/9.
 */

public class RegisterPresenter implements IRegisterContract.IPresenter {
    IRegisterContract.IView mView;

    public RegisterPresenter(IRegisterContract.IView mView) {
        this.mView = mView;
        mView.setPresenter(this);
    }

    @Override
    public void onStart() {

    }
}
