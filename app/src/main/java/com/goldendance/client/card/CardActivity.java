package com.goldendance.client.card;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.goldendance.client.R;
import com.goldendance.client.base.BaseActivity;
import com.goldendance.client.bean.User;

public class CardActivity extends BaseActivity {


    @Override
    public void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_card);
        if (TextUtils.isEmpty(User.tokenid)) {
            toLogin();
            onBackPressed();
            return;
        }
        findViewById(R.id.ivBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        TextView tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvTitle.setText("我的会员卡");
        findViewById(R.id.tvSubmit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CardActivity.this, PayActivity.class);
                startActivity(intent);
            }
        });

        TextView tvVIP = (TextView) findViewById(R.id.tvVIP);
        tvVIP.setText("VIP");
        TextView tvCardType = (TextView) findViewById(R.id.tvCardType);
        tvCardType.setText(User.cardname);


        findViewById(R.id.tvCardHistory).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toHistory();
            }
        });

        int cardnum = User.getCardnum();

        TextView tvCardNum = (TextView) findViewById(R.id.tvCardNum);
        tvCardNum.setText("持卡数:" + String.valueOf(cardnum));

        TextView tvCardOverTime = (TextView) findViewById(R.id.tvCardOverTime);
        tvCardOverTime.setText(User.cardovertime);
        if (cardnum == 0) {
            tvCardOverTime.setVisibility(View.GONE);
        }
    }

    private void toHistory() {
        Intent intent = new Intent(this, CardHistoryActivity.class);
        startActivity(intent);
    }
}
