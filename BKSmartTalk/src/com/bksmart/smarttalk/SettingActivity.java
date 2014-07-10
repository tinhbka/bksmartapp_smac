package com.bksmart.smarttalk;

import java.util.ArrayList;
import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.MailTo;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.bksmart.smarttalk.adapter.ItemLanguageAdapter;
import com.bksmart.smarttalk.adapter.ItemLanguageAdapter.ViewHolder;
import com.bksmart.smarttalk.models.LanguageModel;

public class SettingActivity extends Activity implements OnClickListener {

	Locale myLocale;
	ArrayList<LanguageModel> listLang;
	ItemLanguageAdapter langAdapter;
	ListView lvLang;
	private int mCurItemSelect = -1;
	Button btSave;
	BKSmartApplication mApp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_setting);
		mApp = (BKSmartApplication) getApplication();
		if (mApp.getIsFirst() == 1 && getIntent().getExtras().getBoolean("chat", true)) {
			startActivity(new Intent(this,ChatActivity.class));
			finish();
			return;
		}
		myLocale = getResources().getConfiguration().locale;
		lvLang = (ListView) findViewById(R.id.layout_setting_lvLang);
		btSave = (Button) findViewById(R.id.layout_setting_btSave);
		btSave.setOnClickListener(this);
		listLang = new ArrayList<LanguageModel>();
		if (myLocale.getLanguage().equals("en")) {
			listLang.add(new LanguageModel("English", true));
			listLang.add(new LanguageModel("Tiếng Việt", false));
			mCurItemSelect = 0;
		} else {
			listLang.add(new LanguageModel("English", false));
			listLang.add(new LanguageModel("Tiếng Việt", true));
			mCurItemSelect = 1;
		}
		langAdapter = new ItemLanguageAdapter(getApplicationContext(), listLang);
		lvLang.setAdapter(langAdapter);
		lvLang.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int i,
					long arg3) {
				if (i == mCurItemSelect)
					return;
				listLang.get(mCurItemSelect).setSelected(false);
				listLang.get(i).setSelected(true);
				langAdapter.notifyDataSetChanged();
				mCurItemSelect = i;
			}
		});
	}

	public void setLocale(String lang) {

		myLocale = new Locale(lang);
		Resources res = getResources();
		DisplayMetrics dm = res.getDisplayMetrics();
		Configuration conf = res.getConfiguration();
		conf.locale = myLocale;
		res.updateConfiguration(conf, dm);
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.layout_setting_btSave) {
			if (mCurItemSelect > -1) {
				if (mCurItemSelect == 0) {

					if (!myLocale.getLanguage().equals("en")) {
						Toast.makeText(getApplicationContext(),
								"Changed language to English",
								Toast.LENGTH_SHORT).show();
						setLocale("en");
						mApp.setLang("en");
						setResult(69);
					}
				} else {

					if (!myLocale.getLanguage().equals("vi")) {
						Toast.makeText(getApplicationContext(),
								"Đã đổi sang ngôn ngữ Tiếng Việt",
								Toast.LENGTH_SHORT).show();
						setLocale("vi");
						mApp.setLang("vi");
						setResult(69);
					}
				}
			}
			if (mApp.getIsFirst() == 0) {
				mApp.setIsFirst(1);
				startActivity(new Intent(this,ChatActivity.class));
			}
			finish();
		}
	}
}
