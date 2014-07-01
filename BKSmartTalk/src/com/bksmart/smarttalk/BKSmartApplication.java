package com.bksmart.smarttalk;

import android.app.Application;
import android.content.Context;

public class BKSmartApplication extends Application{
	public static Context mAppContext;
	
	public static Context getAppContext() {
		return mAppContext;
	}

	public static void setAppContext(Context mAppContext) {
		BKSmartApplication.mAppContext = mAppContext;
	}

	public static String token = "d85a64d7-2d59-442f-a21c-c4474bd738a7";
	public static String smart_en = "53aa3c63e4b07e6fddd69bd1";
	public static String smart_vi = "53aa3c6ee4b07e6fddd69bd2";
	public static String URL = "http://tech.fpt.com.vn/AIML/api/bots";
	
	@Override
	public void onCreate() {
		super.onCreate();
		BKSmartApplication.setAppContext(getApplicationContext());
	}
}
