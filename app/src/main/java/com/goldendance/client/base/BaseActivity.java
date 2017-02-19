package com.goldendance.client.base;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Toast;

import com.goldendance.client.bean.User;
import com.goldendance.client.login.LoginActivity;

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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BaseApplication application = (BaseApplication) getApplication();
        application.addActivity(this);
        initIntent();
        initView(savedInstanceState);

    }

    public void initIntent() {
    }

    public abstract void initView(Bundle savedInstanceState);

    protected void toLogin() {
        Intent intent = new Intent();
        intent.setClass(this, LoginActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        ((BaseApplication) getApplication()).removeActivity(this);
        super.onDestroy();
    }

    protected void showToast(String msg) {
        if (TextUtils.isEmpty(msg)) {
            msg = "msg is null";
        }
        final AlertDialog ad = new AlertDialog.Builder(this).create();
        ad.setMessage(msg);
        ad.setButton(AlertDialog.BUTTON_NEGATIVE, "取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ad.dismiss();
            }
        });
        ad.setButton(AlertDialog.BUTTON_POSITIVE, "返回", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ad.dismiss();
                onBackPressed();
            }
        });
        ad.show();
    }

}
