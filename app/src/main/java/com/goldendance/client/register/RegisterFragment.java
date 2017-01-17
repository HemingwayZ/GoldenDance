package com.goldendance.client.register;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.goldendance.client.R;
import com.goldendance.client.home.HomeActivity;
import com.goldendance.client.others.MyTextWatcher;
import com.goldendance.client.utils.GDLogUtils;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;

import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import cz.msebera.android.httpclient.Header;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RegisterFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RegisterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegisterFragment extends Fragment implements IRegisterContract.IView, View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_ACTION = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static final String ACTION_REGISTER = "doRegister";
    public static final String ACTION_RESET_PEW = "reset_psw";
    private static final String TAG = RegisterFragment.class.getSimpleName();
    private IRegisterContract.IPresenter mPresenter;
    // TODO: Rename and change types of parameters
    private String mAction;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private ProgressDialog pdLoading;
    private EditText etMobile;
    private TextView tvMobileCode;
    private Timer timer;
    private EditText etMobileCode;
    private EditText etPassword;
    private View ivClearMobile;
    private View ivClearPassword;
    private View ivClearMobileCode;

    public RegisterFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param action Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RegisterFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RegisterFragment newInstance(String action, String param2) {
        RegisterFragment fragment = new RegisterFragment();
        Bundle args = new Bundle();
        args.putString(ARG_ACTION, action);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mAction = getArguments().getString(ARG_ACTION);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        initView(view);
        return view;
    }

//    private class MyTextWatcher implements TextWatcher {
//        View view;
//
//        public MyTextWatcher(View view) {
//            this.view = view;
//        }
//
//        @Override
//        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//        }
//
//        @Override
//        public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//        }
//
//        @Override
//        public void afterTextChanged(Editable s) {
//            int length = s.length();
//            if (length > 0) {
//                switch (view.getId()) {
//                    case R.id.etMobile:
//                        ivClearMobile.setVisibility(View.VISIBLE);
//                        break;
//                    case R.id.etMobileCode:
//                        ivClearMobileCode.setVisibility(View.VISIBLE);
//                        break;
//                    case R.id.etPassword:
//                        ivClearPassword.setVisibility(View.VISIBLE);
//                        break;
//                }
//            } else {
//                switch (view.getId()) {
//                    case R.id.etMobile:
//                        ivClearMobile.setVisibility(View.GONE);
//                        break;
//                    case R.id.etMobileCode:
//                        ivClearMobileCode.setVisibility(View.GONE);
//                        break;
//                    case R.id.etPassword:
//                        ivClearPassword.setVisibility(View.GONE);
//                        break;
//                }
//            }
//        }
//    }

    private void initView(View view) {
        view.findViewById(R.id.tvSubmit).setOnClickListener(this);

        //
        ivClearMobile = view.findViewById(R.id.ivClearMobile);
        ivClearPassword = view.findViewById(R.id.ivClearPassword);
        ivClearMobileCode = view.findViewById(R.id.ivClearMobileCode);
//        ivClearMobile.setOnClickListener(this);
//        ivClearPassword.setOnClickListener(this);
//        ivClearMobileCode.setOnClickListener(this);

        ivClearMobileCode.setVisibility(View.GONE);
        ivClearPassword.setVisibility(View.GONE);
        ivClearMobile.setVisibility(View.GONE);
        //
        etMobileCode = (EditText) view.findViewById(R.id.etMobileCode);
        etPassword = (EditText) view.findViewById(R.id.etPassword);
        etMobile = (EditText) view.findViewById(R.id.etMobile);
        etMobile.addTextChangedListener(new MyTextWatcher(etMobile, ivClearMobile));
        etMobileCode.addTextChangedListener(new MyTextWatcher(etMobileCode, ivClearMobileCode));
        etPassword.addTextChangedListener(new MyTextWatcher(etPassword, ivClearPassword));
        pdLoading = new ProgressDialog(getActivity());
        pdLoading.setMessage(getString(R.string.is_loading));

        tvMobileCode = (TextView) view.findViewById(R.id.tvMobileCode);
        tvMobileCode.setOnClickListener(this);


        view.findViewById(R.id.ivBack).setOnClickListener(this);
        view.findViewById(R.id.tvRegister).setOnClickListener(this);


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
    public void setPresenter(IRegisterContract.IPresenter iPresenter) {
        this.mPresenter = iPresenter;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvSubmit:
                mPresenter.doRegister();
                break;
            case R.id.tvMobileCode:
                mPresenter.getCode();
                break;
            case R.id.ivClearMobile:
                etMobile.setText("");
                break;
            case R.id.ivClearMobileCode:
                etMobileCode.setText("");
                break;
            case R.id.ivClearPassword:
                etPassword.setText("");
                break;
            case R.id.tvRegister:
            case R.id.ivBack:
                getActivity().onBackPressed();
                break;
        }
    }

    private void testRegister() {
        AsyncHttpClient client = new AsyncHttpClient();
        client.get("http://120.77.206.145:8080/JinWuTuan/sendsms", new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                GDLogUtils.i(TAG, "responseString:" + responseString);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                GDLogUtils.i(TAG, "responseString:" + responseString);
            }
        });
    }

    @Override
    public void showProgress() {
        pdLoading.show();
    }

    @Override
    public void onDestroyView() {
        if (pdLoading != null) {
            pdLoading.dismiss();
        }
        super.onDestroyView();
    }

    @Override
    public void hideProgress() {
        pdLoading.hide();
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

    private int timeCount = 60;

    private boolean isCounting = false;

    @Override
    public void startCount() {
        if (isCounting) {
            return;
        }
        if (timer == null) {
            timer = new Timer();
        }
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (timeCount-- > 0) {
                            isCounting = true;
                            tvMobileCode.setText(String.format(Locale.getDefault(), getString(R.string.time_count), timeCount));
                        } else {
                            timeCount = 60;
                            isCounting = false;
                            tvMobileCode.setText(getString(R.string.get_mobile_code));
                            timer.cancel();
                            timer = null;
                        }
                    }
                });
            }
        };


        timer.schedule(task, 0, 1000);
    }

    @Override
    public boolean isCounting() {
        return isCounting;
    }

    @Override
    public String getMobileCode() {
        return etMobileCode.getText().toString();
    }

    @Override
    public void registSucceed() {
        Intent intent = new Intent(getActivity(), HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    public void onDestroy() {
        if (timer != null) {
            timer.cancel();
        }
        super.onDestroy();
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
