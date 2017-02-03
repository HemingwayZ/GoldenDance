package com.goldendance.client.course;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.goldendance.client.R;
import com.goldendance.client.bean.UserBean;
import com.goldendance.client.http.GDImageLoader;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;

/**
 * Created by hemingway on 2017/2/3.
 */

public class AvatarAdapter extends RecyclerView.Adapter<AvatarAdapter.AvatarHolder> {

    Context mContext;
    LayoutInflater mInflater;
    ArrayList<UserBean> mData;

    public AvatarAdapter(Context mContext) {
        this.mContext = mContext;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public ArrayList<UserBean> getmData() {
        return mData;
    }

    public void setmData(ArrayList<UserBean> mData) {
        this.mData = mData;
    }

    @Override
    public AvatarHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.avatar_item, parent, false);
        return new AvatarHolder(view);
    }

    @Override
    public void onBindViewHolder(AvatarHolder holder, int position) {
        UserBean userBean = mData.get(position);
        if (userBean == null) {
            return;
        }
        String icon = userBean.getIcon();
        GDImageLoader.setCircleView(mContext, icon, holder.ivAvatar);
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    class AvatarHolder extends RecyclerView.ViewHolder {

        private final RoundedImageView ivAvatar;

        public AvatarHolder(View itemView) {
            super(itemView);
            ivAvatar = (RoundedImageView) itemView.findViewById(R.id.ivAvatar);
        }
    }
}
