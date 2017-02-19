package com.goldendance.client.about;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.goldendance.client.R;
import com.goldendance.client.base.BaseActivity;

public class AboutActivity extends BaseActivity implements View.OnClickListener {

    @Override
    public void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_about);

        findViewById(R.id.ivBack).setOnClickListener(this);

        TextView tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvTitle.setText("关于金舞团");


        //版本号
        View i_version = findViewById(R.id.i_version);
        TextView tvSetting = (TextView) i_version.findViewById(R.id.tvSetting);
        tvSetting.setText("版本号");
        TextView tvSettingMore = (TextView) i_version.findViewById(R.id.tvSettingMore);
        try {
            PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_CONFIGURATIONS);
            String versionName = packageInfo.versionName;
            tvSettingMore.setText(versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            tvSettingMore.setText("获取版本异常");
        }

        //用户协议
        View i_protocol = findViewById(R.id.i_protocol);
        TextView tvSetting2 = (TextView) i_protocol.findViewById(R.id.tvSetting);
        tvSetting2.setText("用户协议");
        ((TextView) i_protocol.findViewById(R.id.tvSettingMore)).setText("");
        i_protocol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AboutActivity.this, LicenseActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivBack:
                onBackPressed();
                break;
        }
    }
}
