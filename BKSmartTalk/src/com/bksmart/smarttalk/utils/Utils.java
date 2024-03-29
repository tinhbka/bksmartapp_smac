package com.bksmart.smarttalk.utils;

import java.util.Calendar;
import java.util.Date;

import android.content.Context;

import com.bksmart.smarttalk.BKSmartApplication;
import com.bksmart.smarttalk.R;

public class Utils {
	private static final long YEAR = 60 * 60 * 24 * 365;
	private static final long MONTH = 60 * 60 * 24 * 30;
	private static final long DAY = 60 * 60 * 24;
	private static final long HOUR = 60 * 60;
	private static final long MIN = 60;
	private static final long SECOND = 1;
	
	public static Date getDate(long milliSeconds) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(milliSeconds);
		return calendar.getTime();
	}
	
	public static String convertTimeMilisecondToTimeAgo(long time) {
		Context mContext = BKSmartApplication.getAppContext();
		time = time / 100000;
		long year = time / YEAR;
		time -= year * YEAR;
		long month = time / MONTH;
		time -= month * MONTH;
		long day = time / DAY;
		time -= day * DAY;
		long hour = time / HOUR;
		time -= hour * HOUR;
		long min = time / MIN;
		time -= min * MIN;
		long second = time;
		if (year > 0) {
			if (month > 0)
				return year + mContext.getResources().getString(R.string.yeah) + month + mContext.getResources().getString(R.string.mounth);
			return year + mContext.getResources().getString(R.string.yeah);
		} else if (month > 0) {
			if (day > 0)
				return month + mContext.getResources().getString(R.string.mounth) + day + mContext.getResources().getString(R.string.date);
			return month + mContext.getResources().getString(R.string.mounth);
		} else if (day > 0) {
			if (hour > 0)
				return day + mContext.getResources().getString(R.string.date) + hour + mContext.getResources().getString(R.string.hour);
			return day + mContext.getResources().getString(R.string.date);
		} else if (hour > 0) {
			if (min > 0)
				return hour + mContext.getResources().getString(R.string.hour) + min + "\'";
			return hour + mContext.getResources().getString(R.string.hour);
		} else if (min > 0)
			return min + "\'";
		else if (second > 0 && second < 16)
			return second + "\"";
		return mContext.getResources().getString(R.string.justnow);
	}
	private static final int SECOND_MILLIS = 1000;
	private static final int MINUTE_MILLIS = 60 * SECOND_MILLIS;
	private static final int HOUR_MILLIS = 60 * MINUTE_MILLIS;
	private static final int DAY_MILLIS = 24 * HOUR_MILLIS;


	public static String getTimeAgo(long time,long now) {
		Context mContext = BKSmartApplication.getAppContext();
	    if (time < 1000000000000L) {
	        // if timestamp given in seconds, convert to millis
	        time *= 1000;
	    }

	    if (time > now || time <= 0) {
	        return null;
	    }

	    // TODO: localize
	    final long diff = now - time;
	    if (diff < MINUTE_MILLIS) {
	        return mContext.getResources().getString(R.string.justnow);
	    } else if (diff < 2 * MINUTE_MILLIS) {
	        return mContext.getResources().getString(R.string.a_minute_ago);
	    } else if (diff < 50 * MINUTE_MILLIS) {
	        return diff / MINUTE_MILLIS + " " + mContext.getResources().getString(R.string.minute_ago);
	    } else if (diff < 90 * MINUTE_MILLIS) {
	        return mContext.getResources().getString(R.string.a_hour_ago);
	    } else if (diff < 24 * HOUR_MILLIS) {
	        return diff / HOUR_MILLIS + " "+ mContext.getResources().getString(R.string.hour_ago);
	    } else if (diff < 48 * HOUR_MILLIS) {
	        return mContext.getResources().getString(R.string.yesterday);
	    } else {
	        return diff / DAY_MILLIS + " "+ mContext.getResources().getString(R.string.day_ago);
	    }
	}
}
