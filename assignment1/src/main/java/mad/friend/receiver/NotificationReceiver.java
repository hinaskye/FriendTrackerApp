package mad.friend.receiver;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import java.util.Locale;

import mad.friend.model.Meeting;
import mad.friend.model.MeetingModel;
import mad.friend.model.WalkingDataModel;
import mad.friend.model.database.DBMeetingHelper;
import mad.friend.view.RemindDialog;
import mad.friend.view.SuggestNowNotification;
import mad.friend.view.UpcomingMeetingNotification;
import mad.friend.view.model.FriendTrackerAlarmManager;
import util.FriendTrackerUtil;

/**
 * NotificationReceiver
 * Receives broadcast events that have to do with notification for FriendTracker app
 */
public class NotificationReceiver extends BroadcastReceiver {

    public static final String NOTIFICATION_ID = "notification_id";
    public static final String NOTIFICATION = "notification";

    private String LOG_TAG = this.getClass().getName();

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        int id = intent.getIntExtra(NOTIFICATION_ID, 0);

        // Suggest a new meeting notification
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
        }   // Display a notification
        else if(intent.getAction() == "NOTIFY_OF_NOTIFICATION")
        {
            Log.i(LOG_TAG, "Received notify of notification, displaying notification");
            Notification notification = intent.getParcelableExtra(NOTIFICATION);
            notificationManager.notify(id, notification);
        }   // Repeated notification event
        else if(intent.getAction() == "REPEAT_NOTIFICATION")
        {
            Log.i(LOG_TAG, "Received repeat notification event");
            Notification notification = intent.getParcelableExtra(NOTIFICATION);
            notificationManager.notify(id, notification); // display or update notification
        }   // Dismiss notification event
        else if(intent.getAction() == "DISMISS_NOTIFICATION")
        {
            Log.i(LOG_TAG, "Received dismiss notification event");
            notificationManager.cancel(id);
        }   // Set an Alarm for upcoming meeting
        else if(intent.getAction() == "ALARM_FOR_MEETING")
        {
            Log.i(LOG_TAG, "Received intent to schedule an alarm for upcoming meeting");
            Meeting meeting = (Meeting)intent.getSerializableExtra("meeting");
            System.err.println(meeting.toString());
            UpcomingMeetingNotification upcomingMeeting = new UpcomingMeetingNotification(context, FriendTrackerUtil.ALARM_FOR_MEETING);
            Notification notification = upcomingMeeting.getNotification(meeting);
            long millisBefore = meeting.getStartTime().getTime() - 5*60*1000; //5 minutes
            new FriendTrackerAlarmManager(context).scheduleNotification(notification, id, millisBefore);
        }   // Creates the suggested meeting if user has said yes on notification
        else if(intent.getAction() == "CREATE_SUGGESTED_MEETING")
        {
            Log.i(LOG_TAG, "User said yes to creating the suggested meeting");
            Meeting meeting = (Meeting) intent.getSerializableExtra("meeting");
            if(meeting != null)
            {
                MeetingModel.getInstance().addMeeting(meeting);
                Log.i(LOG_TAG, String.format(Locale.getDefault(),"Meeting added %s", meeting.toString()));
                Toast.makeText(context, "Added new Meeting", Toast.LENGTH_LONG).show();
                new DBMeetingHelper(context).insertMeeting(meeting); // Add to DB, need to change to thread

                // After meeting has been added, send an upcoming meeting alarm intent
                Intent alarmIntent = new Intent(context, NotificationReceiver.class);
                alarmIntent.setAction("ALARM_FOR_MEETING");
                alarmIntent.putExtra("meeting", meeting);
                context.sendBroadcast(alarmIntent);

            }
            notificationManager.cancel(id); // cancel notification or else will suggest for next friend
        }
        else if(intent.getAction() == "CANCEL_MEETING")
        {
            Log.i(LOG_TAG, "User said cancel upcoming meeting");
            Meeting meeting = (Meeting) intent.getSerializableExtra("meeting");
            System.err.println(meeting.toString());
            new DBMeetingHelper(context).deleteMeeting(meeting.getId());
            MeetingModel.getInstance().getMeetings().remove(meeting);
            Toast.makeText(context, "Removed upcoming Meeting", Toast.LENGTH_LONG).show();

        }
        else if(intent.getAction() == "REMIND_IN")
        {
            Log.i(LOG_TAG, "Remind user intent activated");
            Log.e(LOG_TAG, "Functionality current not working");
            /*
            RemindDialog remindDialog = new RemindDialog(context);
            remindDialog.setContents();
            remindDialog.show();*/
        }
    }
}
