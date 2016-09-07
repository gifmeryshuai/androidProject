package com.example.crashapp;


import com.base.crashlog.AppCrashHandler;

import android.app.Application;

public class App extends Application {
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		
//		CrashAppLog.getInstance().init(this);
		
		AppCrashHandler.getInstance().init(this);
		
	}

}
