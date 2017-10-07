package mad.friend.view;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;

import java.sql.Time;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import hinaskye.assignment1.R;
import mad.friend.controller.receiver.NotificationReceiver;
import mad.friend.model.Friend;
import mad.friend.model.Meeting;
import mad.friend.model.WalkingData;
import mad.friend.model.WalkingDataModel;
import mad.friend.view.model.FriendTrackerNotification;
import util.FriendTrackerUtil;

/**
 * Created by Hinaskye on 5/10/2017.
 */

public class SuggestNowNotification extends FriendTrackerNotification {

    WalkingDataModel walkingDataModel = WalkingDataModel.getInstance();
    WalkingData currentData;

    public SuggestNowNotification(Context caller, int id)
    {
        super(caller, id);
        if(walkingDataModel.currentIndex<walkingDataModel.getListSize())
        {
            currentData = walkingDataModel.getWalkingList().get(walkingDataModel.currentIndex);
        }
    }

    /* Returns the Notification object a new suggested meeting */
    public Notification getNotification()
    {
        // Yes
        Intent intent = new Intent(current, NotificationReceiver.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(NotificationReceiver.NOTIFICATION_ID, id);
        intent.setAction("CREATE_SUGGESTED_MEETING");
        // Meeting object
        Meeting meeting = new Meeting();
        meeting.setId(FriendTrackerUtil.uniqueId(4));
        meeting.setTitle(String.format(Locale.getDefault(), "Meeting with %s", currentData.getFriend().getName()));
        meeting.setDate(new Date());
        meeting.setLatitude(currentData.getLatitude());
        meeting.setLongitude(currentData.getLongitude());
        meeting.setStartTime(new Time(Calendar.getInstance().getTimeInMillis() + currentData.maxWalkingTime()*60*1000));
        meeting.setEndTime(new Time(Calendar.getInstance().getTimeInMillis() + currentData.maxWalkingTime()*60*1000 + 10*60*1000));
        meeting.addFriend(currentData.getFriend());
        intent.putExtra("meeting", meeting);
        // pending intent for creating new meeting
        PendingIntent createMeetingIntent = PendingIntent.getBroadcast(current,
                FriendTrackerUtil.CREATE_SUGGESTTED_MEETING, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        // No (suggest another meeting with next friend in list)
        intent.setAction("SUGGEST_NOW_NOTIFICATION");
        PendingIntent suggestIntent = PendingIntent.getBroadcast(current,
                FriendTrackerUtil.SUGGEST_NOW_NOTIFICATION, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        // Dismiss
        intent.setAction("DISMISS_NOTIFICATION");
        PendingIntent dismissIntent = PendingIntent.getBroadcast(current,
                FriendTrackerUtil.SUGGEST_NOW_NOTIFICATION_DISMISS, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(current);
        builder.setContentTitle("Friend close by! ")
                .setSmallIcon(R.drawable.ic_notification_icon)
                .addAction(R.drawable.ic_yes, "yes", createMeetingIntent)
                .addAction(R.drawable.ic_no, "no", suggestIntent)
                .addAction(R.drawable.ic_dismiss, "dismiss", dismissIntent)
                .setAutoCancel(true);
        if(currentData != null)
        {
            builder.setContentText(String.format(Locale.getDefault(), "Meet with %s in %d mins?",
                    currentData.getFriend().getName(), currentData.maxWalkingTime()));
        }
        return builder.build();
    }
}
