package mad.friend.controller.meeting;

import android.app.Activity;
import android.util.Log;
import android.view.View;

import mad.friend.model.Meeting;
import mad.friend.model.MeetingModel;
import mad.friend.model.database.DBMeetingHelper;
import mad.friend.view.EditMeetingActivity;

/**
 * DeleteMeetingListener
 * Deletes the specified meeting
 */
public class DeleteMeetingListener implements View.OnClickListener {
    private Activity current;
    private Meeting meeting;
    private String LOG_TAG = this.getClass().getName();
    public DeleteMeetingListener(Activity caller, Meeting meeting)
    {
        current = caller;
        this.meeting = meeting;
    }

    /**
     *  deletes meeting from model and DB
     */
    @Override
    public void onClick(View v) {
        Log.i(LOG_TAG, String.format("Meeting removed: %s", meeting.toString()));
        MeetingModel.getInstance().getMeetings().remove(meeting);
        DBMeetingHelper DBMeeting = new DBMeetingHelper(current);
        DBMeeting.deleteMeeting(meeting.getId());
        current.finish();
    }
}
