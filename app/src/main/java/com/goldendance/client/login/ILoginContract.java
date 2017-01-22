package com.goldendance.client.login;

import android.support.annotation.StringRes;

import com.goldendance.client.base.IBasePresenter;
import com.goldendance.client.base.IBaseView;

/**
 * Created by hemingway on 2017/1/13.
 */

public interface ILoginContract {
    interface IPresenter extends IBasePresenter {
        void doLogin();

        void getToken();
    }

    interface IView extends IBaseView<IPresenter> {
        String getMobile();

        String getPassword();

        void showToast(@StringRes int idStr, String msg);

        void loginSuccess(String tokens, String finalPsw);
    }
}
