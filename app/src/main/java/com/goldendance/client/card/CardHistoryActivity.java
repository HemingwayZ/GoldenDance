package com.goldendance.client.card;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.goldendance.client.R;
import com.goldendance.client.base.BaseActivity;
import com.goldendance.client.bean.CardRecordBean;
import com.goldendance.client.bean.DataResultBean;
import com.goldendance.client.http.GDHttpManager;
import com.goldendance.client.http.GDOnResponseHandler;
import com.goldendance.client.model.CardModel;
import com.goldendance.client.utils.JsonUtils;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class CardHistoryActivity extends BaseActivity {

    private CardHistoryAdapter adapter;
    private View emptyView;
    private TextView tvEmpty;

    @Override
    public void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_card_history);
        initHead();

        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        RecyclerView rvList = (RecyclerView) findViewById(R.id.rvList);
        rvList.setLayoutManager(manager);
        adapter = new CardHistoryAdapter(this);
        rvList.setAdapter(adapter);

        emptyView = findViewById(R.id.empty_view);
        emptyView.setVisibility(View.GONE);
        tvEmpty = (TextView) emptyView.findViewById(R.id.tvEmpty);
        tvEmpty.setText("未发现购买记录");
        initData();
    }

    private void initData() {
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("加载中...");
        pd.show();
        new CardModel().getCardRecord(new GDOnResponseHandler() {
            @Override
            public void onEnd() {
                pd.dismiss();
                super.onEnd();
            }

            @Override
            public void onSuccess(int code, String json) {
                super.onSuccess(code, json);
                if (GDHttpManager.CODE200 != code) {
                    showToast("code:" + code);
                    return;
                }

                DataResultBean<ArrayList<CardRecordBean>> result = JsonUtils.fromJson(json, new TypeToken<DataResultBean<ArrayList<CardRecordBean>>>() {
                });
                if (result == null) {
                    showToast("data parse error");
                    return;
                }
                if (GDHttpManager.CODE200 != result.getCode()) {
                    showToast(result.getMessage());
                    return;
                }
                ArrayList<CardRecordBean> data = result.getData();
                if (data == null || data.size() < 1) {
                    emptyView.setVisibility(View.VISIBLE);
                    return;
                }
                adapter.setData(result.getData());
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void initHead() {
        findViewById(R.id.ivBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        TextView tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvTitle.setText("购卡历史列表");
    }
}
