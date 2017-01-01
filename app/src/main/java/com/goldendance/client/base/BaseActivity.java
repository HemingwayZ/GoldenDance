package com.goldendance.client.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Hemingway1014@gmail.com on 2016/12/10.
 */

public abstract class BaseActivity extends AppCompatActivity {
    //不能重写该方法，否则EventBus会出现异常
//    @Override
//    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
//        super.onCreate(savedInstanceState, persistentState);
//    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BaseApplication application = (BaseApplication) getApplication();
        application.addActivity(this);
        initIntent();
        initView(savedInstanceState);

    }

    public void initIntent() {
    }

    ;

    public abstract void initView(Bundle savedInstanceState);


    @Override
    protected void onDestroy() {
        ((BaseApplication) getApplication()).removeActivity(this);
        super.onDestroy();
    }
}
