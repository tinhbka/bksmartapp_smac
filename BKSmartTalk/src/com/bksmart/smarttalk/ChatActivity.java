package com.bksmart.smarttalk;import java.net.URLEncoder;import java.util.ArrayList;import java.util.Locale;import org.json.JSONException;import org.json.JSONObject;import android.annotation.SuppressLint;import android.content.Intent;import android.content.res.Configuration;import android.graphics.Color;import android.graphics.drawable.ColorDrawable;import android.os.Bundle;import android.speech.RecognizerIntent;import android.support.v4.app.FragmentActivity;import android.util.Log;import android.view.ContextMenu;import android.view.Menu;import android.view.MenuItem;import android.view.View;import android.view.ContextMenu.ContextMenuInfo;import android.view.View.OnClickListener;import android.view.View.OnFocusChangeListener;import android.widget.Button;import android.widget.EditText;import android.widget.ListView;import android.widget.Toast;import com.android.volley.Response;import com.android.volley.VolleyError;import com.android.volley.VolleyLog;import com.android.volley.toolbox.JsonObjectRequest;import com.bksmart.smarttalk.adapter.ItemChatAdapter;import com.bksmart.smarttalk.models.ItemChat;import com.bksmart.smarttalk.volleyhttp.VolleyService;import com.bksmart.smarttalk.volleyhttp.VolleySingleton;public class ChatActivity extends FragmentActivity implements OnClickListener {	private ListView listView;	private EditText etInput;	private Button btSend,btVoice;	private ItemChatAdapter chatAdapter;	private ArrayList<ItemChat> list;	private BKSmartApplication mApp;	private static final int REQUEST_CODE = 1234;	@Override	protected void onCreate(Bundle arg0) {		super.onCreate(arg0);		setContentView(R.layout.layout_chat);		mApp = (BKSmartApplication)getApplication();		listView = (ListView) findViewById(R.id.layout_chat_listview);		etInput = (EditText) findViewById(R.id.layout_chat_edittext);		btSend = (Button) findViewById(R.id.layout_chat_btSend);		btVoice = (Button) findViewById(R.id.layout_chat_btVoice);		btSend.setOnClickListener(this);		btVoice.setOnClickListener(this);		list = GetData();		chatAdapter = new ItemChatAdapter(list, getApplicationContext());		listView.setAdapter(chatAdapter);		scrollMyListViewToBottom();	}	@Override	public boolean onCreateOptionsMenu(Menu menu) {		getMenuInflater().inflate(R.menu.main_menu, menu);		return true;	}	@Override	public boolean onOptionsItemSelected(MenuItem item) {		switch (item.getItemId()) {		case R.id.menu_setting:			Intent i = new Intent(this, SettingActivity.class);			i.putExtra("chat", false);			startActivityForResult(i, 69);			break;		default:			break;		}		return super.onOptionsItemSelected(item);	}	@Override	public void onConfigurationChanged(Configuration newConfig) {		super.onConfigurationChanged(newConfig);	}	@Override	protected void onActivityResult(int requestCode, int resultCode, Intent data) {		super.onActivityResult(requestCode, resultCode, data);		if (requestCode == 69) {			if (resultCode == 69) {				Intent i = getIntent();				finish();				startActivity(i);			}		}else if(requestCode == REQUEST_CODE && resultCode == RESULT_OK){			ArrayList<String> matches = data.getStringArrayListExtra(	                RecognizerIntent.EXTRA_RESULTS);			etInput.setText(matches.get(0));		}	}	@Override	public void onClick(View v) {		if (v.getId() == R.id.layout_chat_btSend) {			Log.d("BtSend", "Click");			String massage = etInput.getText().toString();			etInput.setText("");			if (!massage.equals("")) {				ItemChat ic = new ItemChat(1, System.currentTimeMillis(), massage,						1, BKSmartApplication.getIDRobot());				list.add(ic);				mApp.getLocalStorage().insertMessage(ic);				chatAdapter.notifyDataSetChanged();				scrollMyListViewToBottom();				sendMessage(massage);			}		}else		if(v.getId() == R.id.layout_chat_btVoice)		{				Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);		    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,		            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);		    intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 1);		    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, getResources().getString(R.string.code_lang));		    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, getResources().getString(R.string.code_lang));		    intent.putExtra(RecognizerIntent.EXTRA_ONLY_RETURN_LANGUAGE_PREFERENCE, true);		    intent.putExtra(RecognizerIntent.EXTRA_PROMPT, getResources().getString(R.string.talk_voice));		    intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,getClass().getName());		    startActivityForResult(intent, REQUEST_CODE);		}	}	private ArrayList<ItemChat> GetData() {		ArrayList<ItemChat> data = mApp.getLocalStorage().getMessage();		return data;	}		private void scrollMyListViewToBottom() {		listView.post(new Runnable() {			@Override			public void run() {				// Select the last row so it will scroll into view...				listView.setSelection(list.size() - 1);			}		});	}	private void sendMessage(String mess) {		VolleySingleton				.getInstance()				.getRequestQueue()				.add(new JsonObjectRequest(						"http://tech.fpt.com.vn/AIML/api/bots/"								+ BKSmartApplication.getIDSmart()								+ "/chat?request=" + URLEncoder.encode(mess)								+ "&token=" + BKSmartApplication.token, null,						new Response.Listener<JSONObject>() {							@Override							public void onResponse(JSONObject result) {								try {									if (result.has("status")											&& result.getString("status")													.equals("success")) {										ItemChat tc = new ItemChat(												1,												System.currentTimeMillis(),												result.getString("response"),												BKSmartApplication.getIDRobot(),												1);										list.add(tc);										mApp.getLocalStorage().insertMessage(tc);										chatAdapter.notifyDataSetChanged();										scrollMyListViewToBottom();									}								} catch (JSONException e) {									e.printStackTrace();								}							}						}, new Response.ErrorListener() {							@Override							public void onErrorResponse(VolleyError error) {								VolleyLog.e("Volley ErrorResponse: ",										error.getMessage());								ItemChat ic = new ItemChat(1, System										.currentTimeMillis(),										"Sorry,I'm being busy.",										BKSmartApplication.getIDRobot(), 1);								list.add(ic);								mApp.getLocalStorage().insertMessage(ic);								chatAdapter.notifyDataSetChanged();								scrollMyListViewToBottom();							}						}));	}}