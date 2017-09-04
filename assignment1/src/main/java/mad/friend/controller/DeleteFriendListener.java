package mad.friend.controller;

import android.app.Activity;
import android.view.View;
import android.widget.Toast;

import mad.friend.model.Friend;
import mad.friend.model.FriendModel;

/**
 * Delete friend listener
 * Deletes the friend from our model when view element is clicked
 */
public class DeleteFriendListener implements View.OnClickListener {

    private Activity current;
    private Friend friend;

    public DeleteFriendListener(Activity caller, Friend friend)
    {
        current = caller;
        this.friend = friend;
    }

    @Override
    public void onClick(View v) {
        FriendModel.getInstance().removeFriend(friend.getId());
        Toast.makeText(current, "Deleted Friend", Toast.LENGTH_SHORT).show();
        current.finish();
    }
}
