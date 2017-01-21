package com.goldendance.client.userinfo;

import com.goldendance.client.base.IBasePresenter;
import com.goldendance.client.base.IBaseView;

/**
 * Created by hemingway on 2017/1/17.
 */

public interface IUpdateUserInfoContract {
    interface IPresenter extends IBasePresenter {

        void confirm();


    }

    interface IView extends IBaseView<IPresenter> {
        String getAction();

        String getEditText();

        void showMsg(String msg);

        void updateSuccess(String key, String value);

        String getGender();
    }
}
