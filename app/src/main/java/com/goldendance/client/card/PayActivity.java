package com.goldendance.client.card;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.goldendance.client.R;
import com.goldendance.client.base.BaseActivity;
import com.goldendance.client.bean.CardBean;
import com.goldendance.client.bean.DataResultBean;
import com.goldendance.client.bean.User;
import com.goldendance.client.http.GDHttpManager;
import com.goldendance.client.http.GDImageLoader;
import com.goldendance.client.http.GDOnResponseHandler;
import com.goldendance.client.model.CardModel;
import com.goldendance.client.utils.GDLogUtils;
import com.goldendance.client.utils.JsonUtils;
import com.goldendance.client.zhifubao.OrderInfoUtil2_0;
import com.goldendance.client.zhifubao.PayResult;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Map;

public class PayActivity extends BaseActivity {

    private static final String TAG = PayActivity.class.getSimpleName();
    private TextView tvUserName;
    private TextView tvMobile;
    private RadioButton rbMouth;
    private RadioButton rbSeason;
    private View scrollView;
    private TextView tvPayNotice;

    @Override
    public void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_pay);
        if (TextUtils.isEmpty(User.tokenid)) {
            toLogin();
            return;
        }

        scrollView = findViewById(R.id.scrollView);
        scrollView.setVisibility(View.INVISIBLE);
        ImageView ivAvatar = (ImageView) findViewById(R.id.ivAvatar);
        GDLogUtils.i(TAG, User.icon);
        GDImageLoader.setImageView(this, User.icon, ivAvatar);
        findViewById(R.id.ivBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        findViewById(R.id.tvSubmit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doPay();
            }
        });

        tvUserName = (TextView) findViewById(R.id.tvUserName);
        tvMobile = (TextView) findViewById(R.id.tvMobile);
        tvUserName.setText(User.name);
        tvMobile.setText(User.tel);

        rbMouth = (RadioButton) findViewById(R.id.rbMouth);
        rbMouth.setChecked(true);
        rbMouth.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Object tag = buttonView.getTag();
                if (tag instanceof CardBean) {
                    CardBean card = (CardBean) tag;
                    if (isChecked) {
                        setPayNotice(card.getCardprice(), card.getPoint());
                    }
                }

            }
        });
        rbSeason = (RadioButton) findViewById(R.id.rbSeason);
        rbSeason.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Object tag = buttonView.getTag();
                if (tag instanceof CardBean) {
                    CardBean card = (CardBean) tag;
                    if (isChecked) {
                        setPayNotice(card.getCardprice(), card.getPoint());
                    }
                }

            }
        });
        tvPayNotice = (TextView) findViewById(R.id.tvPayNotice);
        setPayNotice("0.0", "0.0");
        getCardInfos();


    }

    private void doPay() {
        EditText etInviteUser = (EditText) findViewById(R.id.etInviteUser);
        String tel = etInviteUser.getText().toString();

        Object tag;
        if (rbMouth.isChecked()) {
            tag = rbMouth.getTag();
        } else {
            tag = rbSeason.getTag();
        }
        CardBean card;

        if (tag instanceof CardBean) {
            card = (CardBean) tag;
            final ProgressDialog pd = new ProgressDialog(this);
            pd.setMessage("加载中...");
            pd.show();
            new CardModel().buyCard(tel, card.getCardid(), new GDOnResponseHandler() {
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
                    DataResultBean da = JsonUtils.fromJson(json, new TypeToken<DataResultBean>() {
                    });
                    if (da == null) {
                        showToast("data parse error");
                        return;
                    }
                    int code1 = da.getCode();
                    if (GDHttpManager.CODE200 != code1) {
                        showToast(da.getCode() + ":" + da.getMessage());
                        return;
                    }
                    Toast.makeText(PayActivity.this, da.getMessage(), Toast.LENGTH_SHORT).show();
                    onBackPressed();
                }
            });
        }

    }

    /**
     * 支付宝支付业务：入参app_id
     */
    public static final String APPID = "2017012505421466";

    /**
     * 支付宝账户登录授权业务：入参pid值
     */
    public static final String PID = "";
    /**
     * 支付宝账户登录授权业务：入参target_id值
     */
    public static final String TARGET_ID = "";

    /** 商户私钥，pkcs8格式 */
    /** 如下私钥，RSA2_PRIVATE 或者 RSA_PRIVATE 只需要填入一个 */
    /** 如果商户两个都设置了，优先使用 RSA2_PRIVATE */
    /** RSA2_PRIVATE 可以保证商户交易在更加安全的环境下进行，建议使用 RSA2_PRIVATE */
    /** 获取 RSA2_PRIVATE，建议使用支付宝提供的公私钥生成工具生成， */
    /**
     * 工具地址：https://doc.open.alipay.com/docs/doc.htm?treeId=291&articleId=106097&docType=1
     */
    public static final String RSA2_PRIVATE = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCD6bO+Peon2OKu8zNs7fiBA/yiV3IQWHnzSUffdsHVZTeHod1uOuX5a3sur3UczgKAgi4FbPRyeU7lcb3BrJGkphCmg7EE8J63Ty2C5f2NUIB7LreE0qXnwOCffqx8OSlPNWpBPAUbkcqOWjsC63Jmn7jxO+EWvemlqrwWrO3AMoV5O6gNKGiHEBzl7jGbc85s6oNqp8c0GqXDqzsrWgTTiaVX1pJ28854JOHlHeu7KNXKZKEvGs9we+Zk7t6d3B/a51nV7py7NPZo1p7ywFKpQWkI6FCcZOVHUttrZZ8Mtlru2j9WoUZz3oBECiBw3Cny9h4m1auzLzY7QHqUHDa1AgMBAAECggEAel2+aQmSPuiIT3Y3DCMsVLHYFtg7bUJFzhL6YycM4+GCw4S5ndlxIBA+Mq58Wv4XgBJCkrHhiJdTQrBiMFjeufd6lpyvB0AWW0FNnJGwxPgaVyqc/s9RK0zWkd22L33u/hU0vdLpgguZ9Ldq6mhytRQRQWYkhS4ioZVh6T79RT2zGGVD8PenpHvDksGzMvkC/UXfc62kgtZbqWHLrmOLqccY7ewwTvCBRQGA8yd77dBPV2PeqntbSnxW7PFWZKRn6Uj7q+6aSVn/HiX5uMDKD/OVdv3Toleg7rmgYby/faL6cTWVCt4SI7BTX1loWKeyN09D+W7x78kt3DX+q5tZOQKBgQD6Ny/iwsTsVv5KyAgdte3OMxeO+Wu1ky5NZG7QFyC33hGx1uZusX5djM/UFYCJdfKYOA7vpEkYjov6OReiyGmuNU9Iyr+Mb5cvp2dm8W4lyBHfMCW97Ig5ibaNAILt0dvHndaWTj0lUUNx0xzybWIVFDNeUSdJrQUzyX95SFHDwwKBgQCG9mHblRNZK0NiIPnNTTABU/QZr492KJrCo/PTWX0YuIQkofSeg/mlnfcvDs1moJ87NNJLiPDmzcKKHB/Zmdvd4t4suyH3ee1T2Miia8emE2pRNy5HIAcadjDOAwFXwRJHB75qHGnnETq48YrOFGNJyf6bUICHRV/CbqNwxzjMJwKBgQCLNZUQxmglTakhFfVeAlASPt7GjwJb/UzvhgcZAyUS5xxVI0kFIP87MRKX3narA9I62kWFEpQQc45xYr5BtNsHL2WzDApSKeulea/P2Fb98jfvQqxc5HJOAM5t2HD/u5hZkPlImrLuRi4N7/TU9AJz089YlDIVjYutalfftFq5XQKBgDRm2ZwOh9Kpb6JcN2G1RJxfYnnOpp80KNLGIde/+Uht7DDlCTu69mWNrfoBf1MHrouoeLaHVzHmVlCVlvBAQxQmECnCBvA10SYrF8uomX4sL62VBUzsbJWjqlNy60SNXHFj2lUM7zmegPRqJIDbfama2lanrR/YmdO+bYtObwklAoGATwpqqOp2fxeFJIJUmLzZwQuKJ4iMh6WcP2rXT2L0zX0UgVbDGN0NXakT6E7M+1nlFKYrJ1Df0AShSUw+x0ImcVgH9WCKIic9QDE0q0FYZT9YPbn04w2RWcwy4iVld1w4pPWySw7831vPoqO1myo44cwGGc0VZxsPsY8Jci0Y2VQ=";
    public static final String RSA_PRIVATE = "";

    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_AUTH_FLAG = 2;
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        Toast.makeText(PayActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast.makeText(PayActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
            }
        }

        ;
    };

    //app_id=2014072300007148&biz_content={"button":[{"actionParam":"ZFB_HFCZ","actionType":"out","name":"话费充值"},{"name":"查询","subButton":[{"actionParam":"ZFB_YECX","actionType":"out","name":"余额查询"},{"actionParam":"ZFB_LLCX","actionType":"out","name":"流量查询"},{"actionParam":"ZFB_HFCX","actionType":"out","name":"话费查询"}]},{"actionParam":"http://m.alipay.com","actionType":"link","name":"最新优惠"}]}&charset=GBK&method=alipay.mobile.public.menu.add&sign_type=RSA2&timestamp=2014-07-24 03:07:50&version=1.0
    private void doPay2() {
        if (TextUtils.isEmpty(APPID) || (TextUtils.isEmpty(RSA2_PRIVATE) && TextUtils.isEmpty(RSA_PRIVATE))) {
            new AlertDialog.Builder(this).setTitle("警告").setMessage("需要配置APPID | RSA_PRIVATE")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialoginterface, int i) {
                            //
                            finish();
                        }
                    }).show();
            return;
        }

        /**
         * 这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
         * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
         * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险；
         *
         * orderInfo的获取必须来自服务端；
         */
        boolean rsa2 = (RSA2_PRIVATE.length() > 0);
        Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(APPID, rsa2);
        String orderParam = OrderInfoUtil2_0.buildOrderParam(params);

        String privateKey = rsa2 ? RSA2_PRIVATE : RSA_PRIVATE;
        String sign = OrderInfoUtil2_0.getSign(params, privateKey, rsa2);
        final String orderInfo = orderParam + "&" + sign;

        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(PayActivity.this);
                Map<String, String> result = alipay.payV2(orderInfo, true);
                GDLogUtils.i("msp", result.toString());

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    void setPayNotice(String money, String point) {
        tvPayNotice.setText(Html.fromHtml(String.valueOf(String.format(Locale.getDefault(), getString(R.string.pay_notice), money, point))));
    }

    private void getCardInfos() {
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("加载中...");
        pd.show();
        new CardModel().getCardList(new GDOnResponseHandler() {
            @Override
            public void onSuccess(int code, String json) {
                super.onSuccess(code, json);
                GDLogUtils.i(TAG, json);
                if (GDHttpManager.CODE200 != code) {
                    showToast("code:" + code);
                    return;
                }
                DataResultBean<ArrayList<CardBean>> data = JsonUtils.fromJson(json, new TypeToken<DataResultBean<ArrayList<CardBean>>>() {
                });
                if (data == null) {
                    showToast("data parse error");
                    return;
                }
                if (GDHttpManager.CODE200 != data.getCode()) {
                    showToast(data.getCode() + ":" + data.getMessage());
                    return;
                }
                ArrayList<CardBean> result = data.getData();
                setPayInfo(result);
            }

            @Override
            public void onEnd() {
                pd.dismiss();
                super.onEnd();
            }
        });
    }

    private void setPayInfo(ArrayList<CardBean> result) {
        if (result == null || result.size() < 1) {
            showToast("data result is empty");
            onBackPressed();
            return;
        }
        scrollView.setVisibility(View.VISIBLE);
        int size = result.size();
        for (int i = 0; i < size; i++) {
            CardBean card = result.get(i);
            String cardprice = card.getCardprice();
            switch (i) {
                case 0:
                    setPayNotice(card.getCardprice(), card.getPoint());
                    rbMouth.setText(card.getCardname() + ":" + cardprice + "元");
                    rbMouth.setTag(card);
                    break;
                case 1:
                    rbSeason.setText(card.getCardname() + ":" + cardprice + "元");
                    rbSeason.setTag(card);
                    break;
            }
        }
    }
}
