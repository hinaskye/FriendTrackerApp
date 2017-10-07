package mad.friend.controller;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import mad.friend.view.LocationMapsActivity;

/**
 * Created by Hinaskye on 7/10/2017.
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

    @Override
    public void onClick(View v) {
        Intent mapIntent = new Intent(current, LocationMapsActivity.class);
        String[] tokens = location.replace("(","").replace(")","").split(",");

        latitude = Double.parseDouble(tokens[0]);
        longitude = Double.parseDouble(tokens[1]);
        mapIntent.putExtra("latitude", latitude);
        mapIntent.putExtra("longitude", longitude);
        current.startActivity(mapIntent);
    }
}
