package com.goldendance.client.userinfo;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.goldendance.client.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link UpdateUserInfoFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link UpdateUserInfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UpdateUserInfoFragment extends Fragment implements IUpdateUserInfoContract.IView, View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_ACTION = "action";
    private static final String ARG_PARAM2 = "param2";
    private IUpdateUserInfoContract.IPresenter mPresenter;
    // TODO: Rename and change types of parameters
    private String action;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private EditText etUser;

    public UpdateUserInfoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UpdateUserInfoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UpdateUserInfoFragment newInstance(String param1, String param2) {
        UpdateUserInfoFragment fragment = new UpdateUserInfoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_ACTION, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            action = getArguments().getString(ARG_ACTION);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_info, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        view.findViewById(R.id.llCancel).setOnClickListener(this);
        view.findViewById(R.id.cancel).setOnClickListener(this);

        switch (action) {
            case UserInfoActivity.ACTION_USERNAME:
                initEditUserName(view);
                break;
            case UserInfoActivity.ACTION_PSW:
                initEditPSW(view);
                break;
            case UserInfoActivity.ACTION_GENDER:
                initChoiceGender(view);
                break;
            case UserInfoActivity.ACTION_ICON:
                initChoiceIcon(view);
                break;
        }

    }

    private void initChoiceIcon(View view) {
        initTitle(view, "选择头像");
        view.findViewById(R.id.llEdit).setVisibility(View.GONE);
    }

    private void initChoiceGender(View view) {
        initTitle(view, "选择性别");
        view.findViewById(R.id.llEdit).setVisibility(View.GONE);
    }

    private void initEditPSW(View view) {
        initTitle(view, "修改密码");
        view.findViewById(R.id.llOption1).setVisibility(View.GONE);
        view.findViewById(R.id.llOption2).setVisibility(View.GONE);
        etUser = (EditText) view.findViewById(R.id.etUser);
        etUser.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
    }

    /**
     * 修改用户名
     *
     * @param view
     */
    private void initEditUserName(View view) {
        initTitle(view, "修改昵称");
        view.findViewById(R.id.llOption1).setVisibility(View.GONE);
        view.findViewById(R.id.llOption2).setVisibility(View.GONE);
        etUser = (EditText) view.findViewById(R.id.etUser);
    }


    private void initTitle(View view, String title) {
        TextView tvTitle = (TextView) view.findViewById(R.id.tvTitle);
        tvTitle.setText(title);
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
    public void setPresenter(IUpdateUserInfoContract.IPresenter iPresenter) {
        this.mPresenter = iPresenter;
    }

    @Override
    public void showProgress() {
    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.llCancel:
            case R.id.cancel:
                getActivity().onBackPressed();
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
