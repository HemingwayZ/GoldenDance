package com.goldendance.client.base;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Toast;

import com.alipay.android.phone.mrpc.core.HttpManager;
import com.goldendance.client.R;
import com.goldendance.client.bean.MessageBean;
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

    @Override
    public void onBackPressed() {
        HttpManager.getInstance(this).close();
        super.onBackPressed();

    }

    public boolean reLogin(int code) {
        if (MessageBean.CODE_TOKEN_OVERTIME == code||code==502) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("用户认证超时，请重新登陆");
            builder.setNegativeButton(getString(R.string.confirm), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(BaseActivity.this,LoginActivity.class);
                    startActivity(intent);
                }
            }).setPositiveButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            }).show();
            return true;
        }
        return false;
    }
}
