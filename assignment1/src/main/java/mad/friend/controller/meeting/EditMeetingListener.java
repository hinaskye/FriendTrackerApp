package mad.friend.controller.meeting;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import mad.friend.view.EditMeetingActivity;

/**
 * EditMeetingListener
 * Opens up an edit meeting activity for the selected meeting in the list view
 */
public class EditMeetingListener implements AdapterView.OnItemClickListener {

    private Activity current;
    public EditMeetingListener(Activity caller)
    {
        current = caller;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        Intent editMeetingIntent = new Intent(current, EditMeetingActivity.class);
        editMeetingIntent.putExtra("position", position);
        current.startActivity(editMeetingIntent);
    }
}
