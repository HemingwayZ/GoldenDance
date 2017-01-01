package com.goldendance.client.login;

import android.net.Uri;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.goldendance.client.R;
import com.goldendance.client.base.BaseActivity;

public class LoginActivity extends AppCompatActivity implements LoginFragment.OnFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_login);
        setContentView(R.layout.activity_login);
        LoginFragment fragment = (LoginFragment) getSupportFragmentManager().findFragmentById(R.id.activity_login);
        if(fragment==null){
            fragment = LoginFragment.newInstance("","");
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.add(R.id.activity_login,fragment);
            ft.commit();
        }
    }

//    @Override
//    public void initIntent() {
//
//    }
//
//    @Override
//    public void initView(Bundle savedInstanceState) {
//        setContentView(R.layout.activity_login);
//        LoginFragment fragment = (LoginFragment) getSupportFragmentManager().findFragmentById(R.id.activity_login);
//        if(fragment==null){
//            fragment = LoginFragment.newInstance("","");
//            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//            ft.add(R.id.activity_login,fragment);
//            ft.commit();
//        }
//    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
