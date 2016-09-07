package com.base.crash;

import android.app.Application;

/**
 * Created by 东帅 on 2016/9/6.
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        CrashApphandler.getInstance().init(this);
    }
}
