package mad.friend.controller.meeting;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;

import mad.friend.controller.DistanceCalcThread;
import util.FriendTrackerUtil;

/**
 * SuggestNowListener
 * Creates the thread that calculates the distance and sends a broadcast
 * to display a suggest now notification
 */
public class SuggestNowListener implements View.OnClickListener {

    Activity current;

    public SuggestNowListener(Activity caller)
    {
        current = caller;
    }

    @Override
    public void onClick(View v) {
        int locationPermission = ContextCompat.checkSelfPermission(current, Manifest.permission.ACCESS_FINE_LOCATION);
        if(locationPermission != PackageManager.PERMISSION_GRANTED)
        {
            if(ActivityCompat.shouldShowRequestPermissionRationale(current, Manifest.permission.ACCESS_FINE_LOCATION))
            {
                new AlertDialog.Builder(current)
                        .setTitle("Need Location Permission")
                        .setMessage("Suggest now requires location permissions to look for nearby friends")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                ActivityCompat.requestPermissions(current, new String[]
                                        {Manifest.permission.ACCESS_FINE_LOCATION}, FriendTrackerUtil.LOCATION_PERMISSION);
                            }
                        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                dialog.cancel();
                            }
                        }).show();
            }
            else
                {
                ActivityCompat.requestPermissions(current, new String[]
                        {Manifest.permission.ACCESS_FINE_LOCATION}, FriendTrackerUtil.LOCATION_PERMISSION);
            }
        }
        Thread distCalcThread = new DistanceCalcThread(current);
        distCalcThread.start();
    }
}
