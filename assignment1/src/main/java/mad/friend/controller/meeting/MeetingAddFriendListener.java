package mad.friend.controller.meeting;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import mad.friend.view.AddFriendToMeetingActivity;
import util.FriendTrackerUtil;

/**
 * Meeting, add friend listener
 * When button clicked will start an add friend to meeting intent and start activty for result
 * expects a friend object back
 */
public class MeetingAddFriendListener implements View.OnClickListener {

    Activity current;

    public MeetingAddFriendListener(Activity caller)
    {
        current = caller;
    }

    @Override
    public void onClick(View v) {
        Intent addFriendIntent = new Intent(current, AddFriendToMeetingActivity.class);
        current.startActivityForResult(addFriendIntent, FriendTrackerUtil.MEETING_ADD_FRIEND);
    }
}
