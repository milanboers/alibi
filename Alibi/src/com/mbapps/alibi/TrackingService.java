package com.mbapps.alibi;

import java.util.Calendar;

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
	
	private NotificationManager mNotificationManager;
	private LocationManager mLocationManager;

	public TrackingService() {
		super("TrackingService");
		Log.v(TAG, "constructed");
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		
		mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		mLocationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		
		// Destroy notification
		mNotificationManager.cancel(NOTIFICATION_TRACKING);
		
		// Tell user tracking has stopped
		NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
			.setSmallIcon(R.drawable.ic_action_search)
			.setContentTitle("Tracking stopped")
			.setContentText("Tracking has stopped");
		mNotificationManager.notify(NOTIFICATION_TRACKING_STOPPED, builder.build());
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
		mNotificationManager.notify(NOTIFICATION_TRACKING, builder.build());
		
		TrackingsKeeper tk = new TrackingsKeeper(this);
		
		Log.v(TAG, "Started tracking service");
		long endTime = System.currentTimeMillis() + 30*1000;
		while(System.currentTimeMillis() < endTime) {
			synchronized(this) {
				try {
					wait(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		        Log.v(TAG, "hi");
		        Location lastKnownLocation = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
		        Log.v(TAG, lastKnownLocation.getLatitude() + " " + lastKnownLocation.getLongitude());
		        
		        tk.addTracking(Calendar.getInstance().getTime().getTime(), lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude());
			}
		}
	}

}
