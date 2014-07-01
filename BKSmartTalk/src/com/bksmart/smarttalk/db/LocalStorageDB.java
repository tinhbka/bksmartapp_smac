package com.bksmart.smarttalk.db;

import com.bksmart.smarttalk.models.ItemChat;
import com.bksmart.smarttalk.models.User;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class LocalStorageDB {
	
	private Context mContext;
	
	private static final String DATABASE_NAME = "bksmart.db";
	private static final int DATABASE_VERSION = 1;
	private DBOpenHelper dbOpenHelper;
	private SQLiteDatabase database;
	
	/*
	 * Table Name
	 */
	private static final String TABLE_USER = "user";
	private static final String TABLE_MESSAGE = "message";
	
	/**
	 * Column Name table user
	 */
	
	private static final String COLUMN_ID = "id";
	private static final String COLUMN_NAME = "name";
	
	
	private static final String COLUMN_AGE = "age";
	
	/**
	 * @param sex 1 - man
	 * 				0 - woman
	 */
	private static final String COLUMN_SEX = "sex";
	
	/**
	 * @param lang 1 - en
	 * 				0 - vi
	 */
	private static final String COLUMN_LANGUAGE = "lang"; 
	private static final String COLUMN_AVATAR = "ava";
	
	/**
	 * Column Name table Message
	 */
	
	private static final String COLUMN_TIME = "time";
	private static final String COLUMN_CONTENT = "cont";
	private static final String COLUMN_USER_SEND = "user_send";
	private static final String COLUMN_USER_RECEIVE = "user_receive";
	
	
	/**
	 * @param context 
	 * 				of application 
	 */
	public LocalStorageDB(Context context){
		this.mContext = context;
		this.dbOpenHelper = new DBOpenHelper(mContext, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	
	public void insertUser(User us){
		
	}
	
	public void updateUser(){
		
	}
	
	public void insertMessage(ItemChat ic){
		
	}
	
	public User getUser(){
		User us = new User();
		return us;
	}
	
	public ItemChat getListMessage(){
		ItemChat it = new ItemChat();
		return it;
	}
	
	private class DBOpenHelper extends SQLiteOpenHelper {

		// SQL Statements to create new tables:
		private static final String CREATE_TABLE_USER = "create table " + TABLE_USER + " (" + COLUMN_ID + " integer primary key autoincrement, "
				+ COLUMN_NAME + " text not null, " + COLUMN_SEX + " integer, "+ COLUMN_AGE + " integer, "+ COLUMN_LANGUAGE + " integer, "+COLUMN_AVATAR + "text);";
		private static final String CREATE_TABLE_MESSAGE = "create table " + TABLE_MESSAGE + " (" + COLUMN_ID + " integer primary key autoincrement, "
				+ COLUMN_TIME + " integer not null, " + COLUMN_CONTENT + " text not null, "
				+ COLUMN_USER_SEND + " integer not null, " + COLUMN_USER_RECEIVE + " integer not null);";

		/**
		 * Default constructor.
		 * 
		 * @param context
		 *            to use to open or create the database
		 * @param name
		 *            of the database file, or null for an in-memory database
		 * @param factory
		 *            to use for creating cursor objects, or null for the
		 *            default
		 * @param version
		 *            database version number
		 */
		public DBOpenHelper(Context context, String name, CursorFactory factory, int version) {
			super(context, name, factory, version);
		}

		/**
		 * Called when the database is first created.
		 */
		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(CREATE_TABLE_MESSAGE);
			db.execSQL(CREATE_TABLE_USER);
		}

		/**
		 * Called when database needs an update.
		 */
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_MESSAGE);
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
			onCreate(db);
		}
	}
}
