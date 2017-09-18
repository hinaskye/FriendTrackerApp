package mad.friend.controller.meeting;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import mad.friend.model.Friend;
import mad.friend.model.FriendModel;

/**
 * When a friend is selected, this will return the friend object and finish the current activity
 */
public class AddFriendToMeetingListener implements AdapterView.OnItemClickListener {

    Activity current;

    public AddFriendToMeetingListener(Activity caller)
    {
        current = caller;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Friend friendSelected = FriendModel.getInstance().getFriends().get(position);
        Intent returnIntent = new Intent();
        returnIntent.putExtra("friend", friendSelected);
        current.setResult(Activity.RESULT_OK, returnIntent);
        current.finish();
    }
}
