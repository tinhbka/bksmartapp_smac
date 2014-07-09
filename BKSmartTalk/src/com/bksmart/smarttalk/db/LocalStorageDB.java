package com.bksmart.smarttalk.db;

import java.util.ArrayList;

import com.bksmart.smarttalk.BKSmartApplication;
import com.bksmart.smarttalk.models.ItemChat;
import com.bksmart.smarttalk.models.User;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;

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
		ContentValues ret = new ContentValues(4);
		ret.put(COLUMN_TIME, ic.getTime());
		ret.put(COLUMN_CONTENT, ic.getContent());
		ret.put(COLUMN_USER_RECEIVE,ic.getId_user_receice());
		ret.put(COLUMN_USER_SEND,ic.getId_user_send());
		try{
			synchronized (this) {
				database.beginTransaction();
				try{
					database.insert(TABLE_MESSAGE, null, ret);
					database.setTransactionSuccessful();
				}finally{
					database.endTransaction();
				}
			}
		}catch(SQLException e){
			Log.e("SQLException", e.getMessage());
		}
	}
	
	public void deleteMessage(){
		Cursor cursor = null;
		int index = 0;
		try {
			cursor = database.query(TABLE_MESSAGE,new String[]{ COLUMN_ID}, null, null, null,null, null,"1");
			if(cursor != null){
				index = cursor.getInt(0);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public User getUser(){
		User us = new User();
		return us;
	}
	
	public ArrayList<ItemChat> getListMessage(){
		ArrayList<ItemChat> it = new ArrayList<ItemChat>();
		
		Cursor cursor = null;
		try {
			cursor = database.query(TABLE_MESSAGE, null, COLUMN_USER_RECEIVE + " = " + BKSmartApplication.getIDRobot() + " OR " + COLUMN_USER_SEND + " = " + BKSmartApplication.getIDRobot(), null, null, null, null, "50");
			if(cursor!=null){
				int idCi = cursor.getColumnIndex(COLUMN_ID);
				int timeCi = cursor.getColumnIndex(COLUMN_TIME);
				int contentCi = cursor.getColumnIndex(COLUMN_CONTENT);
				int sendCi = cursor.getColumnIndex(COLUMN_USER_SEND);
				int recCi = cursor.getColumnIndex(COLUMN_USER_RECEIVE);
				int counter = 0;
				while(cursor.moveToNext()){
					it.add(new ItemChat(cursor.getInt(idCi), cursor.getLong(timeCi), cursor.getString(contentCi), cursor.getInt(sendCi), cursor.getInt(recCi)));
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return it;
	}
	
	
	public void removeAllData() {
		try {
			synchronized (this) {
				database.beginTransaction();
				try {
					database.delete(TABLE_MESSAGE, null, null);
					database.setTransactionSuccessful();
				} finally {
					database.endTransaction();
				}
			}
		} catch (SQLException e) {
			handleSqlException(e);
		}
	}

	/**
	 * Open a writable instance of the database.
	 * 
	 * @throws SQLiteException
	 */
	protected void open() throws SQLiteException {
		database = dbOpenHelper.getWritableDatabase();
		System.out.println("open database = " + database);
	}

	/**
	 * Close the database.
	 */
	protected void close() {
		database.close();
		database = null;
		System.out.println("close database = " + database);
	}

	private void closeAndOpenWritable() {
		try {
			synchronized (this) {
				close();
				open();
			}
		} catch (SQLException e) {
			Log.d(getClass().getName(), "Fatal error, unable to open db!");
			e.printStackTrace();
		}
	}

	private final void handleSqlException(SQLException e) {
		Log.e(LocalStorageDB.class.getName(), "handleSqlException() :: SQL Exception detected:");
		Log.e(LocalStorageDB.class.getName(), "handleSqlException() :: Cache dir: " + mContext.getExternalCacheDir());
		Log.e(LocalStorageDB.class.getName(), "handleSqlException() :: External storage state: " + Environment.getExternalStorageState());
		try {
			Log.e(LocalStorageDB.class.getName(), "handleSqlException() :: Free space: " + LocalStorage.getFreeSpace(mContext));
		} catch (StorageUnavailableException e1) {
			e1.printStackTrace();
		}
		Log.e(LocalStorageDB.class.getName(), "handleSqlException() :: printing exception ...");
		e.printStackTrace(System.err);
		closeAndOpenWritable();
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
