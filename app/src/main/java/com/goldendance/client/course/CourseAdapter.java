package com.goldendance.client.course;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.goldendance.client.R;
import com.goldendance.client.bean.CourseBean;
import com.goldendance.client.http.GDImageLoader;
import com.goldendance.client.view.BaseViewHolder;
import com.goldendance.client.view.FooterRecyclerViewAdapter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by hemingway on 2017/1/15.
 */

public class CourseAdapter extends FooterRecyclerViewAdapter<CourseBean> {


    public CourseAdapter(Context mContext) {
        super(mContext);
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_CONTENT) {
            View inflate = inflater.inflate(R.layout.item_course, parent, false);
            return new CourseViewHolder(inflate);
        }
        return super.onCreateViewHolder(parent, viewType);
    }

    DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.getDefault());

    @Override
    public void onBindViewHolder(BaseViewHolder baseHolder, int position) {
        if (getItemViewType(position) == TYPE_CONTENT) {

            CourseViewHolder holder = (CourseViewHolder) baseHolder;
            holder.itemView.setTag(R.id.pos, position);
            CourseBean item = mList.get(position);
            if (item == null) {
                return;
            }
            String picture = item.getPicture();
            GDImageLoader.setImageView(mContext, picture, holder.ivCourseCover);
            holder.tvCourseTitle.setText(item.getCoursename());

            holder.tvCoachName.setText(item.getCoach().getName());
            holder.tvCoursePrice.setText(String.format(Locale.getDefault(), mContext.getString(R.string.course_price), item.getPrice()));

            Date date = null;
            try {
                date = format.parse(item.getStarttime());
                long startTime = date.getTime();
                Date endDate = format.parse(item.getEndtime());
                long endTime = endDate.getTime();
                long currentTimeMillis = System.currentTimeMillis();
                if (currentTimeMillis < startTime) {
                    holder.tvCourseStatus.setText("未开始");
                    holder.tvCourseStatus.setTextColor(mContext.getResources().getColor(R.color.colorAccent));
                } else if (currentTimeMillis > startTime) {
                    holder.tvCourseStatus.setText("已结束");
                    holder.tvCourseStatus.setTextColor(0xffcdcdcd);
                } else {
                    holder.tvCourseStatus.setText("进行中...");
                    holder.tvCourseStatus.setTextColor(mContext.getResources().getColor(R.color.colorAccent));
                }
            } catch (ParseException e) {
                e.printStackTrace();

            }
            holder.tvTime.setText(item.getStarttime());
            //数量设置
            holder.tvCourseNum.setText(String.format(Locale.getDefault(), mContext.getString(R.string.order_number), item.getOrdernum(), item.getTotalnum()));
        } else if (getItemViewType(position) == TYPE_FOOT_VIEW) {
            super.onBindViewHolder(baseHolder, position);
        }
    }

    private class CourseViewHolder extends BaseViewHolder {

        private final ImageView ivCourseCover;
        private final TextView tvCourseTitle;
        private final TextView tvCoachName;
        private final TextView tvCoursePrice;
        private final TextView tvTime;
        private final TextView tvCourseNum;
        private final TextView tvCourseStatus;

        public CourseViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Object tag = v.getTag(R.id.pos);
                    if (tag instanceof Integer) {
                        int pos = (int) tag;
                        CourseBean courseBean = mList.get(pos);
                        Intent intent = new Intent(mContext, DetailCourseActivity.class);
                        intent.putExtra("course", courseBean);
                        mContext.startActivity(intent);
                    }

                }
            });

            ivCourseCover = (ImageView) itemView.findViewById(R.id.ivCourseCover);

            tvCourseTitle = (TextView) itemView.findViewById(R.id.tvCourseTitle);

            tvCoachName = (TextView) itemView.findViewById(R.id.tvCoachName);

            tvCoursePrice = (TextView) itemView.findViewById(R.id.tvCoursePrice);

            tvTime = (TextView) itemView.findViewById(R.id.tvTime);

            tvCourseNum = (TextView) itemView.findViewById(R.id.tvCourseNum);

            tvCourseStatus = (TextView) itemView.findViewById(R.id.tvCourseStatus);
        }
    }
}
