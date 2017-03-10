/* This Source Code Form is subject to the terms of the Mozilla Public
 *  * License, v. 2.0. If a copy of the MPL was not distributed with this
 *   * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package com.mbapps.alibi.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseOpenHelper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "alibi.db";
	private static final int DATABASE_VERSION = 1;

	public static final String TRACKINGS_TABLE_NAME = "trackings";
	public static final String TRACKINGS_KEY_TIME_START = "time_start";
	public static final String TRACKINGS_KEY_TIME_END = "time_end";
	public static final String TRACKINGS_KEY_LAT_START = "lat_start";
	public static final String TRACKINGS_KEY_LONG_START = "long_start";
	public static final String TRACKINGS_KEY_LAT_END = "lat_end";
	public static final String TRACKINGS_KEY_LONG_END = "long_end";

	private static final String TRACKINGS_TABLE_CREATE =
			"CREATE TABLE " + TRACKINGS_TABLE_NAME + "(" +
				TRACKINGS_KEY_TIME_START  + " DATETIME," +
				TRACKINGS_KEY_TIME_END    + " DATETIME," +
				TRACKINGS_KEY_LAT_START   + " FLOAT,"    +
				TRACKINGS_KEY_LONG_START  + " FLOAT,"    +
				TRACKINGS_KEY_LAT_END     + " FLOAT,"    +
				TRACKINGS_KEY_LONG_END    + " FLOAT"     +
			")";

	public DatabaseOpenHelper(Context context) {
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
