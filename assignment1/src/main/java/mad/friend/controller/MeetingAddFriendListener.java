package mad.friend.controller;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import mad.friend.view.AddFriendToMeetingActivity;
import util.FriendTrackerUtil;

/**
 * Created by Hinaskye on 18/09/2017.
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
