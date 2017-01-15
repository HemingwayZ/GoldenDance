package com.goldendance.client.http;

import java.io.IOException;

import okhttp3.Response;

/**
 * Created by Hemingway1014@gmail.com on 2016/12/11.
 */

public interface IResponseHandler {
    void onStart();

    void onEnd();

    void onSuccess(int code, String json);

    void onFailed(IOException e);
}
