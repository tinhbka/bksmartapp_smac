package com.bksmart.smarttalk.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bksmart.smarttalk.R;
import com.bksmart.smarttalk.models.LanguageModel;

public class ItemLanguageAdapter extends BaseAdapter{

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
		return arg0;
	}
	



	private LayoutInflater mInflater;
	private ArrayList<LanguageModel> data;

	public ItemLanguageAdapter(Context context, ArrayList<LanguageModel> objects) {
		mInflater = LayoutInflater.from(context);
		this.data = objects;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewHolder mViewHolder;
		LanguageModel mLangData = data.get(position);
		if (convertView == null) {
			mViewHolder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.item_language, null);
			mViewHolder.tvCountry = (TextView) convertView
					.findViewById(R.id.item_lag_name);
			mViewHolder.tvCountry.setText(mLangData.getLang());
			mViewHolder.mIVItemLangSelect = (ImageView) convertView.findViewById(R.id.item_lag_check);
			convertView.setTag(mViewHolder);
		}else
			mViewHolder = (ViewHolder) convertView.getTag();
		
		if(mLangData.isSelected()){
			mViewHolder.tvCountry.setTextColor(Color.parseColor("#007acc"));
			mViewHolder.mIVItemLangSelect.setBackgroundResource(R.drawable.icon_tick);
		}else{
			mViewHolder.tvCountry.setTextColor(Color.parseColor("#757575"));
			mViewHolder.mIVItemLangSelect.setBackgroundResource(R.drawable.icon_nottick);
		}
		return convertView;
	}
	
	

	public class ViewHolder {
		public TextView tvCountry;
		public ImageView mIVItemLangSelect;
	}

}
