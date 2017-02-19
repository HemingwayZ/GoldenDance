package com.goldendance.client.userinfo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.goldendance.client.R;
import com.goldendance.client.base.BaseActivity;
import com.goldendance.client.bean.DataResultBean;
import com.goldendance.client.bean.User;
import com.goldendance.client.http.GDHttpManager;
import com.goldendance.client.http.GDOnResponseHandler;
import com.goldendance.client.login.LoginActivity;
import com.goldendance.client.model.UserModel;
import com.goldendance.client.others.MyTextWatcher;
import com.goldendance.client.register.RegisterActivity;
import com.goldendance.client.register.RegisterFragment;
import com.goldendance.client.utils.GDSharedPreference;
import com.goldendance.client.utils.GDTextUtils;
import com.goldendance.client.utils.JsonUtils;
import com.google.gson.reflect.TypeToken;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class UpdatePasswordActivity extends BaseActivity implements View.OnClickListener {


    private EditText etNew2;
    private EditText etNew1;
    private EditText etOld;
    private String localPsw;

    @Override
    public void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_update_password);

        //获取密码
        localPsw = (String) GDSharedPreference.get(this, GDSharedPreference.KEY_PASSWORD, "");
        if (TextUtils.isEmpty(localPsw)) {
            Toast.makeText(this, "账号登录异常，请重新登录", Toast.LENGTH_LONG).show();
            Intent intent = new Intent();
            intent.setClass(this, LoginActivity.class);
            startActivity(intent);
            return;
        }

        initHead();

        //旧密码
        View oldPsw = findViewById(R.id.oldPsw);
        ImageView ivLeft = (ImageView) oldPsw.findViewById(R.id.ivEtLeft);
        ivLeft.setImageResource(R.mipmap.i_psw);
        etOld = (EditText) oldPsw.findViewById(R.id.etditetx);
        etOld.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        etOld.setHint("请输入原密码");
        etOld.addTextChangedListener(new MyTextWatcher(etOld, oldPsw.findViewById(R.id.ivClear)));

        //新密码1


        View newPsw = findViewById(R.id.newPsw1);
        ImageView ivLeft1 = (ImageView) newPsw.findViewById(R.id.ivEtLeft);
        ivLeft1.setImageResource(R.mipmap.i_psw);
        etNew1 = (EditText) newPsw.findViewById(R.id.etditetx);
        etNew1.setHint("请输入新密码");
        etNew1.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        etNew1.addTextChangedListener(new MyTextWatcher(etNew1, newPsw.findViewById(R.id.ivClear)));

//        新密码2
        View newPsw2 = findViewById(R.id.newPsw2);
        ImageView ivLeft2 = (ImageView) newPsw2.findViewById(R.id.ivEtLeft);
        ivLeft2.setImageResource(R.mipmap.i_psw);
        etNew2 = (EditText) newPsw2.findViewById(R.id.etditetx);
        etNew2.setHint("请再次输入新密码");
        etNew2.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        etNew2.addTextChangedListener(new MyTextWatcher(etNew2, newPsw2.findViewById(R.id.ivClear)));

        findViewById(R.id.tvSubmit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doSubmit();
            }
        });

        findViewById(R.id.tvForgetPsw).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(UpdatePasswordActivity.this, RegisterActivity.class);
                intent1.putExtra("action", RegisterFragment.ACTION_RESET_PSW);
                startActivity(intent1);
            }
        });
    }

    private void doSubmit() {
        String oldPswStr = etOld.getText().toString();
        if (TextUtils.isEmpty(oldPswStr) || oldPswStr.length() < 6) {
            Toast.makeText(this, "请输入不小于六位数的原密码", Toast.LENGTH_LONG).show();
            return;
        }
        String newPswStr1 = etNew1.getText().toString();
        if (TextUtils.isEmpty(newPswStr1) || newPswStr1.length() < 6) {
            Toast.makeText(this, "请输入不小于6位数的新密码", Toast.LENGTH_LONG).show();
            return;
        }
        String newPswStr2 = etNew2.getText().toString();
        if (TextUtils.isEmpty(newPswStr2) || newPswStr2.length() < 6) {
            Toast.makeText(this, "请重复输入不小于6位数的新密码", Toast.LENGTH_LONG).show();
            return;
        }
        oldPswStr = GDTextUtils.getMD5(oldPswStr);
        if (!oldPswStr.equals(localPsw)) {
            Toast.makeText(this, "原密码错误，请检查后重新输入", Toast.LENGTH_LONG).show();
            return;
        }
        if (!newPswStr2.equals(newPswStr1)) {
            Toast.makeText(this, "两次新密码输入不同，请检查后重新输入", Toast.LENGTH_LONG).show();
            return;
        }
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("修改中...");
        progressDialog.show();
        final String md5Psw = GDTextUtils.getMD5(newPswStr2);
        new UserModel().updatePsw(User.userid, md5Psw, new GDOnResponseHandler() {

            @Override
            public void onEnd() {
                progressDialog.dismiss();
                super.onEnd();
            }

            @Override
            public void onSuccess(int code, String json) {
                super.onSuccess(code, json);
                DataResultBean data = processJson(code, json);
                if (data == null) {
                    return;
                }
                showMsg(data.getMessage());
                Map<String, Object> map = new HashMap<>();
                map.put(GDSharedPreference.KEY_PASSWORD, md5Psw);
                GDSharedPreference.storeValue(UpdatePasswordActivity.this, map);
                onBackPressed();
            }
        });
    }

    private DataResultBean processJson(int code, String json) {
        if (GDHttpManager.CODE200 != code) {
            showMsg("code:" + code);
            return null;
        }
        DataResultBean data = JsonUtils.fromJson(json, new TypeToken<DataResultBean>() {
        });
        if (data == null) {
            showMsg("data parse error");
            return null;
        }
        if (GDHttpManager.CODE200 != data.getCode()) {
            showMsg("error:" + data.getMessage());
            return null;
        }
        return data;
    }

    private void showMsg(String s) {
        Toast.makeText(this, s, Toast.LENGTH_LONG).show();
    }

    private void initHead() {
        findViewById(R.id.ivBack).setOnClickListener(this);
        TextView tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvTitle.setText("修改密码");
        findViewById(R.id.ivMore).setVisibility(View.GONE);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                onBackPressed();
                break;
        }
    }
}
