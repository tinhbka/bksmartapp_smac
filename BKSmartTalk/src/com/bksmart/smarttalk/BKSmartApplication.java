package com.bksmart.smarttalk;

import java.util.Locale;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;

import com.bksmart.smarttalk.db.LocalStorage;
import com.bksmart.smarttalk.db.StorageUnavailableException;

public class BKSmartApplication extends Application {

	Locale myLocale;
	public static Context mAppContext;

	public static Context getAppContext() {
		return mAppContext;
	}

	public static void setAppContext(Context mAppContext) {
		BKSmartApplication.mAppContext = mAppContext;
	}

	public static String Lang = "en";

	private SharedPreferences mPref;

	public static String token = "d85a64d7-2d59-442f-a21c-c4474bd738a7";
	public static String smart_en = "53aa3c63e4b07e6fddd69bd1";
	public static String smart_vi = "53aa3c6ee4b07e6fddd69bd2";
	public static String URL = "http://tech.fpt.com.vn/AIML/api/bots";
	private LocalStorage localStorage;
	private Handler handler;

	@Override
	public void onCreate() {
		super.onCreate();
		BKSmartApplication.setAppContext(getApplicationContext());
		mPref = PreferenceManager.getDefaultSharedPreferences(mAppContext);
		BKSmartApplication.Lang = mPref.getString("Lang", "vi");
		myLocale = getResources().getConfiguration().locale;
		setLocale(Lang);
		handler = new Handler();
		try {
			localStorage = LocalStorage.create(this);
		} catch (StorageUnavailableException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
	}

	public void recreateLocalStorage() {
		if (localStorage == null) {
			handler.post(new Runnable() {

				@Override
				public void run() {
					try {
						localStorage = LocalStorage
								.create(BKSmartApplication.this);
					} catch (StorageUnavailableException e) {
						e.printStackTrace();
					} catch (NullPointerException e) {
						e.printStackTrace();
					}
				}
			});
		}
	}

	public final LocalStorage getLocalStorage() {
		return localStorage;
	}

	public static LocalStorage getLocalStorage(Context context) {
		return ((BKSmartApplication) context.getApplicationContext())
				.getLocalStorage();
	}

	public static String getIDSmart() {
		return BKSmartApplication.Lang.equals("en") ? BKSmartApplication.smart_en
				: BKSmartApplication.smart_vi;
	}

	public static int getIDRobot() {
		return BKSmartApplication.Lang.equals("en") ? 0 : 2;
	}

	public void setLang(String lg) {
		mPref.edit().putString("Lang", lg).commit();
		BKSmartApplication.Lang = lg;
	}

	public void setLocale(String lang) {

		myLocale = new Locale(lang);
		Resources res = getResources();
		DisplayMetrics dm = res.getDisplayMetrics();
		Configuration conf = res.getConfiguration();
		conf.locale = myLocale;
		res.updateConfiguration(conf, dm);
	}
}
