/* This Source Code Form is subject to the terms of the Mozilla Public
 *  * License, v. 2.0. If a copy of the MPL was not distributed with this
 *   * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package com.mbapps.alibi.database;

import android.location.Location;

/**
 * A tracking represents one location update
 * @author Milan
 *
 */
public class Tracking {
	public long timestamp;
	public Location location;

	public Tracking(long timestamp, Location location) {
		this.timestamp = timestamp;
		this.location = location;
	}
}
