package mad.friend.controller.meeting;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import mad.friend.view.EditMeetingActivity;

/**
 * Created by Hinaskye on 7/10/2017.
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
        Intent editFriendIntent = new Intent(current, EditMeetingActivity.class);
        editFriendIntent.putExtra("position", position);
        current.startActivity(editFriendIntent);
    }
}
