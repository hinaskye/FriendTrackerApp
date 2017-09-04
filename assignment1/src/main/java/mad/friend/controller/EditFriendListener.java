package mad.friend.controller;

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
 * Currently activated on long click from the list view
 * Will create a edit friend intent and start the edit friend activity
 */
public class EditFriendListener implements AdapterView.OnItemLongClickListener {

    private Activity current;

    public EditFriendListener(Activity caller) {
        current = caller;
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        // starts the EditFriendActivity and passes position of friend on list view over
        Intent editIntent = new Intent(current, EditFriendActivity.class);
        editIntent.putExtra("position", position);
        current.startActivityForResult(editIntent, FriendTrackerUtil.EDIT_FRIEND);
        //current.startActivity(editIntent);
        return true;
    }
}
