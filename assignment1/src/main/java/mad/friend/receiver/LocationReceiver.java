package mad.friend.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import mad.friend.controller.DistanceCalcThread;

/**
 * LocationReceiver
 * Receives location has change updates and suggest meeting now if network has changed
 */
public class LocationReceiver extends BroadcastReceiver {

    private String LOG_TAG = this.getClass().getName();

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(LOG_TAG, "onReceive()");
        if(intent.getAction() == "LOCATION_CHANGED")
        {
            Log.i(LOG_TAG, "LOCATION_CHANGED");
            Thread distCalcThread = new DistanceCalcThread(context);
            distCalcThread.start();
        }
        else if(intent.getAction() == "SUGGEST_NOW")
        {
            Log.i(LOG_TAG, "SUGGEST_NOW");
            Thread distCalcThread = new DistanceCalcThread(context);
            distCalcThread.start();
        }
    }
}
