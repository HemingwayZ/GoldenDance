package com.goldendance.client.login;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.goldendance.client.R;
import com.goldendance.client.bean.User;
import com.goldendance.client.home.HomeActivity;
import com.goldendance.client.others.MyTextWatcher;
import com.goldendance.client.register.RegisterActivity;
import com.goldendance.client.utils.GDLogUtils;
import com.goldendance.client.utils.GDSharedPreference;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LoginFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment implements View.OnClickListener, ILoginContract.IView {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = LoginFragment.class.getSimpleName();
    private ILoginContract.IPresenter mPresenter;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private EditText etMobile;
    private EditText etPassword;
    private AlertDialog pbLoading;

    public LoginFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LoginFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
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
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        view.findViewById(R.id.tvRegister).setOnClickListener(this);

        view.findViewById(R.id.ivBack).setOnClickListener(this);

        view.findViewById(R.id.tvForgetPsw).setOnClickListener(this);

        etMobile = (EditText) view.findViewById(R.id.etMobile);
        etPassword = (EditText) view.findViewById(R.id.etPassword);
        etMobile.addTextChangedListener(new MyTextWatcher(etMobile, view.findViewById(R.id.ivClearMobile)));
        etPassword.addTextChangedListener(new MyTextWatcher(etMobile, view.findViewById(R.id.ivClearPassword)));
        view.findViewById(R.id.tvSubmit).setOnClickListener(this);

        //
        pbLoading = new ProgressDialog(getActivity());
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
            case R.id.tvRegister:
                Intent intent = new Intent(getActivity(), RegisterActivity.class);
                startActivity(intent);
                break;
            case R.id.ivBack:
                getActivity().onBackPressed();
                break;
            case R.id.tvSubmit:
                mPresenter.getToken();
                break;
        }
    }

    @Override
    public void setPresenter(ILoginContract.IPresenter iPresenter) {
        this.mPresenter = iPresenter;
    }

    @Override
    public void showProgress() {
        pbLoading.show();
    }

    @Override
    public void hideProgress() {
        pbLoading.cancel();
    }

    @Override
    public String getMobile() {
        return etMobile.getText().toString();
    }

    @Override
    public String getPassword() {
        return etPassword.getText().toString();
    }

    @Override
    public void showToast(@StringRes int idStr, String msg) {
        Toast.makeText(getActivity(), String.format(Locale.getDefault(), getString(idStr), msg), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void loginSuccess(String tokenid, String finalPsw) {
        GDLogUtils.i(TAG, "tokens:" + tokenid);
        Map<String, Object> map = new HashMap<>();
        map.put(GDSharedPreference.KEY_TOKEN, tokenid);
        map.put(GDSharedPreference.KEY_PASSWORD, finalPsw);
        User.tokenid = tokenid;
        boolean b = GDSharedPreference.storeValue(getActivity(), map);
        if (!b) {
            Toast.makeText(getActivity(), "save token failed", Toast.LENGTH_SHORT).show();
            return;
        }
//        getActivity().setResult(Activity.RESULT_OK);
//        getActivity().onBackPressed();
        Intent intent = new Intent(getActivity(), HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("action", "loginSuccess");
        startActivity(intent);
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

    @Override
    public void onDestroyView() {
        if (pbLoading != null) {
            pbLoading.dismiss();
        }
        super.onDestroyView();
    }
}
