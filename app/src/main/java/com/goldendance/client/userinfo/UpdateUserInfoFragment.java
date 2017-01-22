package com.goldendance.client.userinfo;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.text.InputType;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.goldendance.client.R;
import com.goldendance.client.bean.User;
import com.goldendance.client.utils.GDFileUtils;
import com.goldendance.client.utils.GDLogUtils;

import java.io.File;

import static android.app.Activity.RESULT_OK;

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
    private static final String TAG = UpdateUserInfoFragment.class.getSimpleName();
    private IUpdateUserInfoContract.IPresenter mPresenter;
    // TODO: Rename and change types of parameters
    private String action;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private EditText etUser;
    private AlertDialog pdLoading;
    private TextView tvChoice1;
    private TextView tvChoice2;


    private static final String ICON_FILE_NAME = "icon.jpg";
    private Uri cropUri;

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
        //进度条
        pdLoading = new AlertDialog.Builder(getActivity()).create();
        pdLoading.setMessage("修改中...");
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
        view.findViewById(R.id.llEdit).setVisibility(View.GONE);

        view.findViewById(R.id.llOption1).setOnClickListener(this);
        view.findViewById(R.id.llOption2).setOnClickListener(this);

        view.findViewById(R.id.llCancel).setOnClickListener(this);

        tvChoice1 = (TextView) view.findViewById(R.id.tvChoice1);
        tvChoice2 = (TextView) view.findViewById(R.id.tvChoice2);
        tvChoice1.setText("相册");
        tvChoice2.setText("拍照");
    }

    private void initChoiceGender(View view) {
        initTitle(view, "选择性别");
        view.findViewById(R.id.llEdit).setVisibility(View.GONE);

        view.findViewById(R.id.llOption1).setOnClickListener(this);
        view.findViewById(R.id.llOption2).setOnClickListener(this);

        tvChoice1 = (TextView) view.findViewById(R.id.tvChoice1);
        tvChoice2 = (TextView) view.findViewById(R.id.tvChoice2);
        tvChoice1.setText("男");
        tvChoice2.setText("女");
        setGender();
    }

    private void setGender() {
        String gender = User.gender;
//        0 男 1 女
        switch (gender) {
            case "0":
                tvChoice1.setTextColor(getResources().getColor(R.color.colorAccent));
                tvChoice2.setTextColor(0xff333333);
                break;
            case "1":
                tvChoice1.setTextColor(0xff333333);
                tvChoice2.setTextColor(getResources().getColor(R.color.colorAccent));
                break;
            default:
                tvChoice1.setTextColor(0xff333333);
                tvChoice2.setTextColor(0xff333333);
        }
    }

    private void initEditPSW(View view) {
        initTitle(view, "修改密码");
        view.findViewById(R.id.llOption1).setVisibility(View.GONE);
        view.findViewById(R.id.llOption2).setVisibility(View.GONE);
        etUser = (EditText) view.findViewById(R.id.etUser);
        //一起用才有效
        etUser.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
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

        TextView tvCancel = (TextView) view.findViewById(R.id.tvCancel);
        tvCancel.setText(getString(R.string.confirm));

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
        pdLoading.show();
    }

    @Override
    public void hideProgress() {
        pdLoading.dismiss();
    }

    private String gender = "-1";

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.llCancel:
                if (UserInfoActivity.ACTION_USERNAME.equals(action)) {
                    //修改用户名
                    mPresenter.confirm();
                } else if (UserInfoActivity.ACTION_ICON.equals(action)) {
                    getActivity().onBackPressed();
                }
                break;
            case R.id.cancel:
                getActivity().onBackPressed();
                break;
            case R.id.llOption1:
                if (UserInfoActivity.ACTION_GENDER.equals(action)) {
                    gender = "0";
                    mPresenter.confirm();
                } else if (UserInfoActivity.ACTION_ICON.equals(action)) {

                    getIconByGallery();
                }
                break;
            case R.id.llOption2:
                if (UserInfoActivity.ACTION_GENDER.equals(action)) {
                    gender = "1";
                    mPresenter.confirm();
                } else if (UserInfoActivity.ACTION_ICON.equals(action)) {
                    getIconByCamera();
                }
                break;
        }
    }

    private static final int REQUEST_GALLERY = 10000;
    private static final int REQUEST_CAMERA = 10001;
    private static final int REQUEST_CROP = 10002;

    private void getIconByGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_GALLERY);
    }

    private void getIconByCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    @Override
    public String getAction() {
        return action;
    }

    @Override
    public String getEditText() {
        return etUser.getText().toString();
    }

    @Override
    public void showMsg(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void updateSuccess(String key, String value) {
        Intent intent = new Intent();
        intent.putExtra("action", action);
        intent.putExtra(key, value);
        getActivity().setResult(RESULT_OK, intent);
        getActivity().onBackPressed();
    }

    @Override
    public String getGender() {
        return gender;
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK || data == null) {
            return;
        }
        switch (requestCode) {
            case REQUEST_GALLERY:
                Uri uri = data.getData();
                if (uri == null) {
                    Toast.makeText(getActivity(), "can find image", Toast.LENGTH_SHORT).show();
                    return;
                }
                String imagePath = GDFileUtils.getImageAbsolutePath(getActivity(), uri);
                GDLogUtils.i(TAG, "imagePath:" + imagePath);
//                updateSuccess(UserInfoActivity.ACTION_ICON, imagePath);
                toCrop(uri);
//                data.putExtra("action", action);
//                getActivity().setResult(RESULT_OK, data);
//                getActivity().onBackPressed();
                break;
            case REQUEST_CROP:
//                Uri cropUri = data.getData();
//                GDLogUtils.i(TAG, cropUri.getPath());
//                File storgeFile = GDFileUtils.getStorgeFile(ICON_FILE_NAME);
//                if (storgeFile == null || !storgeFile.exists()) {
//                    Toast.makeText(getActivity(), "image is not exist!!!", Toast.LENGTH_SHORT).show();
//                    return;
//                }
                if (cropUri == null) {
                    return;
                }
                updateSuccess(UserInfoActivity.ACTION_ICON, cropUri.getPath());
                break;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void toCrop(Uri uri) {


        File file = new File(GDFileUtils.getImageAbsolutePath(getActivity(), uri));
        if (!file.exists()) {
            Toast.makeText(getActivity(), "file is not exist!!!", Toast.LENGTH_LONG).show();
            return;
        }
        File storgeFile = GDFileUtils.getStorgeFile(ICON_FILE_NAME);
        if (storgeFile == null) {
            Toast.makeText(getActivity(), "file create failed!!!", Toast.LENGTH_LONG).show();
            return;
        }
        //本地图片存储 android N适配
        //Uri cropUri = FileProvider.getUriForFile(getActivity(), GDFileUtils.PROVIDER_AUTHORITY, storgeFile);
        cropUri = Uri.fromFile(storgeFile);
        GDLogUtils.i(TAG, cropUri.getPath());
        //
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 100);
        intent.putExtra("outputY", 100);
        intent.putExtra("scale", true);
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, cropUri);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", false);
        startActivityForResult(intent, REQUEST_CROP);
    }
}
