package com.bksmart.smarttalk;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_splash);
		new Handler().postDelayed(new Runnable() {
			
			@Override
			public void run() {
				Intent i = new Intent(SplashActivity.this,SettingActivity.class);
				i.putExtra("chat", true);
				finish();
				startActivity(i);
			}
		}, 2000L);
	}
}
