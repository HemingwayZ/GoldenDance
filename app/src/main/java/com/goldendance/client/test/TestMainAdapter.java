package com.goldendance.client.test;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.goldendance.client.R;

import java.util.ArrayList;

/**
 * Created by Hemingway1014@gmail.com on 2016/12/13.
 */

public class TestMainAdapter extends RecyclerView.Adapter<TestMainAdapter.TestViewHolder> {
    ArrayList<String> data;
    Context mContext;
    private final LayoutInflater inflater;

    public TestMainAdapter(ArrayList<String> data, Context mContext) {
        this.data = data;
        this.mContext = mContext;
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public TestViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = inflater.inflate(R.layout.ada_test_main_item, parent, false);

        return new TestViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(TestViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    static class TestViewHolder extends RecyclerView.ViewHolder {

        public TestViewHolder(View itemView) {
            super(itemView);
        }
    }
}
