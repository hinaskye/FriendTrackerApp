package mad.friend.controller;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import mad.friend.view.LocationMapsActivity;

/**
 * DisplayMapsListener
 * Displayed a google map based on location in the form of (lat,long)
 * or based on latitude and longitude values
 */
public class DisplayMapsListener implements View.OnClickListener{

    Context current;
    String location;
    double latitude, longitude;

    public DisplayMapsListener(Context caller, String location)
    {
        current = caller;
        this.location = location;
    }

    public DisplayMapsListener(Context caller, double latitude, double longitude)
    {
        current = caller;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Override
    public void onClick(View v) {
        Intent mapIntent = new Intent(current, LocationMapsActivity.class);
        // (lat,long) parse just the lat or long details
        if(location != null)
        {
            String[] tokens = location.replace("(","").replace(")","").split(",");
            latitude = Double.parseDouble(tokens[0]);
            longitude = Double.parseDouble(tokens[1]);
        }

        mapIntent.putExtra("latitude", latitude);
        mapIntent.putExtra("longitude", longitude);
        current.startActivity(mapIntent);
    }
}
