package com.goldendance.client.userinfo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.goldendance.client.R;
import com.goldendance.client.base.BaseActivity;

public class UserInfoActivity extends BaseActivity implements View.OnClickListener {
    public static final String ACTION_ICON = "icon";
    public static final String ACTION_GENDER = "gender";
    public static final String ACTION_PSW = "psw";
    public static final String ACTION_USERNAME = "username";

    private static final int REQUEST_USERINFO = 10000;

    @Override
    public void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_user_info);
        findViewById(R.id.flIcon).setOnClickListener(this);
        findViewById(R.id.flGender).setOnClickListener(this);
        findViewById(R.id.flUsername).setOnClickListener(this);
        findViewById(R.id.flPsw).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, UpdateUserInfoActivity.class);
        switch (v.getId()) {
            case R.id.flIcon:
                intent.putExtra("action", ACTION_ICON);
                break;
            case R.id.flGender:
                intent.putExtra("action", ACTION_GENDER);
            case R.id.flPsw:
                intent.putExtra("action", ACTION_PSW);
                break;
            case R.id.flUsername:
                intent.putExtra("action", ACTION_USERNAME);
                break;
        }
        startActivityForResult(intent, REQUEST_USERINFO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
    }
}
