package com.goldendance.client.course.history;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.TextView;

import com.goldendance.client.R;
import com.goldendance.client.base.BaseActivity;

public class CourseHistoryActivity extends BaseActivity implements CourseHistoryListFragment.OnFragmentInteractionListener {

    @Override
    public void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_course_history);
        findViewById(R.id.ivBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        ((TextView) findViewById(R.id.tvTitle)).setText("课程预约历史");

        CourseHistoryListFragment fragment = (CourseHistoryListFragment) getSupportFragmentManager().findFragmentById(R.id.flContent);
        if (fragment == null) {
            fragment = CourseHistoryListFragment.newInstance("", "");
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.add(R.id.flContent, fragment);
            fragmentTransaction.commitAllowingStateLoss();
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
