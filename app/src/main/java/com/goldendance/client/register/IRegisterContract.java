package com.goldendance.client.register;

import com.goldendance.client.base.IBaseModel;
import com.goldendance.client.base.IBasePresenter;
import com.goldendance.client.base.IBaseView;
import com.goldendance.client.http.GDOnResponseHandler;

/**
 * Created by hemingway on 2017/1/9.
 */

public interface IRegisterContract {

    interface IModel extends IBaseModel {
        void getCode(String mobile, String type, GDOnResponseHandler handler);
    }

    interface IPresenter extends IBasePresenter {
        void getCode();
    }

    interface IView extends IBaseView<IPresenter> {
        void showProgress();

        void hideProgress();

        String getMobile();
    }
}
