package com.goldendance.client.base;

import android.app.Activity;
import android.app.Application;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hemingway1014@gmail.com on 2016/12/10.
 */

public class BaseApplication extends Application {
    public List<Activity> activityStack = null;

    @Override
    public void onCreate() {
        activityStack = new ArrayList<Activity>();
        // 异常处理
        BaseCrashHandler handler = BaseCrashHandler.getInstance();
        handler.init(this);

        // 程序异常关闭1s之后重新启动
        new RebootThreadExceptionHandler(getBaseContext());
        super.onCreate();
    }

    @Override
    public void onTerminate() {

        super.onTerminate();

    }

    // 在内存低时,发送广播可以释放一些内存
    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    public void exitApp() {
        for (Activity activity : activityStack) {
            if (activity != null) {
                activity.finish();
            }
        }
    }
}
