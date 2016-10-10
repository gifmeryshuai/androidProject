package com.base.crashlog;

import android.app.Application;

/**
 * Created by 东帅 on 2016/10/10.
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        AppCrashHandler.getInstance().init(this);
    }
}
