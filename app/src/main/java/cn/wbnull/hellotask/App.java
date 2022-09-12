package cn.wbnull.hellotask;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import java.util.HashSet;
import java.util.Set;

/**
 * 应用初始化
 *
 * @author dukunbiao(null)  2022-09-11
 */
public class App extends Application {

    private Activity currentActivity;
    private final Set<Activity> allActivities = new HashSet<>();
    @SuppressLint("StaticFieldLeak")
    private static App instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        initActivityLifecycleCallbacks();
    }

    private void initActivityLifecycleCallbacks() {
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                allActivities.add(activity);
                setCurrentActivity(activity);
            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {
                setCurrentActivity(activity);
            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                if (activity.equals(currentActivity)) {
                    setCurrentActivity(null);
                }
                allActivities.remove(activity);
            }
        });
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    public static App getInstance() {
        return instance;
    }

    private void setCurrentActivity(Activity currentActivity) {
        this.currentActivity = currentActivity;
    }

    public Activity getCurrentActivity() {
        return currentActivity;
    }

    public static Activity getContext() {
        return getInstance().getCurrentActivity();
    }

    public static Context getAppContext() {
        return getInstance().getApplicationContext();
    }

    public void exit() {
        for (Activity activity : allActivities) {
            if (activity != null) {
                activity.finish();
            }
        }
    }
}
