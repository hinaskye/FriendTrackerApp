package mad.friend.controller.meeting;

import android.app.Activity;
import android.content.Intent;
import android.view.MenuItem;

import mad.friend.view.MeetingListActivity;

/**
 * Display Meeting Listener from menu item
 * When menu item is clicked, the meeting list activity will start
 */
public class DisplayMeetingListListener implements MenuItem.OnMenuItemClickListener {

    private Activity current;

    public DisplayMeetingListListener(Activity caller)
    {
        current = caller;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        Intent displayMeetingListIntent = new Intent(current, MeetingListActivity.class);
        current.startActivity(displayMeetingListIntent);
        return true;
    }
}
