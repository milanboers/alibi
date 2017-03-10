/* This Source Code Form is subject to the terms of the Mozilla Public
 *  * License, v. 2.0. If a copy of the MPL was not distributed with this
 *   * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package com.mbapps.alibi.database;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class Database extends Observable {
	private static final String TAG = "TrackingsKeeper";

	private SQLiteDatabase database;

	private Tracking currentTracking;
	private boolean traveling = false;

	private static Database instance;

	private Database(Context context) {
		DatabaseOpenHelper openHelper = new DatabaseOpenHelper(context);
		this.database = openHelper.getWritableDatabase();
	}

	public static Database getInstance(Context context) {
		if(Database.instance == null)
			Database.instance = new Database(context);
		return Database.instance;
	}

	/**
	 * Get track events from the database.
	 * @return
	 */
	public List<TrackEvent> getTrackEvents()
	{
		List<TrackEvent> fb = new ArrayList<TrackEvent>();

		Cursor cursor = this.database.query(
				DatabaseOpenHelper.TRACKINGS_TABLE_NAME,
				new String[] {
						DatabaseOpenHelper.TRACKINGS_KEY_TIME_START,
						DatabaseOpenHelper.TRACKINGS_KEY_TIME_END,
						DatabaseOpenHelper.TRACKINGS_KEY_LAT_START,
						DatabaseOpenHelper.TRACKINGS_KEY_LONG_START,
						DatabaseOpenHelper.TRACKINGS_KEY_LAT_END,
						DatabaseOpenHelper.TRACKINGS_KEY_LONG_END
				},
				null,
				null,
				null,
				null,
				DatabaseOpenHelper.TRACKINGS_KEY_TIME_START);


		// Loop over all rows
		cursor.moveToFirst();
		while(!cursor.isAfterLast())
		{
			long timeStart = cursor.getLong(0);
			long timeEnd = cursor.getLong(1);
			double latStart = cursor.getDouble(2);
			double longStart = cursor.getDouble(3);
			double latEnd = cursor.getDouble(4);
			double longEnd = cursor.getDouble(5);

			Log.v(TAG, timeStart + " " + timeEnd);

			fb.add(new TrackEvent(timeStart, timeEnd, latStart, longStart, latEnd, longEnd));

			cursor.moveToNext();
		}

		return fb;
	}

	public void addTracking(Tracking tracking)
	{
		if(this.currentTracking == null || tracking.location.distanceTo(this.currentTracking.location) > 1)
		{
			// Location is different than previous one
			if(this.traveling)
			{
				// Update the location and the timestamp
				ContentValues contentValues = new ContentValues();
				contentValues.put(DatabaseOpenHelper.TRACKINGS_KEY_LAT_END, tracking.location.getLatitude());
				contentValues.put(DatabaseOpenHelper.TRACKINGS_KEY_LONG_END, tracking.location.getLongitude());
				contentValues.put(DatabaseOpenHelper.TRACKINGS_KEY_TIME_END, tracking.timestamp);

				this.database.update(
						DatabaseOpenHelper.TRACKINGS_TABLE_NAME,
						contentValues,
						DatabaseOpenHelper.TRACKINGS_KEY_TIME_START + " = " + this.currentTracking.timestamp,
						null);
			}
			else
			{
				// Traveling has now been initiated
				// This tracking has another location than the previous one, add it to the db
				ContentValues contentValues = new ContentValues();
				contentValues.put(DatabaseOpenHelper.TRACKINGS_KEY_TIME_START, tracking.timestamp);
				contentValues.put(DatabaseOpenHelper.TRACKINGS_KEY_TIME_END, tracking.timestamp);
				contentValues.put(DatabaseOpenHelper.TRACKINGS_KEY_LAT_START,  tracking.location.getLatitude());
				contentValues.put(DatabaseOpenHelper.TRACKINGS_KEY_LONG_START, tracking.location.getLongitude());
				contentValues.put(DatabaseOpenHelper.TRACKINGS_KEY_LAT_END,  tracking.location.getLatitude());
				contentValues.put(DatabaseOpenHelper.TRACKINGS_KEY_LONG_END, tracking.location.getLongitude());

				this.database.insert(DatabaseOpenHelper.TRACKINGS_TABLE_NAME, null, contentValues);

				// Is now travelling
				this.traveling = true;
				this.currentTracking = tracking;
			}
		}
		else
		{
			Log.v(TAG, "Location still the same. Just updating.");
			// Not travelling anymore
			this.traveling = false;

			// Just update the last tracking with the timestamp, don't add new one, don't update location
			ContentValues contentValues = new ContentValues();
			contentValues.put(DatabaseOpenHelper.TRACKINGS_KEY_TIME_END, tracking.timestamp);

			this.database.update(
					DatabaseOpenHelper.TRACKINGS_TABLE_NAME,
					contentValues,
					DatabaseOpenHelper.TRACKINGS_KEY_TIME_START + " = " + this.currentTracking.timestamp,
					null);
		}

		this.setChanged();
		this.notifyObservers();
	}
}
