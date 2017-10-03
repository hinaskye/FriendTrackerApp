package mad.friend.controller.meeting;

import android.app.Activity;
import android.view.View;
import android.widget.Toast;

import mad.friend.model.Meeting;
import mad.friend.model.MeetingModel;

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
            MeetingModel.getInstance().getMeetings().add(meeting);
            current.finish();
        }
        else
        {
            System.err.println("Unable to add meeting, meeting is null");
        }
    }
}
