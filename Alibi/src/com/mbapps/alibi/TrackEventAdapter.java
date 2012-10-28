package com.mbapps.alibi;

import java.util.List;

import com.mbapps.alibi.database.TrackEvent;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class TrackEventAdapter extends ArrayAdapter<TrackEvent> {
	private Context context;
	private List<TrackEvent> data;
	
	public TrackEventAdapter(Context context, int layoutResourceId, List<TrackEvent> data) {
		super(context, layoutResourceId, data);
		
		this.context = context;
		this.data = data;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		if(v == null) {
			// Inflate the layout
			LayoutInflater inflater = LayoutInflater.from(this.context);
			v = inflater.inflate(R.layout.trackeventlist_item, null);
		}
		
		TrackEvent trackEvent = data.get(position);
		if(trackEvent != null) {
			TextView text = (TextView) v.findViewById(R.id.trackeventlist_item_text);
			text.setText(trackEvent.toString());
			
			switch(trackEvent.getType()) {
				case POINT:
					v.setBackgroundColor(Color.GRAY);
					break;
				case STAY:
					v.setBackgroundColor(Color.GREEN);
					break;
				case TRAVEL:
					v.setBackgroundColor(Color.BLUE);
					break;
			}
		}
		
		return v;
	}
}
