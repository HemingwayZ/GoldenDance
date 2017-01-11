package com.goldendance.client.register;

import android.net.Uri;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.goldendance.client.R;
import com.goldendance.client.base.BaseActivity;

public class RegisterActivity extends BaseActivity implements RegisterFragment.OnFragmentInteractionListener {

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//    }

    @Override
    public void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_register);
        RegisterFragment fragment = (RegisterFragment) getSupportFragmentManager().findFragmentById(R.id.activity_register);
        if (fragment == null) {
            fragment = RegisterFragment.newInstance("", "");
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.add(R.id.activity_register, fragment);
            ft.commit();
        }
        new RegisterPresenter(fragment);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
