package mad.friend.controller;

import android.app.Activity;
import android.content.Intent;
import android.test.ActivityTestCase;
import android.view.View;
import android.widget.AdapterView;

import mad.friend.model.Friend;
import mad.friend.model.FriendModel;
import mad.friend.view.AddFriendToMeetingActivity;

/**
 * Created by Hinaskye on 18/09/2017.
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
