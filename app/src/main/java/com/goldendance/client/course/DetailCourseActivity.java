package com.goldendance.client.course;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.goldendance.client.R;
import com.goldendance.client.base.BaseActivity;
import com.goldendance.client.bean.CourseBean;
import com.goldendance.client.bean.User;
import com.goldendance.client.http.GDImageLoader;

public class DetailCourseActivity extends BaseActivity {

    @Override
    public void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_course_detail);
        CourseBean courseBean = (CourseBean) getIntent().getSerializableExtra("course");
        if (courseBean == null) {
            Toast.makeText(this, "params is error", Toast.LENGTH_SHORT).show();
            return;
        }
        findViewById(R.id.ivBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        View ivAddCourse = findViewById(R.id.ivAddCourse);
        ivAddCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(DetailCourseActivity.this, "功能暂未实现,敬请期待", Toast.LENGTH_SHORT).show();
            }
        });

        ImageView ivCourseCover = (ImageView) findViewById(R.id.ivCourseCover);
        GDImageLoader.setImageView(this, courseBean.getPicture(), ivCourseCover);

        TextView tvCourseTitle = (TextView) findViewById(R.id.tvCourseTitle);
        tvCourseTitle.setText(courseBean.getName());

        TextView tvCoachName = (TextView) findViewById(R.id.tvCoachName);
        tvCoachName.setText(String.format(getString(R.string.coach), courseBean.getCoachname()));

        TextView tvCoursePrice = (TextView) findViewById(R.id.tvCoursePrice);
        tvCoursePrice.setText(String.format(getString(R.string.course_price), courseBean.getPrice()));

        TextView tvTime = (TextView) findViewById(R.id.tvTime);
        tvTime.setText(courseBean.getStarttime());

        TextView tvCourseNum = (TextView) findViewById(R.id.tvCourseNum);
        tvCourseNum.setText(String.format(getString(R.string.order_number), courseBean.getOrdernum(), courseBean.getTotalnum()));

        TextView tvDesc = (TextView) findViewById(R.id.tvDesc);
        tvDesc.setText(courseBean.getIntroduce());

        TextView tvTime2 = (TextView) findViewById(R.id.tvTime2);
        tvTime2.setText(courseBean.getStarttime());
        View flNotice = findViewById(R.id.flNotice);
        if (TextUtils.isEmpty(User.tokenid)) {
            ivAddCourse.setVisibility(View.GONE);
            flNotice.setVisibility(View.GONE);
        }
    }
}
