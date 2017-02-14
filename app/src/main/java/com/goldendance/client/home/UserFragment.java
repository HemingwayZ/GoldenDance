package com.goldendance.client.home;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.goldendance.client.R;
import com.goldendance.client.about.AboutActivity;
import com.goldendance.client.bean.User;
import com.goldendance.client.card.CardActivity;
import com.goldendance.client.course.history.CourseHistoryActivity;
import com.goldendance.client.http.GDImageLoader;
import com.goldendance.client.userinfo.UserInfoActivity;
import com.goldendance.client.utils.GDSharedPreference;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link UserFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link UserFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private ImageView ivGender;
    private TextView tvUserName;
    private ImageView ivAvatar;
    private TextView tvCardName;

    public UserFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UserFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UserFragment newInstance(String param1, String param2) {
        UserFragment fragment = new UserFragment();
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

        View view = inflater.inflate(R.layout.fragment_user, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {


        view.findViewById(R.id.llVip).setOnClickListener(this);
        view.findViewById(R.id.llUserInfo).setOnClickListener(this);
        view.findViewById(R.id.llLogOut).setOnClickListener(this);
        //清除缓存
        view.findViewById(R.id.llClearCache).setOnClickListener(this);

        //课程历史
        view.findViewById(R.id.llCourseHistory).setOnClickListener(this);
        //关于
        view.findViewById(R.id.llAbout).setOnClickListener(this);
        //用户信息
        ivGender = (ImageView) view.findViewById(R.id.ivGender);
        tvUserName = (TextView) view.findViewById(R.id.tvUserName);
        ivAvatar = (ImageView) view.findViewById(R.id.ivAvatar);
        //月卡或者会员
        tvCardName = (TextView) view.findViewById(R.id.tvCardName);
        tvCardName.setText("");
        setUserInfo();


    }

    public void setUserInfo() {
        if (User.tokenid == null) {
            //未登录
            return;
        }
        String gender = User.gender;
//        if("0".equals()gender)
        if (!TextUtils.isEmpty(User.name))
            tvUserName.setText(User.name);
        GDImageLoader.setImageView(getActivity(), User.icon, ivAvatar);
        tvCardName.setText(User.cardname);

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
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.llVip:
                intent.setClass(getActivity(), CardActivity.class);
                startActivity(intent);
                break;
            case R.id.llUserInfo:
                intent.setClass(getActivity(), UserInfoActivity.class);
                startActivity(intent);
                break;
            case R.id.llLogOut:
                logOut();
                break;
            case R.id.llClearCache:
                clearCache();
                break;
            case R.id.llAbout:
                intent.setClass(getActivity(), AboutActivity.class);
                startActivity(intent);
                break;
            case R.id.llCourseHistory:
                intent.setClass(getActivity(), CourseHistoryActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void clearCache() {
        AlertDialog.Builder ab = new AlertDialog.Builder(getActivity());
        ab.setMessage("是否要清除图片缓存？");
        ab.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                final ProgressDialog show = ProgressDialog.show(getActivity(), null, "清除图片缓存中...");
                show.show();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        GDImageLoader.clearCache(getActivity());
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                show.hide();
                                GDImageLoader.clearMemoryCache(getActivity());
                                Toast.makeText(getActivity(), "清除完毕", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                }).start();
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).show();
    }

    private void logOut() {
        AlertDialog.Builder ab = new AlertDialog.Builder(getActivity());
        ab.setMessage("是否要注销账号？");
        ab.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                GDSharedPreference.remove(getActivity(), GDSharedPreference.KEY_TOKEN);
                User.logOut();
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).show();

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
