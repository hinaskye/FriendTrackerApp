package mad.friend.view.model;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.util.Log;

import java.util.Calendar;

import mad.friend.controller.NetworkChangeReceiver;
import mad.friend.controller.NotificationReceiver;
import mad.friend.model.Friend;
import util.FriendTrackerUtil;

/**
 * Any notification from friend tracker should extend this class
 * Provides method to scheduleNotification
 */
public abstract class FriendTrackerNotification {

    public Activity current;
    public int id;

    private String LOG_TAG = this.getClass().getName();

    public FriendTrackerNotification(Activity caller, int id)
    {
        current = caller;
        this.id =  id;
    }

    // schedules an event after x milliseconds
    public void scheduleNotification(Notification notification, int millis)
    {
        // Sends an intent to notification receiver class to deal with
        Intent notificationIntent = new Intent(current, NotificationReceiver.class);
        notificationIntent.setAction("NOTIFY_OF_NOTIFICATION");
        notificationIntent.putExtra(NotificationReceiver.NOTIFICATION_ID,id);
        notificationIntent.putExtra(NotificationReceiver.NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(current,
                FriendTrackerUtil.NOTIFY_OF_NOTIFICATION, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        long futureTimeInMilli = SystemClock.elapsedRealtime() + millis;
        AlarmManager alarmManager = (AlarmManager)current.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, futureTimeInMilli, pendingIntent);
        /**/
    }

    // repeats a notification every x minutes
    public void repeatNotification(Notification notification, double minutes)
    {
        long milliseconds = (long) (minutes * 60 * 1000);
        Log.i(LOG_TAG, String.format("minutes:%f\tmilliseconds:%d", minutes, milliseconds));
        Intent repeatIntent = new Intent(current, NotificationReceiver.class);
        repeatIntent.setAction("REPEAT_NOTIFICATION");
        repeatIntent.putExtra(NotificationReceiver.NOTIFICATION_ID,id);
        repeatIntent.putExtra(NotificationReceiver.NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(current,
                FriendTrackerUtil.REPEAT_NOTIFICATION, repeatIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager)current.getSystemService(Context.ALARM_SERVICE);
        // According to https://stackoverflow.com/questions/34585381/setrepeating-of-alarmmanager-repeats-after-1-minute-no-matter-what-the-time-is
        // Alarms have a minimum of 1 minute in between repeats as of API 22
        alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                SystemClock.elapsedRealtime(), milliseconds, pendingIntent);
    }
}
