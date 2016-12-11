package com.goldendance.client;

import android.os.Bundle;
import android.view.View;

import com.goldendance.client.base.BaseActivity;

import com.goldendance.client.utils.LogUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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
//                LogUtils.i(TAG, "" + 1 / 0);
                EventBus.getDefault().post(new MessageEvent("aaa", "aaa"));
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
        LogUtils.d(TAG, message.name);
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
