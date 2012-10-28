package com.mbapps.alibi.database;


import android.location.Location;
import android.location.LocationManager;

/**
 * A TrackEvent represents one row from the database.
 * @author Milan
 *
 */
public class TrackEvent {
	private long timeStart;
	private long timeEnd;
	private Location locStart;
	private Location locEnd;
	
	public TrackEvent(long timeStart, long timeEnd, Location locStart, Location locEnd) {
		this.timeStart = timeStart;
		this.timeEnd = timeEnd;
		this.locStart = locStart;
		this.locEnd = locEnd;
	}
	
	public TrackEvent(long timeStart, long timeEnd, double latStart, double longStart, double latEnd, double longEnd) {
		this.timeStart = timeStart;
		this.timeEnd = timeEnd;
		this.locStart = new Location(LocationManager.GPS_PROVIDER);
		this.locStart.setLatitude(latStart);
		this.locStart.setLongitude(longStart);
		this.locEnd = new Location(LocationManager.GPS_PROVIDER);
		this.locEnd.setLatitude(latEnd);
		this.locEnd.setLongitude(longEnd);
	}
	
	public TrackEventType getType() {
		if(this.timeStart == this.timeEnd)
			return TrackEventType.POINT;
		else if(this.locStart.distanceTo(this.locEnd) == 0)
			return TrackEventType.STAY;
		else
			return TrackEventType.TRAVEL;
	}
	
	@Override
	public String toString() {
		/*return this.locStart.getLatitude() + " " + 
		       this.locStart.getLongitude() + " / " + 
		       this.locEnd.getLatitude() + " " + 
		       this.locEnd.getLongitude();*/
		return this.timeStart + " " + this.timeEnd;
	}
}
