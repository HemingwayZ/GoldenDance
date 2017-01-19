package com.goldendance.client.userinfo;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.goldendance.client.R;
import com.goldendance.client.base.BaseActivity;

public class UserInfoActivity extends BaseActivity implements View.OnClickListener {

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
        startActivity(intent);
        switch (v.getId()) {
            case R.id.flIcon:

                break;
        }
    }
}
