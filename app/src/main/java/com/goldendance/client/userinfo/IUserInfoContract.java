package com.goldendance.client.userinfo;

import com.goldendance.client.base.IBasePresenter;
import com.goldendance.client.base.IBaseView;

/**
 * Created by hemingway on 2017/1/17.
 */

public interface IUserInfoContract {
    interface IPresenter extends IBasePresenter {
        void updateIcon(String iconBase64);

        void updateUsername(String username);

        void updateSex(String sex);

        void updatePassword(String md5Psw);

    }

    interface IView extends IBaseView<IPresenter> {

    }
}
