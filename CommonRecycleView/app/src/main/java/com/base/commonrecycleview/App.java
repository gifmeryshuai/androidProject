package com.base.commonrecycleview;

import android.app.Application;

/**
 * Created by 东帅 on 2016/9/23.
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        CrashApphandler.getInstance().init(this);
    }
}
