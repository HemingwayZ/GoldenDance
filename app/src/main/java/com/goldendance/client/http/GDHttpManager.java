package com.goldendance.client.http;

import android.text.TextUtils;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Authenticator;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Credentials;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;

/**
 * 网络请求
 * Created by Hemingway1014@gmail.com on 2016/12/11.
 */

public class GDHttpManager {
    private static final String TAG = GDHttpManager.class.getSimpleName();

    private static final String SCHEME = "https";
    private static final String HOST = "shaishufang.com";
    private static final String PORT = "80";

    private static String BASE_URL = "https://shaishufang.com";
    private static GDHttpManager mInstance;
    private static OkHttpClient mOkHttpClient;

    private GDHttpManager() {
        mOkHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new BasicAuthInterceptor("255648", "123456"))//auth认证
                .connectTimeout(10, TimeUnit.SECONDS)//链接超时
                .writeTimeout(10, TimeUnit.SECONDS)//写入超时
                .readTimeout(30, TimeUnit.SECONDS)//读取超时
                .build();
        StringBuilder sb = new StringBuilder();
        BASE_URL = sb.append(SCHEME).append("://").append(HOST).toString();
    }

    private static GDHttpManager getInstance() {
        if (mInstance == null) {
            synchronized (GDHttpManager.class) {
                if (mInstance == null) {
                    mInstance = new GDHttpManager();
                }
            }
        }
        return mInstance;
    }

    private static String getUrl(String url) {
        if (TextUtils.isEmpty(url)) {
            throw new NullPointerException("url can not be null");
        }
        StringBuilder sb = new StringBuilder();
//        return sb.append(SCHEME).append(":").append(PORT).append("://").append(HOST).append(url).toString();
        return sb.append(BASE_URL).append(url).toString();
    }

    private void get(String url, final GDOnResponseHandler handler) {
        call(url, null, handler);
    }

    private void post(String url, Map<String, String> params, final GDOnResponseHandler handler) {

        call(url, params, handler);
    }

    private void call(String url, Map<String, String> params, final GDOnResponseHandler handler) {

        Request.Builder requestBuild = new Request.Builder().url(getUrl(url));
        if (params != null) {
            FormBody.Builder builder = new FormBody.Builder();
            requestBuild.post(builder.build());
        }
        Request request = requestBuild.build();
        handler.onStart();

        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

                handler.onFailed(e);
                handler.onEnd();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                handler.onSuccess(response);
                handler.onEnd();
            }
        });
    }

    //对外公布的方法
    public static void doGet(String url, final GDOnResponseHandler handler) {
        getInstance().get(url, handler);
    }

    public static void getPost(String url, Map<String, String> params, final GDOnResponseHandler handler) {
        getInstance().post(url, params, handler);
    }
}
