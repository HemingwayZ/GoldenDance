package com.goldendance.client.http;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import com.goldendance.client.bean.User;
import com.goldendance.client.utils.GDLogUtils;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.os.Looper.getMainLooper;

/**
 * 网络请求
 * Created by Hemingway1014@gmail.com on 2016/12/11.
 */

public class GDHttpManager {
    //常数集
    public static final int CODE200 = 200;
    private static final String METHOD_POST = "post";
    private static final String METHOD_GET = "get";
    private static final String TAG = GDHttpManager.class.getSimpleName();

    private static final String SCHEME = "http";
    private static final String HOST = "http://120.77.206.145";
    private static final String PORT = "80";
    private Handler mainHandler;
    private static String BASE_URL = "http://120.77.206.145:8080/JinWuTuan/";
    private static GDHttpManager mInstance;
    private static OkHttpClient mOkHttpClient;

    private GDHttpManager() {
        mainHandler = new Handler(Looper.getMainLooper());

        mOkHttpClient = new OkHttpClient.Builder()
//                .addInterceptor(new BasicAuthInterceptor("255648", "123456"))//auth认证
                .connectTimeout(10, TimeUnit.SECONDS)//链接超时
                .writeTimeout(10, TimeUnit.SECONDS)//写入超时
                .readTimeout(30, TimeUnit.SECONDS)//读取超时
                .followRedirects(true)
                .build();
        StringBuilder sb = new StringBuilder();
//        BASE_URL = sb.append(SCHEME).append("://").append(HOST).toString();
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
        call(METHOD_GET, url, null, handler);
    }

    public static void doGet(String url, Map<String, String> params, final GDOnResponseHandler handler) {
        if (params != null) {
            if (!TextUtils.isEmpty(User.tokenid)) {
                params.put("tokenid", User.tokenid);
            }
            if (!params.isEmpty()) {
                StringBuilder sb = new StringBuilder(url);
                sb.append("?");
                for (Map.Entry<String, String> next : params.entrySet()) {
                    sb.append(next.getKey()).append("=").append(next.getValue()).append("&");
                }
                //去除最后一个&符号
                sb.setCharAt(sb.length() - 1, ' ');
                url = sb.toString().trim();
            }
        }
        getInstance().get(url, handler);
    }

    private void post(String url, Map<String, String> params, final GDOnResponseHandler handler) {
        call(METHOD_POST, url, params, handler);
    }

    private void call(String method, String url, Map<String, String> params, final GDOnResponseHandler handler) {

//        Request.Builder requestBuild = new Request.Builder().url("https://shaishufang.com/index.php/api2/account/isnew?fmt=json");
        Request.Builder requestBuild = new Request.Builder().url(getUrl(url));
        if (METHOD_POST.equals(method)) {
            FormBody.Builder builder = new FormBody.Builder();
            if (params != null) {
                params.put("tokenid", User.tokenid == null ? "" : User.tokenid);
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    builder.add(entry.getKey(), entry.getValue());
                }
                //一旦调用该句，则使用post请求，而忽略get请求
            }
            requestBuild.post(builder.build());
        }

        Request request = requestBuild.build();
        handler.onStart();

        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                e.printStackTrace();
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        handler.onFailed(e);
                        handler.onEnd();
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                GDLogUtils.i(TAG, response.toString());
//                GDLogUtils.i(TAG, "code:" + response.code());
//                GDLogUtils.i(TAG, "message:" + response.message());
//                GDLogUtils.i(TAG, "headers:" + response.headers().toString());
//                GDLogUtils.i(TAG, "body:" + response.body().toString());
                final int code = response.code();
                final String result = response.body().string();

                GDLogUtils.i(TAG, "body:" + result);
                boolean successful = response.isSuccessful();
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {

                        handler.onSuccess(code, result);
                        handler.onEnd();
                    }
                });
            }
        });
    }

    //对外公布的方法
    public static void doGet(String url, final GDOnResponseHandler handler) {
        doGet(url, null, handler);
    }

    public static void doPost(String url, Map<String, String> params, final GDOnResponseHandler handler) {
        getInstance().post(url, params, handler);
    }
}
