package com.mbapps.alibi.activities;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import com.mbapps.alibi.R;
import com.mbapps.alibi.TrackEventAdapter;
import com.mbapps.alibi.UpdateListTask;
import com.mbapps.alibi.database.TrackEvent;
import com.mbapps.alibi.database.Database;
import com.mbapps.alibi.services.TrackingService;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.widget.ListView;

public class MainActivity extends Activity implements Observer {
	private static final String TAG = "MainActivity";
	
	private final List<TrackEvent> trackEvents = new ArrayList<TrackEvent>();
	private TrackEventAdapter trackEventAdapter;
	
	private Database trackingsKeeper;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        Log.v(TAG, "oncreate");
        
		this.trackingsKeeper = Database.getInstance(this);
        
        this.startService(new Intent(this, TrackingService.class));
        
        // Setup the list
        ListView listView = (ListView) findViewById(R.id.track_events_list);
        this.trackEventAdapter = new TrackEventAdapter(
        		this,
        		R.layout.trackeventlist_item, 
        		this.trackEvents);
        listView.setAdapter(this.trackEventAdapter);
        
        // Initialize the list
        this.updateList();
        
        // Register to database changes
        this.trackingsKeeper.addObserver(this);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    public void update(Observable observable, Object data) {
    	this.updateList();
    }
    
    /**
     * Updates the listview with the database info
     */
    private void updateList() {
    	new UpdateListTask(this).execute(this.trackEvents, this.trackEventAdapter);
    }
}
