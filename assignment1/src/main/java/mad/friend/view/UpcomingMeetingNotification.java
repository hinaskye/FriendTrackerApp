package mad.friend.view;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import hinaskye.assignment1.R;
import mad.friend.controller.receiver.NotificationReceiver;
import mad.friend.model.Meeting;
import util.FriendTrackerUtil;

/**
 * Upcoming meeting notification
 * Sets the content of the notification to an upcoming meeting
 */
public class UpcomingMeetingNotification {

    private Context current;
    private int id;

    public UpcomingMeetingNotification(Context caller, int id)
    {
        current = caller;
        this.id = id;
    }

    /* Returns the Notification object containing the meeting data */
    public Notification getNotification(Meeting meeting)
    {
        // Cancel
        Intent intent = new Intent(current, NotificationReceiver.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(NotificationReceiver.NOTIFICATION_ID, id);
        intent.setAction("CANCEL_MEETING");
        PendingIntent cancelIntent = PendingIntent.getBroadcast(current,
                FriendTrackerUtil.UPCOMING_MEETING_CANCEL, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        // Remind
        intent.setAction("REMIND_IN");
        PendingIntent remindIntent = PendingIntent.getBroadcast(current,
                FriendTrackerUtil.UPCOMING_MEETING_REMIND, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        // Dismiss
        intent.setAction("DISMISS_NOTIFICATION");
        PendingIntent dismissIntent = PendingIntent.getBroadcast(current, FriendTrackerUtil.UPCOMING_MEETING_DISMISS,
                intent, PendingIntent.FLAG_CANCEL_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(current);
        builder.setContentTitle("Upcoming meeting")
                .setContentText(String.format("%s at %s", meeting.getTitle(), meeting.getStartTime().toString()))
                .setSmallIcon(R.drawable.ic_notification_icon)
                .addAction(R.drawable.ic_cancel, "cancel", cancelIntent)
                .addAction(R.drawable.ic_remind, "remind", remindIntent)
                .addAction(R.drawable.ic_dismiss, "dismiss", dismissIntent)
                .setAutoCancel(true);

        return builder.build();
    }
}
