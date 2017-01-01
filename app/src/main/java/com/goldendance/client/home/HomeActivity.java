package com.goldendance.client.home;

import android.net.Uri;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CheckBox;

import com.goldendance.client.R;
import com.goldendance.client.base.BaseActivity;

public class HomeActivity extends BaseActivity implements HomeFragment.OnFragmentInteractionListener, UserFragment.OnFragmentInteractionListener {

    @Override
    public void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_home);

        //用户页面
        UserFragment userFragment = (UserFragment) getSupportFragmentManager().findFragmentById(R.id.flBody);
        if (userFragment == null) {
            userFragment = UserFragment.newInstance("", "");

            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.add(R.id.flBody, userFragment);
            ft.commit();
        }
        //首页
        HomeFragment homeFragment = (HomeFragment) getSupportFragmentManager().findFragmentById(R.id.flBody);
        if (homeFragment == null) {
            homeFragment = HomeFragment.newInstance("", "");

            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.add(R.id.flBody, homeFragment);
            ft.commit();
        }
        //底部按钮
        CheckBox menuUser = (CheckBox) findViewById(R.id.rbMenuUser);
        menuUser.setChecked(false);
        CheckBox menuHome = (CheckBox) findViewById(R.id.rbMenuHome);
        menuHome.setChecked(true);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
