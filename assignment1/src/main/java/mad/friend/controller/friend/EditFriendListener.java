package mad.friend.controller.friend;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import mad.friend.model.Friend;
import mad.friend.model.FriendModel;
import mad.friend.view.EditFriendActivity;
import util.FriendTrackerUtil;

/**
 * Edit Friend Listener
 * When friend on friend list is clicked
 * Will create a edit friend intent and start the edit friend activity
 */
public class EditFriendListener implements AdapterView.OnItemClickListener {

    private Activity current;

    public EditFriendListener(Activity caller) {
        current = caller;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // starts the EditFriendActivity and passes position of friend on list view over
        Intent editIntent = new Intent(current, EditFriendActivity.class);
        editIntent.putExtra("position", position);
        current.startActivityForResult(editIntent, FriendTrackerUtil.EDIT_FRIEND);
    }
}
