package com.goldendance.client.course;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.goldendance.client.R;
import com.goldendance.client.bean.DataResultBean;
import com.goldendance.client.bean.StoreBean;
import com.goldendance.client.http.GDHttpManager;
import com.goldendance.client.http.GDOnResponseHandler;
import com.goldendance.client.model.CourseModel;
import com.goldendance.client.utils.GDLogUtils;
import com.goldendance.client.utils.JsonUtils;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CourseFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CourseFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CourseFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = CourseFragment.class.getSimpleName();

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private int[] ivIdres;
    private View view;
    private ViewPager vpBody;
    private StoreListAdapter adapter;
    private RecyclerView storeList;
    private TextView tvTitle;
    private ArrayList<String> dateList;
    private List<Fragment> fragmentList;

    public CourseFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CourseFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CourseFragment newInstance(String param1, String param2) {
        CourseFragment fragment = new CourseFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void setTitle(StoreBean bean) {
        showOrHideStoreList();
        if (bean == null) {
            return;
        }
        if (tvTitle.getText().toString().equals(bean.getText())) {
            return;
        }
        tvTitle.setText(bean.getText());
        int currentItem = vpBody.getCurrentItem();
        CourseListFragment.storeId = bean.getValue();
        CourseListFragment courseListFragment = (CourseListFragment) fragmentList.get(currentItem);
        if (courseListFragment != null) {
            courseListFragment.onrefresh2();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_course, container, false);
        initView(view);

        getStore();
        return view;
    }

    /**
     * 获取门店
     */
    private void getStore() {
        final ProgressDialog show = ProgressDialog.show(getActivity(), null, "加载中...");
        new CourseModel().getStore(new GDOnResponseHandler() {
            @Override
            public void onEnd() {
                super.onEnd();
                show.dismiss();
            }

            @Override
            public void onSuccess(int code, String json) {
                super.onSuccess(code, json);
                if (GDHttpManager.CODE200 != code) {
                    Toast.makeText(getActivity(), "network error:" + code, Toast.LENGTH_SHORT).show();
                    return;
                }
                DataResultBean<ArrayList<StoreBean>> base = JsonUtils.fromJson(json, new TypeToken<DataResultBean<ArrayList<StoreBean>>>() {
                });
                if (base == null) {
                    Toast.makeText(getActivity(), "data parse error", Toast.LENGTH_SHORT).show();
                    return;
                }
                int code1 = base.getCode();
                if (GDHttpManager.CODE200 != code1) {
                    Toast.makeText(getActivity(), "error：" + code1, Toast.LENGTH_SHORT).show();
                    return;
                }

                ArrayList<StoreBean> data = base.getData();
                adapter.setmData(data);
                adapter.notifyDataSetChanged();
                if (data != null && data.size() > 0) {
                    StoreBean storeBean = data.get(0);
                    CourseListFragment.storeId = storeBean.getValue();
                    tvTitle.setText(storeBean.getText());
                    initBody();
                }
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }

    private void initView(final View view) {
//        RecyclerView rvList = (RecyclerView) view.findViewById(R.id.rvList);
//        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
//        manager.setOrientation(LinearLayoutManager.VERTICAL);
//        rvList.setLayoutManager(manager);
//        view.findViewById(R.id.ivBack).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                getActivity().onBackPressed();
//            }
//        });
//        CourseAdapter adapter = new CourseAdapter(getActivity());
//        rvList.setAdapter(adapter);
        initHead(view);
        initHead2(view);

        initStoreView(view);
    }

    private void initBody() {
        vpBody = (ViewPager) view.findViewById(R.id.vpBody);
        fragmentList = new ArrayList<>();
        for (String date : dateList) {
            fragmentList.add(CourseListFragment.newInstance(date, ""));
        }
        CoursePagerAdapter adapter = new CoursePagerAdapter(getActivity().getSupportFragmentManager(), fragmentList);
        vpBody.setAdapter(adapter);
        vpBody.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int pos) {
//                view.findViewById(ivIdres[pos]).setVisibility(View.VISIBLE);
                for (int i = 0; i < ivIdres.length; i++) {
                    if (i == pos) {
                        view.findViewById(ivIdres[pos]).setVisibility(View.VISIBLE);
                    } else {
                        view.findViewById(ivIdres[i]).setVisibility(View.INVISIBLE);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    private void initStoreView(View view) {
        storeList = (RecyclerView) view.findViewById(R.id.rvList);
        storeList.setVisibility(View.GONE);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        storeList.setLayoutManager(manager);

        adapter = new StoreListAdapter(getActivity());

        storeList.setAdapter(adapter);


    }


    private void initHead2(View view) {
        int[] head2WeekIds = new int[]{
                R.id.tvWeek1,
                R.id.tvWeek2,
                R.id.tvWeek3,
                R.id.tvWeek4,
                R.id.tvWeek5,
                R.id.tvWeek6,
                R.id.tvWeek7,
        };

        int[] head2Ids = new int[]{
                R.id.tvDay1,
                R.id.tvDay2,
                R.id.tvDay3,
                R.id.tvDay4,
                R.id.tvDay5,
                R.id.tvDay6,
                R.id.tvDay7
        };
        String[] weeks = new String[]{
                "周日",
                "周一",
                "周二",
                "周三",
                "周四",
                "周五",
                "周六"
        };

        //        头部点击事件
        int[] head2Ids2 = new int[]{
                R.id.llDay1,
                R.id.llDay2,
                R.id.llDay3,
                R.id.llDay4,
                R.id.llDay5,
                R.id.llDay6,
                R.id.llDay7
        };
        SimpleDateFormat myFmt = new SimpleDateFormat("yyyy-MM-dd");
        dateList = new ArrayList<>();
        //获取当前时间
        Calendar calendar = Calendar.getInstance();
        for (int i = 0; i < 7; i++) {
            Calendar c = Calendar.getInstance();
            //最近一周
            c.set(Calendar.DATE, calendar.get(Calendar.DATE) + i);

            //设置时间
            Date time = c.getTime();
            String format = myFmt.format(time);
            dateList.add(format);
            int monthOfYear = c.get(Calendar.MONTH) + 1;
            int dayOfMonth = c.get(Calendar.DAY_OF_MONTH);
            int week = c.get(Calendar.DAY_OF_WEEK);
//            GDLogUtils.i(TAG, "日期:" + monthOfYear + "月" + dayOfMonth + "日  星期：" + week);
//            设置星期
            TextView tvWeek = (TextView) view.findViewById(head2WeekIds[i]);
            tvWeek.setText(weeks[week - 1]);
//            月份
            TextView tvDay = (TextView) view.findViewById(head2Ids[i]);
            tvDay.setText(monthOfYear + "." + dayOfMonth);
//            按鈕
            view.findViewById(head2Ids2[i]).setOnClickListener(this);
        }
    }

    private void initHead(View view) {
        view.findViewById(R.id.ivBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        ivIdres = new int[]{
                R.id.ivPoint1,
                R.id.ivPoint2,
                R.id.ivPoint3,
                R.id.ivPoint4,
                R.id.ivPoint5,
                R.id.ivPoint6,
                R.id.ivPoint7
        };

        tvTitle = (TextView) view.findViewById(R.id.tvTitle);
        tvTitle.setOnClickListener(this);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.llDay1:
                vpBody.setCurrentItem(0);
                break;
            case R.id.llDay2:
                vpBody.setCurrentItem(1);
                break;
            case R.id.llDay3:
                vpBody.setCurrentItem(2);
                break;
            case R.id.llDay4:
                vpBody.setCurrentItem(3);
                break;
            case R.id.llDay5:
                vpBody.setCurrentItem(4);
                break;
            case R.id.llDay6:
                vpBody.setCurrentItem(5);
                break;
            case R.id.llDay7:
                vpBody.setCurrentItem(6);
                break;
            case R.id.tvTitle:
                showOrHideStoreList();
                break;
        }
    }

    private void showOrHideStoreList() {
        if (storeList.getVisibility() == View.VISIBLE) {
            storeList.setVisibility(View.GONE);
        } else {
            storeList.setVisibility(View.VISIBLE);
        }
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
