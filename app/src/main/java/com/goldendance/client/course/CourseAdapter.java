package com.goldendance.client.course;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.goldendance.client.R;
import com.goldendance.client.bean.CourseBean;
import com.goldendance.client.http.GDImageLoader;
import com.goldendance.client.view.BaseViewHolder;
import com.goldendance.client.view.FooterRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by hemingway on 2017/1/15.
 */

public class CourseAdapter extends FooterRecyclerViewAdapter<CourseAdapter.CourseViewHolder> {
    private ArrayList<CourseBean> mData;

    public ArrayList<CourseBean> getmData() {
        return mData;
    }

    public void setmData(ArrayList<CourseBean> mData) {
        this.mData = mData;
    }

    public void addData(ArrayList<CourseBean> mData) {
        if (mData == null) {
            return;
        }
        if (this.mData == null) {
            this.mData = mData;
            return;
        }
        this.mData.addAll(mData);
    }

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

    @Override
    public void onBindViewHolder(BaseViewHolder baseHolder, int position) {
        if (getItemViewType(position) == TYPE_CONTENT) {
            CourseViewHolder holder = (CourseViewHolder) baseHolder;
            CourseBean item = mData.get(position);
            if (item == null) {
                return;
            }
            String picture = item.getPicture();
            GDImageLoader.setImageView(mContext, picture, holder.ivCourseCover);
            holder.tvCourseTitle.setText(item.getName());

            holder.tvCoachName.setText(item.getCoachname());
            holder.tvCoursePrice.setText(String.format(Locale.getDefault(), mContext.getString(R.string.course_price), item.getPrice()));
            holder.tvTime.setText(item.getStarttime());
            //数量设置
            holder.tvCourseNum.setText(String.format(Locale.getDefault(), mContext.getString(R.string.order_number), item.getOrdernum(), item.getTotalnum()));
        } else if (getItemViewType(position) == TYPE_FOOT_VIEW) {
            super.onBindViewHolder(baseHolder, position);
        }
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    class CourseViewHolder extends BaseViewHolder {

        private final ImageView ivCourseCover;
        private final TextView tvCourseTitle;
        private final TextView tvCoachName;
        private final TextView tvCoursePrice;
        private final TextView tvTime;
        private final TextView tvCourseNum;

        public CourseViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, DetailCourseActivity.class);
                    mContext.startActivity(intent);
                }
            });

            ivCourseCover = (ImageView) itemView.findViewById(R.id.ivCourseCover);

            tvCourseTitle = (TextView) itemView.findViewById(R.id.tvCourseTitle);

            tvCoachName = (TextView) itemView.findViewById(R.id.tvCoachName);

            tvCoursePrice = (TextView) itemView.findViewById(R.id.tvCoursePrice);

            tvTime = (TextView) itemView.findViewById(R.id.tvTime);

            tvCourseNum = (TextView) itemView.findViewById(R.id.tvCourseNum);
        }
    }
}
