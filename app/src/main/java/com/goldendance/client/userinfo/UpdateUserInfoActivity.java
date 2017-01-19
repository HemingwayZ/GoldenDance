package com.goldendance.client.userinfo;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.goldendance.client.R;
import com.goldendance.client.base.BaseActivity;

public class UpdateUserInfoActivity extends BaseActivity implements UpdateUserInfoFragment.OnFragmentInteractionListener {


    @Override
    public void initView(Bundle savedInstanceState) {
        overridePendingTransition(android.R.anim.fade_in, 0);
        setContentView(R.layout.activity_update_user_info);
        String action = getIntent().getStringExtra("action");
        UpdateUserInfoFragment userInfoFragment = (UpdateUserInfoFragment) getSupportFragmentManager().findFragmentById(R.id.flBottom);
        if (userInfoFragment == null) {
            userInfoFragment = UpdateUserInfoFragment.newInstance(action, "");
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.add(R.id.flBottom, userInfoFragment);
            fragmentTransaction.commit();
        }
        new UpdateUserInfoPresenter(userInfoFragment);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.bottom_up);
        findViewById(R.id.flBottom).setAnimation(animation);
        animation.start();
    }

    @Override
    public void onBackPressed() {
//        Animation animation = AnimationUtils.loadAnimation(this, R.anim.top_down);
//        findViewById(R.id.flBottom).setAnimation(animation);
//        animation.setAnimationListener(new Animation.AnimationListener() {
//            @Override
//            public void onAnimationStart(Animation animation) {
//
//            }
//
//            @Override
//            public void onAnimationEnd(Animation animation) {
//                finish();
//
//            }
//
//            @Override
//            public void onAnimationRepeat(Animation animation) {
//
//            }
//        });
//        animation.start();
        super.onBackPressed();
        overridePendingTransition(0, android.R.anim.fade_out);
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
