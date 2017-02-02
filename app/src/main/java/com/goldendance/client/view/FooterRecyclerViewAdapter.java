package com.goldendance.client.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.goldendance.client.R;

import java.util.ArrayList;

/**
 * Created by Hemingway on 2016/10/8.
 */

public abstract class FooterRecyclerViewAdapter<T> extends RecyclerView.Adapter<BaseViewHolder> {
    protected Context mContext;
    protected ArrayList<T> mList;
    protected static final int TYPE_FOOT_VIEW = 0;
    protected static final int TYPE_CONTENT = 1;
    private boolean hasNoData = false;
    private boolean loadFailed = false;
    private String loadText = "";

    class FootViewHolder extends BaseViewHolder {
        private final View pbLoad;
        private final TextView tvLoad;

        public FootViewHolder(View itemView) {
            super(itemView);
            pbLoad = itemView.findViewById(R.id.pbLoad);
            tvLoad = (TextView) itemView.findViewById(R.id.tvLoad);
        }
    }

    public String getLoadText() {
        return loadText;
    }

    public void setLoadText(String loadText) {
        this.loadText = loadText;
    }

    public boolean isLoadFailed() {
        return loadFailed;
    }

    public void setLoadFailed(boolean loadFailed) {
        this.loadFailed = loadFailed;
    }

    public boolean isHasNoData() {
        return hasNoData;
    }

    public void setHasNoData(boolean hasNoData) {
        this.hasNoData = hasNoData;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        FootViewHolder footViewHolder = (FootViewHolder) holder;
        if (hasNoData == true) {
            footViewHolder.pbLoad.setVisibility(View.GONE);
        } else {
            if (loadFailed) {
                footViewHolder.pbLoad.setVisibility(View.GONE);
            } else {
                footViewHolder.pbLoad.setVisibility(View.VISIBLE);
            }
        }
        footViewHolder.tvLoad.setText(loadText);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_FOOT_VIEW) {
            View view = inflater.inflate(R.layout.foot_view, parent, false);
            return new FootViewHolder(view);
        }
        return null;
    }

    protected LayoutInflater inflater;

    public FooterRecyclerViewAdapter(Context mContext) {
        this.mContext = mContext;
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public ArrayList<T> getmList() {
        return mList;
    }

    public void setmList(ArrayList<T> mList) {
        this.mList = mList;
    }

    public void addMoreList(ArrayList<T> listMore) {

        if (listMore == null) {
            return;
        }
        if (mList == null || mList.size() < 1) {
            mList = listMore;
        } else {
            mList.addAll(listMore);
        }
    }

    public void addListItem(int pos, T t) {
        if (mList == null) {
            mList = new ArrayList<>();
        }
        mList.add(pos, t);
    }

    public void addListItem(T t) {
        if (mList == null) {
            mList = new ArrayList<>();
        }
        mList.add(t);
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            return TYPE_FOOT_VIEW;
        }
        return TYPE_CONTENT;
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size() + 1;
    }


}
