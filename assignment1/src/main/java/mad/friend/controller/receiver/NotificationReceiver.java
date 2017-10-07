package mad.friend.controller.receiver;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.widget.Toast;

import java.util.Locale;

import mad.friend.model.Meeting;
import mad.friend.model.MeetingModel;
import mad.friend.model.WalkingDataModel;
import mad.friend.model.database.DBMeetingHelper;
import mad.friend.view.SuggestNowNotification;
import mad.friend.view.UpcomingMeetingNotification;
import util.FriendTrackerUtil;

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

        if(intent.getAction() == "SUGGEST_NOW_NOTIFICATION")
        {
            Log.i(LOG_TAG, "Received suggest now notification");
            // If there is data
            if(!(WalkingDataModel.getInstance().getListSize() == WalkingDataModel.getInstance().currentIndex))
            {
                SuggestNowNotification suggestNowNotification =
                        new SuggestNowNotification(context, FriendTrackerUtil.SUGGEST_NOW_NOTIFICATON_ID);
                Notification notification = suggestNowNotification.getNotification();
                WalkingDataModel.getInstance().incrementIndex();
                notificationManager.notify(FriendTrackerUtil.SUGGEST_NOW_NOTIFICATON_ID, notification);
            }
            else
            {   // cancel notification if all friends have been suggested
                WalkingDataModel.getInstance().resetIndex();
                notificationManager.cancel(FriendTrackerUtil.SUGGEST_NOW_NOTIFICATON_ID);
            }
        }
        else if(intent.getAction() == "NOTIFY_OF_NOTIFICATION")
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
        else if(intent.getAction() == "ALARM_FOR_MEETING")
        {
            Log.i(LOG_TAG, "Received intent to schedule an alarm for upcoming meeting");
            Meeting meeting = (Meeting)intent.getSerializableExtra("meeting");
            UpcomingMeetingNotification notif = new UpcomingMeetingNotification(context, FriendTrackerUtil.ALARM_FOR_MEETING);
            System.err.println(meeting.toString());
            Notification notificaition = notif.getNotification(meeting);
            long millisBefore = meeting.getStartTime().getTime() - 5*60*1000; //5 minutes
            notif.scheduleDelayNotification(notificaition, millisBefore);
        }
        else if(intent.getAction() == "CREATE_SUGGESTED_MEETING")
        {
            Log.i(LOG_TAG, "User said yes to creating the suggested meeting");
            Meeting meeting = (Meeting) intent.getSerializableExtra("meeting");
            if(meeting != null)
            {
                MeetingModel.getInstance().addMeeting(meeting);
                Log.i(LOG_TAG, String.format(Locale.getDefault(),"Meeting added %s", meeting.toString()));
                Toast.makeText(context, "Added new Meeting", Toast.LENGTH_LONG).show();
            }
            new DBMeetingHelper(context).insertMeeting(meeting);
            notificationManager.cancel(id);
        }
    }

    public void suggestNowNotification(Context context)
    {
        SuggestNowNotification suggestNowNotification = new SuggestNowNotification(context, FriendTrackerUtil.SUGGEST_NOW_NOTIFICATON_ID);
    }
}
