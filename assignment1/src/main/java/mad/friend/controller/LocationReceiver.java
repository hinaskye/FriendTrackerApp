package mad.friend.controller;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by Hinaskye on 6/10/2017.
 */

public class LocationReceiver extends BroadcastReceiver {

    private String LOG_TAG = this.getClass().getName();

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(LOG_TAG, "onReceive()");
        if(intent.getAction() == "LOCATION_CHANGED")
        {
            Log.i(LOG_TAG, "LOCATION_CHANGED action received");
        }
        else if(intent.getAction() == "SUGGEST_NOW")
        {

        }
    }
}
