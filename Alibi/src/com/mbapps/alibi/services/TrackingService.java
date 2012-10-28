package com.mbapps.alibi.services;

import java.util.Calendar;

import com.mbapps.alibi.R;
import com.mbapps.alibi.database.Tracking;
import com.mbapps.alibi.database.Database;

import android.app.IntentService;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

public class TrackingService extends IntentService {
	private static final String TAG = "TrackingService";
	
	private static final int NOTIFICATION_TRACKING = 0;
	private static final int NOTIFICATION_TRACKING_STOPPED = 1;
	
	private NotificationManager notificationManager;
	private LocationManager locationManager;
	
	private Database trackingsKeeper;

	public TrackingService() {
		super("TrackingService");
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		
		this.trackingsKeeper = Database.getInstance(this);
		this.notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		this.locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		
		// Destroy notification
		this.notificationManager.cancel(NOTIFICATION_TRACKING);
		
		// Tell user tracking has stopped
		NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
			.setSmallIcon(R.drawable.ic_action_search)
			.setContentTitle("Tracking stopped")
			.setContentText("Tracking has stopped");
		this.notificationManager.notify(NOTIFICATION_TRACKING_STOPPED, builder.build());
	}
	
	@Override
	protected void onHandleIntent(Intent arg0) {
		Log.v(TAG, "starting service");
		// Tell user tracking has started
		NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
			.setSmallIcon(R.drawable.ic_action_search)
			.setContentTitle("Tracking started")
			.setContentText("Tracking was started")
			.setOngoing(true);
		this.notificationManager.notify(NOTIFICATION_TRACKING, builder.build());
		
		Log.v(TAG, "Started tracking service");
		long endTime = System.currentTimeMillis() + 10*1000;
		while(System.currentTimeMillis() < endTime) {
			synchronized(this) {
				try {
					wait(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				this.addTracking();
			}
		}
	}
	
	/**
	 * Gets the latest known location and adds it to the database
	 */
	private void addTracking() {
		Location lastKnownLocation = this.locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if(lastKnownLocation != null) {
        	Log.v(TAG, lastKnownLocation.getLatitude() + " " + lastKnownLocation.getLongitude());
        	this.trackingsKeeper.addTracking(
        			new Tracking(Calendar.getInstance().getTimeInMillis(), lastKnownLocation)
        	);
        }
	}

}
