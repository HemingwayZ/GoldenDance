package com.goldendance.client;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.goldendance.client.base.BaseActivity;

import com.goldendance.client.http.GDHttpManager;
import com.goldendance.client.http.GDOnResponseHandler;
import com.goldendance.client.utils.GDLogUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;

import okhttp3.Response;

public class MainActivity extends BaseActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EventBus.getDefault().register(this);
        findViewById(R.id.btnCrash).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                GDLogUtils.i(TAG, "" + 1 / 0);
                EventBus.getDefault().post(new MessageEvent("aaa", "aaa"));
                initData();
            }
        });
    }

    public class MessageEvent {
        public final String name;
        public final String password;

        public MessageEvent(String name, String password) {
            this.name = name;
            this.password = password;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void helloEventBus(MessageEvent message) {
        GDLogUtils.d(TAG, message.name);
    }


    private void initData() {
//        /api2/bookroom/profile?show=tag&page_size=1&undeal=true&fmt=json
        String url = "/api2/bookroom/profile?show=tag&page_size=1&undeal=true&fmt=json";

        GDHttpManager.doGet(url, new GDOnResponseHandler() {
            @Override
            public void onStart() {
                GDLogUtils.d(TAG, "start ---------------------");
                super.onStart();
            }

            @Override
            public void onSuccess(Response response) {
                try {
                    GDLogUtils.d(TAG, "body:" + response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                super.onSuccess(response);
            }

            @Override
            public void onFailed(IOException e) {
                Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                super.onFailed(e);
            }

            @Override
            public void onEnd() {
                GDLogUtils.d(TAG, "end  ---------------------");
                super.onEnd();
            }
        });
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
