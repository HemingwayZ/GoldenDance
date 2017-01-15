package com.goldendance.client.register;

import android.support.annotation.IdRes;
import android.support.annotation.StringRes;

import com.goldendance.client.base.IBaseModel;
import com.goldendance.client.base.IBasePresenter;
import com.goldendance.client.base.IBaseView;
import com.goldendance.client.http.GDOnResponseHandler;

/**
 * Created by hemingway on 2017/1/9.
 */

public interface IRegisterContract {


    interface IPresenter extends IBasePresenter {
        void getCode();

        void doRegister();
    }

    interface IView extends IBaseView<IPresenter> {

        String getMobile();

        String getPassword();

        void showToast(@StringRes int idStr, String msg);

        void startCount();

        boolean isCounting();

        String getMobileCode();

        void registSucceed();
    }
}
