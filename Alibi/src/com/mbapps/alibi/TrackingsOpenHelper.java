package com.mbapps.alibi;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TrackingsOpenHelper extends SQLiteOpenHelper {
	
	private static final String DATABASE_NAME = "alibi.db";
	private static final int DATABASE_VERSION = 1;
	
	public static final String TRACKINGS_TABLE_NAME = "trackings";
	public static final String TRACKINGS_KEY_TIME = "time";
	public static final String TRACKINGS_KEY_LAT = "lat";
	public static final String TRACKINGS_KEY_LONG = "long";
	
	private static final String TRACKINGS_TABLE_CREATE = 
			"CREATE TABLE " + TRACKINGS_TABLE_NAME + "(" +
				TRACKINGS_KEY_TIME + " DATETIME," +
				TRACKINGS_KEY_LAT  + " FLOAT,"    +
				TRACKINGS_KEY_LONG + "long FLOAT" +
			")";

	public TrackingsOpenHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(TRACKINGS_TABLE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
		// Drop all current tables
		db.execSQL("DROP TABLE IF EXISTS " + TRACKINGS_TABLE_NAME);
		// Recreate
		onCreate(db);
	}

}
