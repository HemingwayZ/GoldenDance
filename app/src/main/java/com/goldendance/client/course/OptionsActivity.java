package com.goldendance.client.course;

import android.animation.Animator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.goldendance.client.R;
import com.goldendance.client.base.BaseActivity;

public class OptionsActivity extends BaseActivity implements View.OnClickListener {

    @Override
    public void initView(Bundle savedInstanceState) {
        overridePendingTransition(android.R.anim.fade_in, 0);
        setContentView(R.layout.activity_options);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.bottom_up);
        findViewById(R.id.flBottom).setAnimation(animation);
        animation.start();

        findViewById(R.id.tvCancel).setOnClickListener(this);
        findViewById(R.id.cancel).setOnClickListener(this);


        findViewById(R.id.llOption1).setOnClickListener(this);
        View llOption2 = findViewById(R.id.llOption2);
        llOption2.setVisibility(View.GONE);
        llOption2.setOnClickListener(this);

        TextView tvChoice1 = (TextView) findViewById(R.id.tvChoice1);
        TextView tvChoice2 = (TextView) findViewById(R.id.tvChoice2);

        tvChoice1.setText("选择门店");
    }

    @Override
    public void onBackPressed() {
        View flBottom = findViewById(R.id.flBottom);
        flBottom.animate().translationY(flBottom.getHeight()).setDuration(500).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                finish();
                overridePendingTransition(0, android.R.anim.fade_out);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        }).start();

    }

    public static final int RESULT_OPTION1 = 1001;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cancel:
            case R.id.tvCancel:
                onBackPressed();
                break;
            case R.id.llOption1:
                setResult(RESULT_OPTION1);
                onBackPressed();
                break;
        }
    }
}
