package mad.friend.controller.friend;

import android.app.Activity;
import android.content.Intent;
import android.provider.ContactsContract;
import android.view.MenuItem;
import android.view.View;

/**
 *  Add Contact Listener, creates an intent to add contact and starts the activity
 */
public class AddContactListener implements View.OnClickListener, MenuItem.OnMenuItemClickListener {

    protected static final int PICK_CONTACTS = 100;

    Activity current;

    public AddContactListener(Activity calling) {
        current = calling;
    }

    /**
     * Allows the user to select a contact using the provided ContactDataManager class
     */
    @Override
    public void onClick(View v) {
        selectContact();
    }

    public void selectContact()
    {
        Intent contactPickerIntent = new Intent(Intent.ACTION_PICK,
                ContactsContract.Contacts.CONTENT_URI);
        current.startActivityForResult(contactPickerIntent, PICK_CONTACTS);
    }

    /**
     * Same as onClick but as a menu item listener
     */
    @Override
    public boolean onMenuItemClick(MenuItem item) {
        Intent contactPickerIntent = new Intent(Intent.ACTION_PICK,
                ContactsContract.Contacts.CONTENT_URI);
        current.startActivityForResult(contactPickerIntent, PICK_CONTACTS);
        return true;
    }
}
