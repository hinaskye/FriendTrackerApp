package mad.friend.view;

import android.app.Activity;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import java.util.List;

import hinaskye.assignment1.R;
import mad.friend.controller.NotificationReceiver;
import mad.friend.model.Meeting;
import mad.friend.view.model.FriendTrackerNotification;
import util.FriendTrackerUtil;

/**
 * Created by Hinaskye on 5/10/2017.
 */

public class UpcomingMeetingNotification extends FriendTrackerNotification {

    public UpcomingMeetingNotification(Activity caller, int id)
    {
        super(caller, id);
    }

    /* Returns the Notification object containing the meeting data */
    public Notification getNotification(Meeting meeting)
    {
        /* TODO need to add intents*/
        Intent intent = new Intent(current, NotificationReceiver.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction("DISMISS_NOTIFICATION");
        intent.putExtra(NotificationReceiver.NOTIFICATION_ID, id);
        PendingIntent dismissIntent = PendingIntent.getBroadcast(current, FriendTrackerUtil.UPCOMING_MEETING_DISMISS,
                intent, PendingIntent.FLAG_CANCEL_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(current);
        builder.setContentTitle("Upcoming meeting")
                .setContentText(String.format("%s at %s", meeting.getTitle(), meeting.getStartTime().toString()))
                .setSmallIcon(R.drawable.ic_notification_icon)
                .addAction(R.drawable.ic_cancel, "cancel", dismissIntent)
                .addAction(R.drawable.ic_remind, "remind", dismissIntent)
                .addAction(R.drawable.ic_dismiss, "dismiss", dismissIntent)
                .setAutoCancel(true);

        return builder.build();
    }
}
