package com.goldendance.client.card;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.goldendance.client.R;
import com.goldendance.client.bean.CardRecordBean;

import java.util.ArrayList;

/**
 * Created by hemingway on 2017/1/26.
 */

public class CardHistoryAdapter extends RecyclerView.Adapter<CardHistoryAdapter.CardHistoryViewHolder> {
    Context mContext;
    LayoutInflater mInflater;
    ArrayList<CardRecordBean> data;

    public ArrayList<CardRecordBean> getData() {
        return data;
    }

    public void setData(ArrayList<CardRecordBean> data) {
        this.data = data;
    }

    public CardHistoryAdapter(Context mContext) {
        this.mContext = mContext;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public CardHistoryViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = mInflater.inflate(R.layout.ada_card_his_item, viewGroup, false);

        return new CardHistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CardHistoryViewHolder cardHistoryViewHolder, int i) {
        CardRecordBean cardRecordBean = data.get(i);
        if (cardRecordBean == null) {
            return;
        }
        cardHistoryViewHolder.tvCardName.setText(cardRecordBean.getCardname());
        cardHistoryViewHolder.tvTime.setText(cardRecordBean.getPaytime());
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    class CardHistoryViewHolder extends RecyclerView.ViewHolder {

        private final TextView tvCardName;
        private final TextView tvTime;

        public CardHistoryViewHolder(View itemView) {
            super(itemView);
            tvCardName = (TextView) itemView.findViewById(R.id.tvCardName);
            tvTime = (TextView) itemView.findViewById(R.id.tvTime);
        }
    }
}
