package mad.friend.controller.meeting;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import hinaskye.assignment1.R;
import mad.friend.model.Friend;
import mad.friend.model.Meeting;

/**
 * MeetingFriendListAdapter
 * Displays the content of the meeting listview based on our meeting model class
 */
public class MeetingFriendListAdapter extends ArrayAdapter<Meeting> {

    private List<Friend> friends;

    public MeetingFriendListAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List objects) {
        super(context, resource, objects);
        friends = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View newView = convertView;

        // inflat if null
        if(newView == null)
        {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE);
            newView = inflater.inflate(R.layout.meeting_friend_list_content, null);
        }

        Friend currentFriend = friends.get(position);

        // Set view of current friend object
        if(currentFriend != null)
        {
            // May want to add image if function implemented
            TextView name = (TextView) newView.findViewById(R.id.meeting_friend_name);

            if(name != null)
            {
                name.setText(currentFriend.getName());
            }
        }

        return newView;
    }
}
