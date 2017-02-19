package com.goldendance.client.about;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.goldendance.client.R;
import com.goldendance.client.base.BaseActivity;

public class LicenseActivity extends BaseActivity {

    @Override
    public void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_license);

        findViewById(R.id.ivBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        TextView tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvTitle.setText("用户协议");
        LinearLayout llContainer = (LinearLayout) findViewById(R.id.llContainer);
        int[] licenseArrays = {
                R.array.license_array_item1,
                R.array.license_array_item2,
                R.array.license_array_item3,
                R.array.license_array_item4,
                R.array.license_array_item5,
                R.array.license_array_item6,
                R.array.license_array_item7,
                R.array.license_array_item8,
                R.array.license_array_item9,
                R.array.license_array_item10,
                R.array.license_array_item11
        };
        for (int i = 0; i < licenseArrays.length; i++) {
            String[] stringArray = getResources().getStringArray(licenseArrays[i]);
            for (int j = 0; j < stringArray.length; j++) {
                String content = stringArray[j];
                int itemLatoutRes = j == 0 ? R.layout.license_title : R.layout.license_content;
                View itemView = getLayoutInflater().inflate(itemLatoutRes, llContainer, false);
                TextView text = (TextView) itemView.findViewById(R.id.text);
                text.setText(content);
                llContainer.addView(itemView);
            }
        }

    }
}
