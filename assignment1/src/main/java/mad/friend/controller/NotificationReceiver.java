package mad.friend.controller;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by Hinaskye on 5/10/2017.
 */

public class NotificationReceiver extends BroadcastReceiver {

    public static final String NOTIFICATION_ID = "notification_id";
    public static final String NOTIFICATION = "notification";

    private String LOG_TAG = this.getClass().getName();

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        int id = intent.getIntExtra(NOTIFICATION_ID, 0);

        if(intent.getAction() == "NOTIFY_OF_NOTIFICATION")
        {
            Log.i(LOG_TAG, "Received notify of notification, displaying notification");
            Notification notification = intent.getParcelableExtra(NOTIFICATION);
            notificationManager.notify(id, notification);
        }
        else if(intent.getAction() == "REPEAT_NOTIFICATION")
        {
            Log.i(LOG_TAG, "Received repeat notification event");
            Notification notification = intent.getParcelableExtra(NOTIFICATION);
            notificationManager.notify(id, notification);
        }
        else if(intent.getAction() == "DISMISS_NOTIFICATION")
        {
            Log.i(LOG_TAG, "Received dismiss notification event");
            notificationManager.cancel(id);
        }
    }
}
