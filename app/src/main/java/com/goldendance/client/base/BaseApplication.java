package com.goldendance.client.base;

import android.app.Activity;
import android.app.Application;

import com.tendcloud.tenddata.TCAgent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hemingway1014@gmail.com on 2016/12/10.
 */

public class BaseApplication extends Application {
    public List<Activity> activityStack = null;

    @Override
    public void onCreate() {
        activityStack = new ArrayList<>();
        // 异常处理
        BaseCrashHandler handler = BaseCrashHandler.getInstance();
        handler.init(this);
        // 程序异常关闭1s之后重新启动
//        new RebootThreadExceptionHandler(getBaseContext());
        super.onCreate();
        TCAgent.init(this);
        /*TalkingData start*/
        TCAgent.LOG_ON = true;

        // App ID: 在TalkingData创建应用后，进入数据报表页中，在“系统设置”-“编辑应用”页面里查看App ID。
        // 渠道 ID: 是渠道标识符，可通过不同渠道单独追踪数据。
//        http://doc.talkingdata.com/posts/21
//        系统计算渠道ID时，读取渠道ID的优先级如下：“多渠道打包工具”产生的渠道ID> AndroidManifest.xml中配置的渠道ID>TCAgent.init(this, "您的 App ID", "渠道 ID")方法配置的渠道ID。        TCAgent.init(this, "A569BCED7D174E2F8A63BADDAB7CB748", "TestMode");
        // 如果已经在AndroidManifest.xml配置了App ID和渠道ID，调用TCAgent.init(this)即可；或与AndroidManifest.xml中的对应参数保持一致。
        TCAgent.setReportUncaughtExceptions(true);
        /*TalkingData end*/
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

    public void addActivity(Activity activity) {
        activityStack.add(activity);
    }

    public void removeActivity(Activity activity) {
        if (activityStack.contains(activity)) {
            activityStack.remove(activity);
        }
    }
}
