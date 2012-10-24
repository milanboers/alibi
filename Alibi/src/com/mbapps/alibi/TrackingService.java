package com.mbapps.alibi;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.util.Log;

public class TrackingService extends IntentService {
	private static final String TAG = "TrackingService";

	public TrackingService() {
		super("TrackingService");
		Log.v(TAG, "constructed");
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onHandleIntent(Intent arg0) {
		Log.v(TAG, "Started tracking service");
		long endTime = System.currentTimeMillis() + 5*1000;
		while(System.currentTimeMillis() < endTime)
		{
			synchronized(this)
			{
				try {
					wait(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		        Log.v(TAG, "hi");
		        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
		        Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
		        Log.v(TAG, lastKnownLocation.getLatitude() + " " + lastKnownLocation.getLongitude());
			}
		}
	}

}
