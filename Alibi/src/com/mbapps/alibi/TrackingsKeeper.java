package com.mbapps.alibi;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class TrackingsKeeper {
	private SQLiteDatabase mDatabase;
	
	public TrackingsKeeper(Context context) {
		TrackingsOpenHelper openHelper = new TrackingsOpenHelper(context);
		mDatabase = openHelper.getWritableDatabase();
	}
	
	public void addTracking(long timestamp, double latitude, double longitude)
	{
		ContentValues contentValues = new ContentValues();
		contentValues.put(TrackingsOpenHelper.TRACKINGS_KEY_TIME, timestamp);
		contentValues.put(TrackingsOpenHelper.TRACKINGS_KEY_LAT,  latitude);
		contentValues.put(TrackingsOpenHelper.TRACKINGS_KEY_LONG, longitude);
		
		mDatabase.insert(TrackingsOpenHelper.TRACKINGS_TABLE_NAME, null, contentValues);
	}
}
