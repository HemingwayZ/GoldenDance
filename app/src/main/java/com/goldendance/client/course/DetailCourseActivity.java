package com.goldendance.client.course;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.goldendance.client.R;
import com.goldendance.client.base.BaseActivity;
import com.goldendance.client.bean.CoachBean;
import com.goldendance.client.bean.CourseBean;
import com.goldendance.client.bean.DataResultBean;
import com.goldendance.client.bean.MessageBean;
import com.goldendance.client.bean.Store2Bean;
import com.goldendance.client.bean.User;
import com.goldendance.client.bean.UserBean;
import com.goldendance.client.http.GDHttpManager;
import com.goldendance.client.http.GDImageLoader;
import com.goldendance.client.http.GDOnResponseHandler;
import com.goldendance.client.model.CourseModel;
import com.goldendance.client.utils.JsonUtils;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class DetailCourseActivity extends BaseActivity {

    private CourseBean courseBean;
    private ImageView ivCoachPic;
    private AvatarAdapter avatarAdapter;
    private ImageView ivAddCourse;

    @Override
    public void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_course_detail);
        courseBean = (CourseBean) getIntent().getSerializableExtra("course");
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

        ivAddCourse = (ImageView) findViewById(R.id.ivAddCourse);
        ivAddCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("1".equals(courseBean.getIsordered())) {
                    doUnOrder();
                } else {
                    doOrder();
                }
                ;
            }
        });

        ImageView ivCourseCover = (ImageView) findViewById(R.id.ivCourseCover);
        GDImageLoader.setImageView(this, courseBean.getPicture(), ivCourseCover);

        TextView tvCourseTitle = (TextView) findViewById(R.id.tvCourseTitle);
        tvCourseTitle.setText(courseBean.getCoursename());

        TextView tvCoachName = (TextView) findViewById(R.id.tvCoachName);
        tvCoachName.setText(String.format(getString(R.string.coach), courseBean.getCoach().getName()));

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

        setCoach();

        setStore();

        setOrderMembers();

        initData();
    }

    private void doUnOrder() {
        new CourseModel().unOrderCourse(courseBean.getCourseid(), new GDOnResponseHandler() {
            @Override
            public void onSuccess(int code, String json) {
                super.onSuccess(code, json);
                if (GDHttpManager.CODE200 != code) {
                    Toast.makeText(DetailCourseActivity.this, "网络请求异常 code" + code, Toast.LENGTH_LONG).show();
                    return;
                }
                MessageBean msg = JsonUtils.fromJson(json, new TypeToken<MessageBean>() {
                });
                if (msg == null) {
                    Toast.makeText(DetailCourseActivity.this, "data parse error", Toast.LENGTH_LONG).show();
                    return;
                }
                Toast.makeText(DetailCourseActivity.this, msg.getMessage(), Toast.LENGTH_LONG).show();
                if (GDHttpManager.CODE200 == msg.getCode()) {
                    courseBean.setIsordered("0");
                    setCourseStatus();
                    getOrderedMember();
                }
            }
        });
    }

    private void initData() {
        if (TextUtils.isEmpty(User.tokenid)) {
            return;
        }
        String courseid = courseBean.getCourseid();
        new CourseModel().findCourseMes(courseid, User.tel, new GDOnResponseHandler() {
            @Override
            public void onSuccess(int code, String json) {
                super.onSuccess(code, json);
                if (GDHttpManager.CODE200 != code) {

                    return;
                }
                DataResultBean<CourseBean> data = JsonUtils.fromJson(json, new TypeToken<DataResultBean<CourseBean>>() {
                });
                if (data == null) {
                    Toast.makeText(DetailCourseActivity.this, "data parse error", Toast.LENGTH_LONG).show();
                    return;
                }
                if (GDHttpManager.CODE200 != data.getCode()) {
                    Toast.makeText(DetailCourseActivity.this, "error" + data.getMessage(), Toast.LENGTH_LONG).show();
                    return;
                }
                CourseBean data1 = data.getData();
                if (data1 != null) {
                    courseBean = data.getData();
//                    Toast.makeText(DetailCourseActivity.this, "getIsordered:" + courseBean.getIsordered(), Toast.LENGTH_LONG).show();
                    setCourseStatus();
                }
            }
        });
    }

    private void setCourseStatus() {
        if ("1".equals(courseBean.getIsordered())) {
            ivAddCourse.setImageResource(R.mipmap.detail_book_add_succcess);
        } else {
            ivAddCourse.setImageResource(R.mipmap.detail_book_add);
        }
    }

    private void doOrder() {
        final ProgressDialog show = ProgressDialog.show(this, null, "预约中...");
        show.show();
        new CourseModel().orderCourse(courseBean.getCourseid(), new GDOnResponseHandler() {
            @Override
            public void onEnd() {
                super.onEnd();
                show.dismiss();
            }

            @Override
            public void onSuccess(int code, String json) {
                super.onSuccess(code, json);
                if (GDHttpManager.CODE200 != code) {
                    Toast.makeText(DetailCourseActivity.this, "网络异常" + code, Toast.LENGTH_LONG).show();
                    return;
                }
                MessageBean messageBean = JsonUtils.fromJson(json, new TypeToken<MessageBean>() {
                });
                if (messageBean == null) {
                    Toast.makeText(DetailCourseActivity.this, "data parse error" + code, Toast.LENGTH_LONG).show();
                    return;
                }
                if (GDHttpManager.CODE200 != messageBean.getCode()) {
                    Toast.makeText(DetailCourseActivity.this, messageBean.getMessage(), Toast.LENGTH_LONG).show();
                    return;
                }
                getOrderedMember();
                courseBean.setIsordered("1");
                setCourseStatus();
                Toast.makeText(DetailCourseActivity.this, messageBean.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setOrderMembers() {
        RecyclerView rvList = (RecyclerView) findViewById(R.id.rvList);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvList.setLayoutManager(manager);

        avatarAdapter = new AvatarAdapter(this);
        rvList.setAdapter(avatarAdapter);

        getOrderedMember();
    }

    private void getOrderedMember() {
        final ProgressDialog show = ProgressDialog.show(this, null, "加载中...");
        show.show();
        new CourseModel().getOrderedMember(courseBean.getCourseid(), new GDOnResponseHandler() {

            @Override
            public void onEnd() {
                super.onEnd();
                show.dismiss();
            }

            @Override
            public void onSuccess(int code, String json) {
                super.onSuccess(code, json);
                if (GDHttpManager.CODE200 != code) {
                    Toast.makeText(DetailCourseActivity.this, "网络异常" + code, Toast.LENGTH_LONG).show();
                    return;
                }
                DataResultBean<ArrayList<UserBean>> messageBean = JsonUtils.fromJson(json, new TypeToken<DataResultBean<ArrayList<UserBean>>>() {
                });
                if (messageBean == null) {
                    Toast.makeText(DetailCourseActivity.this, "member data parse error" + code, Toast.LENGTH_LONG).show();
                    return;
                }
                if (GDHttpManager.CODE200 != messageBean.getCode()) {
                    Toast.makeText(DetailCourseActivity.this, messageBean.getMessage(), Toast.LENGTH_LONG).show();
                    return;
                }
                avatarAdapter.setmData(messageBean.getData());
                avatarAdapter.notifyDataSetChanged();
            }
        });
    }

    private void setStore() {
        Store2Bean store = courseBean.getStore();

        TextView tvStoreName = (TextView) findViewById(R.id.tvStoreName);
        if (store == null) {
            tvStoreName.setText(getString(R.string.network_error));
            return;
        }
        ImageView ivStorePic = (ImageView) findViewById(R.id.ivStorePic);
        GDImageLoader.setImageView(this, store.getPicture(), ivStorePic);

        tvStoreName.setText(store.getNickname());

        TextView tvStoreDesc = (TextView) findViewById(R.id.tvStoreDesc);
        tvStoreDesc.setText(store.getAddress());
    }

    private void setCoach() {
        CoachBean coach = courseBean.getCoach();
        if (coach == null) {
            return;
        }
        ImageView ivCoachPic = (ImageView) findViewById(R.id.ivCoachPic);
        GDImageLoader.setImageView(this, coach.getIcon(), ivCoachPic);

        TextView tvCoachName2 = (TextView) findViewById(R.id.tvCoachName2);
        tvCoachName2.setText(coach.getName());

        TextView tvCoachDesc = (TextView) findViewById(R.id.tvCoachDesc);
        tvCoachDesc.setText(coach.getSignature());
    }
}
