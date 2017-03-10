/* This Source Code Form is subject to the terms of the Mozilla Public
 *  * License, v. 2.0. If a copy of the MPL was not distributed with this
 *   * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package com.mbapps.alibi;

import java.util.List;

import com.mbapps.alibi.database.TrackEvent;
import com.mbapps.alibi.database.Database;

import android.content.Context;
import android.os.AsyncTask;

/**
 * Task to update the list of the listview with new data from the database,
 * and then updates the list.
 * @author Milan
 *
 */
public class UpdateListTask extends AsyncTask<Object, Void, TrackEventAdapter> {
	Database trackingsKeeper;

	public UpdateListTask(Context context) {
		this.trackingsKeeper = Database.getInstance(context);
	}

	@Override
	protected TrackEventAdapter doInBackground(Object... args) {
		@SuppressWarnings("unchecked")
		List<TrackEvent> list = (List<TrackEvent>) args[0];

		// Clear the old list
		list.clear();
		// Add database rows
		list.addAll(this.trackingsKeeper.getTrackEvents());

		return (TrackEventAdapter) args[1];
	}

	@Override
	protected void onPostExecute(TrackEventAdapter trackEventAdapter) {
		trackEventAdapter.notifyDataSetChanged();
	}
}
