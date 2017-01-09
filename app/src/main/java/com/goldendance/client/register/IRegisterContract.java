package com.goldendance.client.register;

import com.goldendance.client.base.IBaseModel;
import com.goldendance.client.base.IBasePresenter;
import com.goldendance.client.base.IBaseView;

/**
 * Created by hemingway on 2017/1/9.
 */

public interface IRegisterContract {

    interface IModel extends IBaseModel {

    }

    interface IPresenter extends IBasePresenter {

    }

    interface IView extends IBaseView<IPresenter> {
    }
}
