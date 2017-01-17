package com.goldendance.client.userinfo;

import android.net.Uri;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.goldendance.client.R;
import com.goldendance.client.base.BaseActivity;

public class UserInfoActivity extends BaseActivity implements UserInfoFragment.OnFragmentInteractionListener {

    @Override
    public void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_user_info);
        UserInfoFragment userInfoFragment = (UserInfoFragment) getSupportFragmentManager().findFragmentById(R.id.activity_user_info);
        if (userInfoFragment == null) {
            userInfoFragment = UserInfoFragment.newInstance("", "");
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.add(R.id.activity_user_info, userInfoFragment);
            fragmentTransaction.commit();
        }
        new UserInfoPresenter(userInfoFragment);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
