package com.goldendance.client;

import android.os.Bundle;
import android.view.View;

import com.goldendance.client.base.BaseActivity;

import com.goldendance.client.utils.LogUtils;

public class MainActivity extends BaseActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        findViewById(R.id.btnCrash).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LogUtils.i(TAG, "" + 1 / 0);
            }
        });

    }
}
