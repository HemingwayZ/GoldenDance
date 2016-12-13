package com.goldendance.client.base;

import android.app.Application;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Hemingway1014@gmail.com on 2016/12/10.
 */

public abstract class BaseActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        BaseApplication application = (BaseApplication) getApplication();
        application.addActivity(this);
        initIntent();
        initView(savedInstanceState);
        super.onCreate(savedInstanceState);
    }

    public abstract void initIntent();

    public abstract void initView(Bundle savedInstanceState);


    @Override
    protected void onDestroy() {
        ((BaseApplication) getApplication()).removeActivity(this);
        super.onDestroy();
    }
}
