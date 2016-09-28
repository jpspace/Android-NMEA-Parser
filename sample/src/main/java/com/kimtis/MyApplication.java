package com.kimtis;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

/**
 * Created by uiseok on 2016-09-06.
 */
public class MyApplication extends Application{
    private static Context mContext;

    private static volatile MyApplication instance = null;
    private static volatile Activity currentActivity = null;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        instance = this;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        instance = null;
    }

    public static Context getContext() {
        return mContext;
    }

//    @Override
//    protected void attachBaseContext(Context base) {
//        super.attachBaseContext(base);
//        MultiDex.install(this);
//    }
}
