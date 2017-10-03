package mad.friend.controller.friend;

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
import mad.friend.model.FriendModel;

/**
 * Displays the content of the friend listview based on our friend model class
 */
public class FriendListAdapter extends ArrayAdapter<Friend>
{
    private List<Friend> friends;

    public FriendListAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List objects) {
        super(context, resource, objects);
        friends = objects;
    }

    /**
     * Code from https://devtut.wordpress.com/2011/06/09/custom-arrayadapter-for-a-listview-android/
     * modified to use with Friend Tracker app
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View newView = convertView;

        // inflat if null
        if(newView == null)
        {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE);
            newView = inflater.inflate(R.layout.friend_list_content, null);
        }

        Friend currentFriend = friends.get(position);

        // Set view of current friend object
        if(currentFriend != null)
        {
            TextView name = (TextView) newView.findViewById(R.id.friend_name);
            TextView location = (TextView) newView.findViewById(R.id.friend_location);

            if(name != null)
            {
                name.setText(currentFriend.getName());
            }
            if(location != null)
            {
                location.setText((String)FriendModel.getInstance()
                        .getFriendLocation().get(currentFriend.getId()));
            }
        }

        return newView;
    }
}
