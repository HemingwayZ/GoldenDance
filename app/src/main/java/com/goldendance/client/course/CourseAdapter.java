package com.goldendance.client.course;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.goldendance.client.R;

/**
 * Created by hemingway on 2017/1/15.
 */

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder> {
    private Context mContext;
    private LayoutInflater mInflater;

    public CourseAdapter(Context mContext) {
        this.mContext = mContext;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public CourseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = mInflater.inflate(R.layout.item_course, parent, false);
        return new CourseViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(CourseViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 12;
    }

    class CourseViewHolder extends RecyclerView.ViewHolder {
        public CourseViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, DetailCourseActivity.class);
                    mContext.startActivity(intent);
                }
            });
        }
    }
}
