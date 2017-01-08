package com.goldendance.client.home;

import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;

import com.goldendance.client.R;
import com.goldendance.client.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends BaseActivity implements HomeFragment.OnFragmentInteractionListener, UserFragment.OnFragmentInteractionListener, View.OnClickListener {


    private ViewPager vpBody;
    private RadioButton menuUser;
    private RadioButton menuHome;

    @Override
    public void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_home);
        //用户页面
        UserFragment userFragment = UserFragment.newInstance("", "");
        //首页
        HomeFragment homeFragment = HomeFragment.newInstance("", "");

        List<Fragment> mList = new ArrayList<>();
        mList.add(homeFragment);
        mList.add(userFragment);

        //底部按钮
        menuUser = (RadioButton) findViewById(R.id.rbMenuUser);
        menuUser.setChecked(false);
        menuUser.setOnClickListener(this);

        menuHome = (RadioButton) findViewById(R.id.rbMenuHome);
        menuHome.setChecked(true);
        menuHome.setOnClickListener(this);
        //viewpager
        vpBody = (ViewPager) findViewById(R.id.vpBody);
        HomePagerAdapter adapter = new HomePagerAdapter(getSupportFragmentManager(), mList);
        vpBody.setAdapter(adapter);
        vpBody.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        menuHome.setChecked(true);
                        break;
                    case 1:
                        menuUser.setChecked(true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rbMenuHome:
                if (menuHome.isChecked()) {
                    //首页
                    vpBody.setCurrentItem(0);
                } else {
                    //用户页面
                    vpBody.setCurrentItem(1);
                }
                break;
            case R.id.rbMenuUser:
                if (menuHome.isChecked()) {
                    //首页
                    vpBody.setCurrentItem(0);
                } else {
                    //用户页面
                    vpBody.setCurrentItem(1);
                }
                break;
        }
    }
}
