package com.goldendance.client;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.goldendance.client.base.BaseActivity;
import com.goldendance.client.http.GDHttpManager;
import com.goldendance.client.http.GDOnResponseHandler;
import com.goldendance.client.utils.GDLogUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;

import okhttp3.Response;

public class MainActivity extends BaseActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    public void initIntent() {

    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
//        super.onCreate(savedInstanceState, persistentState);
//    }

    @Override
    protected void onStart() {

        super.onStart();
        EventBus.getDefault().register(this);
    }

//    /**
//     * 生成一个二维码图像
//     *
//     * @param url       传入的字符串，通常是一个URL
//     * @param QR_WIDTH  宽度（像素值px）
//     * @param QR_HEIGHT 高度（像素值px）
//     * @return
//     */
//    public final Bitmap create2DCoderBitmap(String url, int QR_WIDTH,
//                                            int QR_HEIGHT) {
//        try {
//            // 判断URL合法性
//            if (url == null || "".equals(url) || url.length() < 1) {
//                return null;
//            }
//            Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
//            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
//            // 图像数据转换，使用了矩阵转换
//            BitMatrix bitMatrix = new QRCodeWriter().encode(url,
//                    BarcodeFormat.QR_CODE, QR_WIDTH, QR_HEIGHT, hints);
//            int[] pixels = new int[QR_WIDTH * QR_HEIGHT];
//            // 下面这里按照二维码的算法，逐个生成二维码的图片，
//            // 两个for循环是图片横列扫描的结果
//            for (int y = 0; y < QR_HEIGHT; y++) {
//                for (int x = 0; x < QR_WIDTH; x++) {
//                    if (bitMatrix.get(x, y)) {
//                        pixels[y * QR_WIDTH + x] = 0xff000000;
//                    } else {
//                        pixels[y * QR_WIDTH + x] = 0xffffffff;
//                    }
//                }
//            }
//            // 生成二维码图片的格式，使用ARGB_8888
//            Bitmap bitmap = Bitmap.createBitmap(QR_WIDTH, QR_HEIGHT,
//                    Bitmap.Config.ARGB_8888);
//            bitmap.setPixels(pixels, 0, QR_WIDTH, 0, 0, QR_WIDTH, QR_HEIGHT);
//            // 显示到一个ImageView上面
//            // sweepIV.setImageBitmap(bitmap);
//            return bitmap;
//        } catch (WriterException e) {
//            Log.i("log", "生成二维码错误" + e.getMessage());
//            return null;
//        }
//    }


    @Override
    public void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);

//        EventBus.getDefault().register(getActionBar());
        findViewById(R.id.btnCrash).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                GDLogUtils.i(TAG, "" + 1 / 0);
                EventBus.getDefault().post(new MessageEvent("aaa", "aaa"));
//                initData();
//                Intent intent = new Intent(MainActivity.this, TestMainActivity.class);
//
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                    startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(MainActivity.this, view, "crash").toBundle());
//                } else {
//                    startActivity(intent);
//                }

//
//                ImageView ivLogo = (ImageView) findViewById(R.id.ivLogo);
//                Bitmap bitmap = create2DCoderBitmap("https://www.shaishufang.com/mobile/show/book/id/255648", ivLogo.getWidth(), ivLogo.getHeight());
//                ivLogo.setImageBitmap(bitmap);

            }
        });
    }

    public class MessageEvent {
        public final String name;
        public final String password;

        public MessageEvent(String name, String password) {
            this.name = name;
            this.password = password;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void helloEventBus(MessageEvent message) {
        GDLogUtils.d(TAG, message.name);
    }


    private void initData() {
//        /api2/bookroom/profile?show=tag&page_size=1&undeal=true&fmt=json
        String url = "/api2/bookroom/profile?show=tag&page_size=1&undeal=true&fmt=json";

        GDHttpManager.doGet(url, new GDOnResponseHandler() {
            @Override
            public void onStart() {
                GDLogUtils.d(TAG, "start ---------------------");
                super.onStart();
            }

            @Override
            public void onSuccess(Response response) {
                try {
                    GDLogUtils.d(TAG, "body:" + response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                super.onSuccess(response);
            }

            @Override
            public void onFailed(IOException e) {
                Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                super.onFailed(e);
            }

            @Override
            public void onEnd() {
                GDLogUtils.d(TAG, "end  ---------------------");
                super.onEnd();
            }
        });
    }

    @Override
    protected void onPause() {

        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onDestroy() {
//        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
