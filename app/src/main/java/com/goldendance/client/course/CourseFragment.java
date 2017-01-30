package com.goldendance.client.course;

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

import com.goldendance.client.R;
import com.goldendance.client.utils.GDLogUtils;

import java.util.ArrayList;
import java.util.Calendar;
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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_course, container, false);
        initView(view);
        return view;
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
        vpBody = (ViewPager) view.findViewById(R.id.vpBody);
        List<Fragment> list = new ArrayList<>();
        CourseListFragment fragment1 = CourseListFragment.newInstance("", "");
        CourseListFragment fragment2 = CourseListFragment.newInstance("", "");
        CourseListFragment fragment3 = CourseListFragment.newInstance("", "");
        CourseListFragment fragment4 = CourseListFragment.newInstance("", "");
        CourseListFragment fragment5 = CourseListFragment.newInstance("", "");
        CourseListFragment fragment6 = CourseListFragment.newInstance("", "");
        CourseListFragment fragment7 = CourseListFragment.newInstance("", "");
        list.add(fragment1);
        list.add(fragment2);
        list.add(fragment3);
        list.add(fragment4);
        list.add(fragment5);
        list.add(fragment6);
        list.add(fragment7);
        CoursePagerAdapter adapter = new CoursePagerAdapter(getActivity().getSupportFragmentManager(), list);
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
        //获取当前时间
        Calendar calendar = Calendar.getInstance();
        for (int i = 0; i < 7; i++) {
            Calendar c = Calendar.getInstance();
            //最近一周
            c.set(Calendar.DATE, calendar.get(Calendar.DATE) + i);
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
