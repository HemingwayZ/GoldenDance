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

import com.alipay.sdk.app.EnvUtils;
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
                doPay2();
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
        final CardBean card;

        if (tag instanceof CardBean) {
            card = (CardBean) tag;
            final ProgressDialog pd = new ProgressDialog(this);
            pd.setMessage("付款中...");
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
                    User.cardname = card.getCardname();
                    onBackPressed();
                }
            });
        }

    }

    /**
     * 支付宝支付业务：入参app_id
     */
//    public static final String APPID = "2017012505421466";
//    public static final String APPID = "2016072800113706";
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
    //ssf
//    public static final String RSA2_PRIVATE = "";
    /**
     * me
     */
//    public static final String RSA2_PRIVATE = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQDqUD9mf70/RrUAO4MQ/etoMX9mHYH4nvcDixjr8BHBYvQ98PtuF/rDJHpNm/hkXQe29A72iXRq+VRrjf/pMqOVnuxn8aOy4KzZS6IMcISDX0Odn42V8g3WO46duyfM2/+1qBZa/aJry+FGQwUiFrciavu2YhQDUP3czaWlyuAOMwmc4KTFf2IP9Ih3vmMd7dqGsbZyZt9p8bKUNHsCaX2y/IJ7EwIrzkymIEtorEHFnDpVlvrlEvowILM8vCUcolJPJ/GMUsvGXp2E5NGfz+CXO9TXzN7ZWQy9Gma/35prpUCQ4GzzdjlTscEZtratCbZsNjwfht2XLY40BMyECiS/AgMBAAECggEAE5I0WXld80USpTxTTD4JRaCsxXi9fGi6tR8PK9XXhdGgGPRh6+Yw2c/YL1dRvgBPPqbQASOmB7FfrcQklfPW2g52LJBwXeaKSXQZyvQy/U4dtbK3/shDA5vh46bYQjI1/5RUmbEJuftDi7zZVumSzlslPyyeA1Dmg2Th6yEw6b6MpCbWc6gQ4eQ0Fm6oHyg9Hwk0VZ4I/Vwys8VLmKvpC58e8ufERcXW2AEmkvFnga0EwvFzT5BsHHoUPrXTe26ZAF1G2XOmQVJqpUTJgQi8byJM1V5nAbvf/lqYISS2nk4oUOTsTAG0NEdKAiJUJ32G0iQCLp8+dk/bc/NLnC9YYQKBgQD967BMDak2UxXkJ3/1E2TjXOsI5SMSm65r1eOOS2y4HV4pVurYJgPs8f9Pa7CCCaM068+dccg8ftDIg+EOmQFhG6+YQO/LSVffzeXPWkrcMs+benDvuPC0vo01jdLMkAKgpmbyifI74vF4feZ/9kVCX4m3Go8Qr8T9HZ522SDxBwKBgQDsO3SBAfvu2YiXqCFQrjN2ltIGXqsIOjzpxeR0tqLmV8kQ7rdO5WxAMLcT7aUdxywR2tmvhmKbat901WFMhHd8nSZDd5rC5fBjYGk57p28GOhAHVMf2depK5ti96VeIb+vNMZ+Y/9BuGvPUr+NJZ523v2w81Eq1Peu7vzBoQuYiQKBgDCV9HnY81PNuhaZ6EYAnAFVOjPNhFR/LXS8zHnhvL/N/sJyOyV4wK+J7oxH0tJO/ceWh3dc2i+ZJGH5ugqNBFKMdaDwa/G96u6L+zkd0c0oe8kIOKvl+0jaXTKuHHO/6oDK1k/6TTtgRUwE6yH5AW+d0EAU+OL8fvjfvf6HtWHpAoGBAL5NiyYeLhEVBvd9RxFStDyVdS4Sd1+SYt3EQK1RL9u13lEYrW0OXu07EVEpgXmCqNrdXb4L2UXwi4D4IIDeuc8WXeu931ugls3Hrh0wP7t2qSp1NTCUcipi6bOJYpt5PozrS/gnoEsWQStsMsmHA6gpLV+uM7X4iGtRNSbbEJdZAoGAHUbMyVW5NE95zZLAA6fvUb6EcrfFRMml0JnmyFi1Hj5CCDJGi3LGPRQ5XpJ+XcfrgzksUdvdQqUp1hiLpgRaH0UjOQCuHq7VZyfNNFHZkt9AvX3o4dS5Iw0IZ++Eowx2+zx1imUgkRXhRqeL8sWSEDn6TCGLiD79Gy5UMH4GBk0=";

    /**
     * me 沙箱
     */
//    public static final String RSA2_PRIVATE = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCS5r+k0eLCnGLvrN29Hugj9xjR57AisTTXpjkuJ7bNlu8HjAry6C5k6LwKQjEDckE+WOKwJZ6qJCpzF+xnM0xg8PYyYL4Ar6AjNSx2gf2FranTGTnFl5werx6jMLIfKP/ToMXFQX0o14kIkFpBrBzuPt871fPc1eSbcJoGs3Ld9Ee+1r2ozESdPlngNk9grU1/g39vMwyfhEeYXjsGAiaqkuUv1FbeVFfdbmLzNzK+drRzRBIrhV0tZesu1kqG3V5tOdUX/Aw/9lxkRYmAmujYCEj4xZgnEOJ02eyia7ch6Q05HrI8ejjvBmNc2j0a/a5Do1QWQirMvCcZqKzdEy6XAgMBAAECggEAaH8iFkGDF8KdEjcaqLxCj8rm6fHwtkzt9PT2OeHgRJC4SdsRssb8saVolbeOHBB7Xrllz72DBy6gz6xV6vdvvonJ5/4vSlekB0d88HP5fs0RP+vyz57QXHdfyOAL5OSKTuVPqcizGolvnhrpSUwAbG1m6K6CQTYZ34SS3G9sJ3vYHLfgpbpvHRWF0UamWf0N78yPkqImih972QuYJca5ZRYZL2+LKJ6qmLJq2vNUNvAJojqr9SvXGWWwqHihw8mZJbvnjOnjtfb22LvVpUzSSqDf9kOQ5Czv9EF2nR8VwtfC44wnqWC1qguxBGxCS0+IXPO5CbG556mY7oDZosfz4QKBgQDK1MON4ysF51BZVNQvM+/I7fEgVt5a8CP+P3dSa89NurfR7ohfHbeuu4dVD5vwxoro5vVdKAL9vrz3GE+H1gF6lzPKfDjRcMbZ3KeT1AedmrNYd6OFC1HIH/hcsjJnxMSAMgELFsOJZHQEjCH8asigtHcAS2LELAcqNtgM1wsWBwKBgQC5aL+0m/jkkVfPOl4DxSe+DuAjNgpYmnLUtAL33FY6IQAaChamNfk10tlxl9BwtCEaC4or7d57lcD9GpahD0e+1AN0Fjd6RJHkui1ptH3FKTSGxKsBY6lsIn70jXxs9PxpKYprHP3JgXAPa89GHFDc4as4is/q5SdQXVJzP9R+8QKBgAWfmoBfkPzL6f1gDbX1UauXdTz5S5bn24ecCNnfJVM5XwlR/LPuZf5RMrJYCXHGf2lvpdPcXSDd3e1X3jSc6VOVx5jQkt6zqr+1j2vY0BE9jcVhI8Z3ht/uivs+8YjQ+sW3HTJKgkdX4qORowuVhlR2TGpUrLtVoSk5dgn9GFCXAoGAZcPDrRsK0lGgE6LipUkaViwOA+WOajFjo7GmC2tJfKBOUPyGj/YB8fPn23xLMQD9RjFdRl1KByUcBxAH6yDa7TerKZVkH9zxAszZTjJ98bB7HxllDt8nx61rZ0kdKSSSQhpF2iLQfUBpRF2VJ9M04veOJfuywSfDm02NiqpXNNECgYAQEcwUPhuC1LR6igii9rWi4qom0OB8EYupP6rp6j3/jjxh32ARSUxqepl58YaHc/sI6LCirhxpY9yQeHWY6eag/Oo9k/3+8DWt95xkChCmaSUThh6tFvNm+/TVNOLDc7ew+n+BpP2lwE+adWxoihMteZBbnnVl5giZkW44RP8Yxg==";

    /**
     * 沙箱-金舞团 2016073000125585
     * 正式：2017021005616618
     */
    public static final String APPID = "2016073000125585";

    /**
     * 金舞团
     */
    public static final String RSA2_PRIVATE = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCMImnLprrBJpiUzEbyRvWsryMRR9vV28idrU8KPoe3H+dM3j1USQNgCLgKvowOGRxujATjnNVab0U+XqPPOrBcFXN48B4MOTEl1jLQxtz1XcgHYNAVFwJWiLk/pYKHaxdW1xR1xQNVsO3EkR7dxOhEKf2KkxTQAfqvEfA++6t5ZaJhrr5YqasuEogA29y3Jvbc3l4e3EsfxhOJDNmaH1rpoLrRRkvS36oH2oIFS/BnOwM2GOyRhMF7Qe9yk/hGuAsa3N4a2MpbQYyMNg430H/cTutVOI1pqEtMKd0Uzx1VPGP3XxQra728UAJMKemJV0r+7sWUQw2wuyo7e/LYstrtAgMBAAECggEAA2xfy0LScx9/QLtQzGBhZguNPsHTpADPgNKYCLcIV7fmzaDsPJ/f/juYS2LmRv3kDOtMv0a4i5IGTgS/2bMkVcuMW5r6EPBgu/zklucUxMW6ujtqOemq+/QxGlXvv2ElW9C7Rjk+4JwG4vNsnpxN1ER2VilLq90wg5Bbnx5EzR1d7vNEfxxXRUu4ZllWCME8bp1N2cbYH48gSjA81SKfgSCdmmYhoNmCUefT9GtdSThBvhHLcj2+oQWJT1vgnXqB66eixw1YT0IkrEoEr5Xo1OAOMEdjhtKRzsrBDaCGllDZ79iv37HZFuCL4acav5q32sZIy0SClhsyvHqj4f7OYQKBgQD1Gl4hyBM8+2Gm16F/rya2tJNtoMeO80HCCpxaPf/Ip99dj8MIN+PiUBnUff/jN4VlS946Fb3iWfScSjHnLbKzLGgZfeZ8GUyNfYORqq+Sai4bR4NTn851kouNWPaE/QDxOj73yFQwagaWWg5RqFBZUT0RFjE03kX5rh+8cGP3OQKBgQCSXVlVqygyxfXYWv/HWaqsl/H16orB3aVxGpPVAbT5wIlxPbe0+kMRhnrS59NfwuP+cUJKORFn4cuXxYjtOSQj8CBFituzsp0430GZ4FdjAbyLkBgTOAG1hwAI/zs4HDMXzdCvbyEFSlmcNCYVIJe9eOepDSGzTpMdvE+FxrLtVQKBgQDYRpULUAWRyOvpEdrC1WXe0EZK9RVCEWpT85L1mkGq6F6Tq3hYNyERoMa6FxiFgYdm0+Ra8rZkFiZfBqU0LcHCkouk+tA3bwd6zmcbUTathp36mkbKsne2jUIwznBw9uMu+Mt5Tz7inZwEbaC280M7HRE6k4+F7CBo6fm9CJtdGQKBgCjE5dBuF4llCODWIC8YM+lVfalhRntP0PibTZhknFOBJQetCFHZ7/qeufGrb35aHAXQ6IUiNk34YCyLcmOZqg50oZidYCtC0nZ4AZ7qFY1Xe3xoi7w2uosk6oaXCZjMihLUWo+zZcBCgIl5IS57YKj/V5AIP13hwirjPEdoHIjtAoGBAM/QeKQOcK4qyRKbkxZUYTzv88C9DOENBe4v/KYpI0xy2ZZzDdyjfv4YJhTsrfHOUb4TjM0Ox0cac3bd64rYzDJH0xkq9M/aNKVQ+AlMFMkpl27vFFnGSzeYWubu5TLSrUvo6JkrsEw+AKVdzS+42PlNSgX6L4gMWBk+rHC2yqrV";

    //沙箱
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
                    GDLogUtils.i(TAG, "resultInfo:" + resultInfo);
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        Toast.makeText(PayActivity.this, "支付成功" + resultInfo, Toast.LENGTH_SHORT).show();
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
//        final String orderInfo = "app_id=2015052600090779&biz_content=%7B%22timeout_express%22%3A%2230m%22%2C%22seller_id%22%3A%22%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%2C%22total_amount%22%3A%220.02%22%2C%22subject%22%3A%221%22%2C%22body%22%3A%22%E6%88%91%E6%98%AF%E6%B5%8B%E8%AF%95%E6%95%B0%E6%8D%AE%22%2C%22out_trade_no%22%3A%22314VYGIAGG7ZOYY%22%7D&charset=utf-8&method=alipay.trade.app.pay&sign_type=RSA2&timestamp=2016-08-15%2012%3A12%3A15&version=1.0&sign=MsbylYkCzlfYLy9PeRwUUIg9nZPeN9SfXPNavUCroGKR5Kqvx0nEnd3eRmKxJuthNUx4ERCXe552EV9PfwexqW%2B1wbKOdYtDIb4%2B7PL3Pc94RZL0zKaWcaY3tSL89%2FuAVUsQuFqEJdhIukuKygrXucvejOUgTCfoUdwTi7z%2BZzQ%3D";
        GDLogUtils.i(TAG, "orderInfo:" + orderInfo);
        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                /**
                 * 开启沙窗模式
                 */
                EnvUtils.setEnv(EnvUtils.EnvEnum.SANDBOX);
                PayTask alipay = new PayTask(PayActivity.this);
                Map<String, String> result = alipay.payV2(orderInfo, true);
                GDLogUtils.i("map", result.toString());

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
