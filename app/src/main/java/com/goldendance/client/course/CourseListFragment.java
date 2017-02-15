package com.goldendance.client.course;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.goldendance.client.R;
import com.goldendance.client.bean.CourseListBean;
import com.goldendance.client.bean.DataResultBean;
import com.goldendance.client.bean.StoreBean;
import com.goldendance.client.http.GDHttpManager;
import com.goldendance.client.http.GDOnResponseHandler;
import com.goldendance.client.model.CourseModel;
import com.goldendance.client.utils.JsonUtils;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CourseListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CourseListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CourseListFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_DATE = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static String storeId = "";
    public static String courseType = "1";
    // TODO: Rename and change types of parameters
    private String date;
    private String type;

    private OnFragmentInteractionListener mListener;
    private View empty_view;
    private TextView tvEmpty;
    private CourseAdapter adapter;
    private SwipeRefreshLayout refreshView;
    private LinearLayoutManager manager;

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    public CourseListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CourseListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CourseListFragment newInstance(String param1, String param2) {
        CourseListFragment fragment = new CourseListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_DATE, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            date = getArguments().getString(ARG_DATE);
            type = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_course_list, container, false);
        initView(view);
        onrefresh();
        return view;
    }

    public void onrefresh() {
        empty_view.setVisibility(View.GONE);
        adapter.setLoadText("加载中...");
        hasMoreData = true;
        page = 1;
        initData();
    }

    public void onrefresh2() {
        empty_view.setVisibility(View.GONE);
        adapter.setmList(null);
        adapter.notifyDataSetChanged();
        onrefresh();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventBusRefresh(StoreBean storeBean) {
        empty_view.setVisibility(View.GONE);
        adapter.setmList(null);
        adapter.notifyDataSetChanged();
        onrefresh();
    }

    private static int ROWS = 20;
    private int page = 1;

    private void initData() {
        refreshView.setRefreshing(true);
        new CourseModel().getListCourse(courseType, date, storeId, page, ROWS, new GDOnResponseHandler() {
            @Override
            public void onEnd() {
                super.onEnd();
                refreshView.setRefreshing(false);
            }

            @Override
            public void onFailed(IOException e) {
                super.onFailed(e);
                if (adapter.getItemCount() < 1) {
                    showEmptyView("网络请求超时");
                }
            }

            @Override
            public void onSuccess(int code, String json) {
                super.onSuccess(code, json);

                if (GDHttpManager.CODE200 != code) {
                    showEmptyView("newwork error " + code);
                    return;
                }
                DataResultBean<CourseListBean> base = JsonUtils.fromJson(json, new TypeToken<DataResultBean<CourseListBean>>() {
                });
                if (base == null) {
                    showEmptyView("data parse error");
                    return;
                }
                if (GDHttpManager.CODE200 != base.getCode()) {
                    showEmptyView(base.getMessage());
                    return;
                }
                CourseListBean data = base.getData();
                if (data == null) {
                    showEmptyView("没有更多课程了");
                    return;
                }

                if (data.getList() == null || data.getList().size() < ROWS) {
                    hasMoreData = false;
                    adapter.setHasNoData(true);
                    adapter.setLoadText("没有更多课程了");
//                    Toast.makeText(getActivity(), "没有更多课程了", Toast.LENGTH_SHORT).show();
                }
                page++;
                adapter.addMoreList(data.getList());
                adapter.notifyDataSetChanged();
                if (adapter.getItemCount() < 2) {
                    adapter.setLoadText("");
                    showEmptyView("暂无课程信息");
                }
            }
        });
    }

    void showEmptyView(String msg) {
        empty_view.setVisibility(View.VISIBLE);
        tvEmpty.setText(msg);
    }

    private boolean hasMoreData = true;

    private void initView(View view) {
        RecyclerView rvList = (RecyclerView) view.findViewById(R.id.rvList);
        manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rvList.setLayoutManager(manager);
        adapter = new CourseAdapter(getActivity());

        rvList.setAdapter(adapter);
        rvList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (RecyclerView.SCROLL_STATE_IDLE == newState && hasMoreData) {
                    if (manager.findLastVisibleItemPosition() >= adapter.getItemCount() - 1) {
                        initData();
                    }
                }
            }
        });

        empty_view = view.findViewById(R.id.empty_view);
        empty_view.setVisibility(View.GONE);
        tvEmpty = (TextView) empty_view.findViewById(R.id.tvEmpty);
        tvEmpty.setText("暂无课程");


        //刷新
        refreshView = (SwipeRefreshLayout) view.findViewById(R.id.refreshView);
        //设置刷新时动画的颜色，可以设置4个
        refreshView.setProgressBackgroundColorSchemeResource(android.R.color.white);
        refreshView.setColorSchemeResources(android.R.color.holo_blue_light,
                android.R.color.holo_red_light, android.R.color.holo_orange_light,
                android.R.color.holo_green_light);
        refreshView.setProgressViewOffset(false, 0, (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources()
                        .getDisplayMetrics()));
        refreshView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                adapter.setmList(null);
                adapter.notifyDataSetChanged();
                onrefresh();
            }
        });
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
