package mad.friend.controller;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import mad.friend.model.Meeting;
import mad.friend.model.MeetingModel;
import mad.friend.view.AddMeetingActivity;

/**
 * Add Meeting Listener, creates an add meeting intent and starts the activity
 */
public class AddMeetingListener implements View.OnClickListener {

    private Activity current;

    public AddMeetingListener(Activity caller)
    {
        current = caller;
    }

    @Override
    public void onClick(View v) {
        Intent addMeetingIntent = new Intent(current, AddMeetingActivity.class);
        current.startActivity(addMeetingIntent);
    }
}
