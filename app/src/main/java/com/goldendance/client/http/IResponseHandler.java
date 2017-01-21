package com.goldendance.client.http;

import com.goldendance.client.bean.DataResultBean;

import java.io.IOException;

import okhttp3.Response;

/**
 * Created by Hemingway1014@gmail.com on 2016/12/11.
 */

public interface IResponseHandler<T> {
    void onStart();

    void onEnd();

    void onSuccess(int code, String json);
    public void onSuccess(int code, DataResultBean<T> data);
    void onFailed(IOException e);
}
