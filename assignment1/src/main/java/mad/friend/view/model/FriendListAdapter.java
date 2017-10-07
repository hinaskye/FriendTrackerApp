package mad.friend.view.model;

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
import mad.friend.controller.DisplayMapsListener;
import mad.friend.model.Friend;
import mad.friend.model.FriendModel;

/**
 * Moved to view.model package as specified
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

                String locationText = (String)FriendModel.getInstance()
                        .getFriendLocation().get(currentFriend.getName());
                if(locationText!=null)
                {   // remove so displays location to 2 decimals, simple fix used for now
                    StringBuilder build = new StringBuilder(locationText);
                    build.deleteCharAt(7);
                    build.deleteCharAt(7);
                    build.deleteCharAt(7);
                    build.deleteCharAt(7);
                    build.deleteCharAt(14);
                    build.deleteCharAt(14);
                    build.deleteCharAt(14);
                    build.deleteCharAt(14);
                    location.setText(build.toString());
                    location.setOnClickListener(new DisplayMapsListener(getContext(),
                            (String)FriendModel.getInstance()
                                    .getFriendLocation().get(currentFriend.getName())));
                }
                else
                {
                    location.setText("");
                }
                //location.setText((String)FriendModel.getInstance().getFriendLocation().get(currentFriend.getName()));
            }
        }

        return newView;
    }
}
