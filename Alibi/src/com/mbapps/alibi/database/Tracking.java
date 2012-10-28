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
