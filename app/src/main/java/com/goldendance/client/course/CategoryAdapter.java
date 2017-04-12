package com.goldendance.client.course;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.goldendance.client.R;
import com.goldendance.client.bean.StoreBean;

import java.util.ArrayList;

/**
 * Created by hemingway on 2017/2/14.
 */

public class CategoryAdapter extends BaseAdapter {
    ArrayList<StoreBean> mData;
    Context mContext;
    LayoutInflater mInflater;
    private int selectedPos = 0;

    public int getSelectedPos() {
        return selectedPos;
    }

    public void setSelectedPos(int selectedPos) {
        this.selectedPos = selectedPos;
    }

    public void setmData(ArrayList<StoreBean> mData) {
        this.mData = mData;
    }

    public CategoryAdapter(Context mContext) {
        this.mContext = mContext;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mData == null ? 0 : mData.size();
    }

    @Override
    public StoreBean getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private static class StoreViewHolder {
        View itemView;
        private final TextView tvStore;

        public StoreViewHolder(View itemView) {
            this.itemView = itemView;

            tvStore = (TextView) itemView.findViewById(R.id.tvStore);
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        StoreViewHolder holder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.ada_category_item, parent, false);
            holder = new StoreViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (StoreViewHolder) convertView.getTag();
        }
        StoreBean storeBean = mData.get(position);
        holder.tvStore.setText(storeBean.getNickname());

        if (getSelectedPos() == position) {
            holder.tvStore.setTextColor(0xff007733);
        } else {
            holder.tvStore.setTextColor(0xff333333);
        }
        return convertView;
    }
}
