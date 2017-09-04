package mad.friend.controller;

import android.app.Activity;
import android.content.Intent;
import android.view.MenuItem;

import mad.friend.view.FriendListActivity;

/**
 * Display friend list listener from menu item
 * When the menu item is clicked the listener will start the friend list activity
 */
public class DisplayFriendListListener implements MenuItem.OnMenuItemClickListener{

    private Activity current;

    public DisplayFriendListListener(Activity caller)
    {
        current = caller;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        Intent displayFriendListIntent = new Intent(current, FriendListActivity.class);
        current.startActivity(displayFriendListIntent);
        return true;
    }
}
