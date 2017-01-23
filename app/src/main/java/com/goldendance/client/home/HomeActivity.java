package com.goldendance.client.home;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.Toast;

import com.goldendance.client.R;
import com.goldendance.client.base.BaseActivity;
import com.goldendance.client.bean.DataResultBean;
import com.goldendance.client.bean.User;
import com.goldendance.client.bean.UserBean;
import com.goldendance.client.http.GDHttpManager;
import com.goldendance.client.http.GDOnResponseHandler;
import com.goldendance.client.model.IUserModel;
import com.goldendance.client.model.UserModel;
import com.goldendance.client.qrcode.QRCodeActivity;
import com.goldendance.client.utils.GDLogUtils;
import com.goldendance.client.utils.GDSharedPreference;
import com.goldendance.client.utils.JsonUtils;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends BaseActivity implements HomeFragment.OnFragmentInteractionListener, UserFragment.OnFragmentInteractionListener, View.OnClickListener {


    private static final String TAG = HomeActivity.class.getSimpleName();
    private ViewPager vpBody;
    private RadioButton menuUser;
    private RadioButton menuHome;

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent != null) {
            String action = intent.getStringExtra("action");
            if (TextUtils.isEmpty(action)) {
                return;
            }
            if ("loginSuccess".equals(action)) {
                getUserInfo();
            }
        }
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_home);
        getUserInfo();
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

        View ivScan = findViewById(R.id.ivScan);
        ivScan.setOnClickListener(this);
    }

    public void getUserInfo() {
        IUserModel user = new UserModel();
        String token = (String) GDSharedPreference.get(this, GDSharedPreference.KEY_TOKEN, "");
        if (TextUtils.isEmpty(token)) {
            Toast.makeText(this, "用户未登录", Toast.LENGTH_LONG).show();
            return;
        }
        GDLogUtils.i(TAG, "token:" + token);
        User.tokenid = token;
        user.getUserInfo(null, token, new GDOnResponseHandler() {
            @Override
            public void onSuccess(int code, String json) {
                GDLogUtils.i(TAG, "json:" + json);
                if (GDHttpManager.CODE200 != code) {
                    Toast.makeText(HomeActivity.this, "code:" + code, Toast.LENGTH_SHORT).show();
                    return;
                }
                DataResultBean<UserBean> data = JsonUtils.fromJson(json, new TypeToken<DataResultBean<UserBean>>() {
                });
                if (data == null) {
                    Toast.makeText(HomeActivity.this, "data parse error code:" + code, Toast.LENGTH_SHORT).show();
                    return;
                }
                if (GDHttpManager.CODE200 != data.getCode()) {
                    Toast.makeText(HomeActivity.this, data.getCode() + ":" + data.getMessage(), Toast.LENGTH_SHORT).show();
                    if (500 == data.getCode()) {
                        //token错误
                        GDSharedPreference.remove(HomeActivity.this, GDSharedPreference.KEY_TOKEN);
                        User.tokenid = "";
                    }
                    return;
                }
                UserBean user = data.getData();
                User.setUser(user);
                Toast.makeText(HomeActivity.this, User.name + " 登录成功", Toast.LENGTH_LONG).show();
                super.onSuccess(code, json);
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
            case R.id.ivScan:
                Intent intent = new Intent(this, QRCodeActivity.class);
                startActivity(intent);
                break;
        }
    }
}
