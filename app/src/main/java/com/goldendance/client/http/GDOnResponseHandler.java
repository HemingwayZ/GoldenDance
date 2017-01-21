package com.goldendance.client.http;

import com.goldendance.client.base.IBaseView;
import com.goldendance.client.bean.DataResultBean;

import java.io.IOException;

import okhttp3.Response;

/**
 * Created by Hemingway1014@gmail.com on 2016/12/11.
 */

public class GDOnResponseHandler<T> implements IResponseHandler<T> {


    @Override
    public void onStart() {

    }

    @Override
    public void onEnd() {

    }

    @Override
    public void onSuccess(int code, String json) {

    }

    @Override
    public void onSuccess(int code, DataResultBean<T> data) {

    }

    @Override
    public void onFailed(IOException e) {

    }

}
