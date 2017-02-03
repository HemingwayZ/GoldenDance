package com.goldendance.client.course;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.goldendance.client.R;
import com.goldendance.client.bean.StoreBean;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

/**
 * Created by hemingway on 2017/2/2.
 */

public class StoreListAdapter extends RecyclerView.Adapter<StoreListAdapter.StoreViewHolder> {
    Context mContext;
    ArrayList<StoreBean> mData;
    LayoutInflater inflater;


    public ArrayList<StoreBean> getmData() {
        return mData;
    }

    public void setmData(ArrayList<StoreBean> mData) {
        this.mData = mData;
    }

    public StoreListAdapter(Context mContext) {
        this.mContext = mContext;
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public StoreViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.store_list_item, viewGroup, false);
        return new StoreViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StoreViewHolder storeViewHolder, int i) {
        StoreBean item = mData.get(i);
        storeViewHolder.tvStore.setText(item.getText());
        storeViewHolder.itemView.setTag(R.id.pos, i);
        storeViewHolder.itemView.setOnClickListener(onItemClickListener);
    }

    View.OnClickListener onItemClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Object tag = v.getTag(R.id.pos);
//            Toast.makeText(mContext, "pos:" + tag, Toast.LENGTH_LONG).show();
            if (tag instanceof Integer) {
                int pos = (int) tag;
                StoreBean storeBean = mData.get(pos);
                EventBus.getDefault().post(storeBean);
            }
        }
    };

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    class StoreViewHolder extends RecyclerView.ViewHolder {

        private final TextView tvStore;

        public StoreViewHolder(View itemView) {
            super(itemView);
            tvStore = (TextView) itemView.findViewById(R.id.tvStore);
        }
    }
}
