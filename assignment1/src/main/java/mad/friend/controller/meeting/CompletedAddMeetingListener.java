package mad.friend.controller.meeting;

import android.app.Activity;
import android.view.View;

import mad.friend.model.Meeting;

/**
 * Complete Add Meeting Listener
 * Once the add meeting button is clicked, will add the meeting to our meeting model
 */
public class CompletedAddMeetingListener implements View.OnClickListener {

    Activity current;
    Meeting meeting;

    public CompletedAddMeetingListener(Activity caller, Meeting meeting)
    {
        current = caller;
        this.meeting = meeting;
    }

    @Override
    public void onClick(View v) {
        // should add meeting to model when done
    }
}
