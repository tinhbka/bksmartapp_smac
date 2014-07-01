package com.bksmart.smarttalk.adapter;

import java.util.ArrayList;

import com.bksmart.smarttalk.models.ItemChat;
import com.bksmart.smarttalk.utils.Utils;

import com.bksmart.smarttalk.R;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ItemChatAdapter extends BaseAdapter{
	
	private ArrayList<ItemChat> data;
	private Context mContext;
	private LayoutInflater layoutInflater;
	
	public ItemChatAdapter(ArrayList<ItemChat> data, Context context){
		this.mContext = context;
		this.data = data;
		this.layoutInflater = LayoutInflater.from(context);
		for(int i = 0; i<data.size();i++){
			Log.i("Data  " + i, data.get(i).getContent());
		}
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return data.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int p, View v, ViewGroup vg) {
		ChatHolder chatHolder;
		if(v == null){
			v = layoutInflater.inflate(R.layout.item_chat, null);
			chatHolder = new ChatHolder();
			chatHolder.mTextViewUser = (TextView)v.findViewById(R.id.item_chat_TV_user);
			chatHolder.mTextViewRobo = (TextView)v.findViewById(R.id.item_chat_TV_robo);
			chatHolder.mTVTimeRobo = (TextView)v.findViewById(R.id.item_chat_time_robo);
			chatHolder.mTVTimeUser = (TextView)v.findViewById(R.id.item_chat_time_user);
			v.setTag(chatHolder);
		}else{
			chatHolder = (ChatHolder)v.getTag();
		}
		
		chatHolder.mTextViewUser.setText(data.get(p).getContent());
		chatHolder.mTextViewRobo.setText(data.get(p).getContent());
		chatHolder.mTVTimeUser.setText(Utils.getTimeAgo((data.get(p).getTime()),System.currentTimeMillis()));
		chatHolder.mTVTimeRobo.setText(Utils.getTimeAgo((data.get(p).getTime()),System.currentTimeMillis()));
		if(data.get(p).getId_user_send() == 1){
			((RelativeLayout)v.findViewById(R.id.item_chat_user)).setVisibility(View.VISIBLE);
			((RelativeLayout)v.findViewById(R.id.item_chat_robot)).setVisibility(View.GONE);
		}else{
			((RelativeLayout)v.findViewById(R.id.item_chat_user)).setVisibility(View.GONE);
			((RelativeLayout)v.findViewById(R.id.item_chat_robot)).setVisibility(View.VISIBLE);
		}
		
		return v;
	}
	
	class ChatHolder{
		TextView mTextViewUser;
		TextView mTextViewRobo;
		TextView mTVTimeUser;
		TextView mTVTimeRobo;
	}

}
