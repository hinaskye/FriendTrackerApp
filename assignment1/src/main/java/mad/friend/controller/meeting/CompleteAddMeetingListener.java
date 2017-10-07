package mad.friend.controller.meeting;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import mad.friend.controller.NotificationReceiver;
import mad.friend.model.Meeting;
import mad.friend.model.MeetingModel;
import mad.friend.model.database.DBMeetingHelper;
import util.FriendTrackerUtil;

/**
 * Complete Add Meeting Listener
 * Once the add meeting button is clicked, will add the meeting to our meeting model
 */
public class CompleteAddMeetingListener implements View.OnClickListener {

    Activity current;
    Meeting meeting;

    public CompleteAddMeetingListener(Activity caller, Meeting meeting)
    {
        current = caller;
        this.meeting = meeting;
    }

    @Override
    public void onClick(View v) {
        if(meeting!=null)
        {
            // Insert new meeting into model and DB
            MeetingModel.getInstance().getMeetings().add(meeting);
            DBMeetingHelper dbMeeting = new DBMeetingHelper(current);
            dbMeeting.insertMeeting(meeting);
            // Send an intent to set alarm for meeting time
            Intent alarmIntent = new Intent(current, NotificationReceiver.class);
            alarmIntent.setAction("ALARM_FOR_MEETING");
            alarmIntent.putExtra("id", FriendTrackerUtil.ALARM_FOR_MEETING);
            alarmIntent.putExtra("meeting", meeting);
            current.sendBroadcast(alarmIntent);
            current.finish();
        }
        else
        {
            System.err.println("CompleteAddMeetingListener: Unable to add meeting, meeting is null");
        }
    }
}
